package service;

import exception.*;
import model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ============================================================
 *  CLASS   : LibraryService
 *  PACKAGE : service
 *  PURPOSE : Central service layer managing all library
 *            operations (books, members, borrow/return).
 *
 *  OOP CONCEPTS DEMONSTRATED:
 *    - Encapsulation      : All lists are private
 *    - Method Overloading : addBook() has 3 versions
 *                           searchBook() has 2 versions
 *    - Exception Handling : Throws and documents all checked exceptions
 *    - Polymorphism       : displayAllPersons() uses Person references
 *                           to call overridden methods at runtime
 * ============================================================
 */
public class LibraryService {

    // ── Private Data Stores (Encapsulation) ───────────────────────
    private final List<Book>      books;
    private final List<Member>    members;
    private final List<Librarian> librarians;

    // ── Constructor ────────────────────────────────────────────────
    public LibraryService() {
        this.books      = new ArrayList<>();
        this.members    = new ArrayList<>();
        this.librarians = new ArrayList<>();
    }

    // ==============================================================
    //   BOOK OPERATIONS
    // ==============================================================

    /**
     * addBook – OVERLOAD 1
     * Accepts a fully constructed Book object.
     * All other overloads delegate to this one.
     *
     * @throws DuplicateEntryException if bookId already exists
     */
    public void addBook(Book book) throws DuplicateEntryException {
        // Guard: prevent duplicate book IDs
        for (Book b : books) {
            if (b.getBookId().equalsIgnoreCase(book.getBookId())) {
                throw new DuplicateEntryException("Book", book.getBookId());
            }
        }
        books.add(book);
        System.out.println("  ✔ Book added: \"" + book.getTitle() + "\" [ID: " + book.getBookId() + "]");
    }

    /**
     * addBook – OVERLOAD 2  (Method Overloading)
     * Creates a Book without an ISBN, then calls Overload 1.
     */
    public void addBook(String bookId, String title,
                        String author, String genre)
            throws DuplicateEntryException {
        // Delegates to Overload 1 using the 4-arg Book constructor
        addBook(new Book(bookId, title, author, genre));
    }

    /**
     * addBook – OVERLOAD 3  (Method Overloading)
     * Creates a Book with all details including ISBN, then calls Overload 1.
     */
    public void addBook(String bookId, String title,
                        String author, String genre, String isbn)
            throws DuplicateEntryException {
        // Delegates to Overload 1 using the 5-arg Book constructor
        addBook(new Book(bookId, title, author, genre, isbn));
    }

    /** Prints all books in a formatted table */
    public void viewAllBooks() {
        if (books.isEmpty()) {
            System.out.println("  [!] No books in the catalogue yet.");
            return;
        }
        printBookTableHeader();
        for (Book book : books) {
            System.out.println(book.toString());  // calls Book's overridden toString()
        }
        printBookTableFooter();
        System.out.println("  Total: " + books.size() + " book(s)");
    }

    /**
     * searchBook – OVERLOAD 1  (Method Overloading)
     * Searches by a single keyword across title, author, and genre.
     *
     * @param keyword search term
     * @return list of matching books
     */
    public List<Book> searchBook(String keyword) {
        List<Book> results = new ArrayList<>();
        String kw = keyword.toLowerCase().trim();
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(kw)
                    || b.getAuthor().toLowerCase().contains(kw)
                    || b.getGenre().toLowerCase().contains(kw)) {
                results.add(b);
            }
        }
        return results;
    }

    /**
     * searchBook – OVERLOAD 2  (Method Overloading)
     * Searches by BOTH title AND author simultaneously.
     *
     * @param title  partial title to match
     * @param author partial author name to match
     * @return list of matching books
     */
    public List<Book> searchBook(String title, String author) {
        List<Book> results = new ArrayList<>();
        for (Book b : books) {
            boolean titleMatch  = b.getTitle().toLowerCase().contains(title.toLowerCase().trim());
            boolean authorMatch = b.getAuthor().toLowerCase().contains(author.toLowerCase().trim());
            if (titleMatch && authorMatch) {
                results.add(b);
            }
        }
        return results;
    }

    // ==============================================================
    //   MEMBER OPERATIONS
    // ==============================================================

    /**
     * Registers a new member.
     *
     * @throws DuplicateEntryException if memberId already exists
     */
    public void registerMember(Member member) throws DuplicateEntryException {
        for (Member m : members) {
            if (m.getPersonId().equalsIgnoreCase(member.getPersonId())) {
                throw new DuplicateEntryException("Member", member.getPersonId());
            }
        }
        members.add(member);
        System.out.println("  ✔ Member registered: \"" + member.getName()
                + "\" [ID: " + member.getPersonId() + "]");
    }

    /** Adds a librarian to the staff list */
    public void addLibrarian(Librarian librarian) {
        librarians.add(librarian);
        System.out.println("  ✔ Librarian added: \"" + librarian.getName() + "\"");
    }

    /** Prints all registered members */
    public void viewAllMembers() {
        if (members.isEmpty()) {
            System.out.println("  [!] No members registered yet.");
            return;
        }
        printMemberTableHeader();
        for (Member m : members) {
            System.out.printf("| %-8s | %-22s | %-28s | %-7s |%n",
                    m.getPersonId(),
                    m.getName(),
                    m.getEmail(),
                    m.getBorrowedCount() + "/" + Member.getMaxBorrowLimit());
        }
        printMemberTableFooter();
        System.out.println("  Total: " + members.size() + " member(s)");
    }

    // ==============================================================
    //   BORROW / RETURN OPERATIONS
    // ==============================================================

    /**
     * Borrows a book for a member.
     *
     * @throws MemberNotFoundException        if memberId not found
     * @throws BookNotFoundException          if bookId not found
     * @throws BookNotAvailableException      if book is already borrowed
     * @throws BorrowLimitExceededException   if member holds max books
     */
    public void borrowBook(String memberId, String bookId)
            throws MemberNotFoundException,
                   BookNotFoundException,
                   BookNotAvailableException,
                   BorrowLimitExceededException {

        // Step 1: Look up member (throws if not found)
        Member member = findMemberById(memberId);

        // Step 2: Look up book (throws if not found)
        Book book = findBookById(bookId);

        // Step 3: Check book availability (throws if borrowed)
        if (!book.isAvailable()) {
            throw new BookNotAvailableException(bookId);
        }

        // Step 4: Check member's borrow limit (throws if exceeded)
        if (!member.canBorrow()) {
            throw new BorrowLimitExceededException(
                    member.getName(), Member.getMaxBorrowLimit());
        }

        // Step 5: Perform borrow — update both book and member
        book.markBorrowed(memberId);
        member.borrowBook(bookId);

        System.out.println("  ✔ \"" + book.getTitle()
                + "\" successfully borrowed by " + member.getName());
    }

    /**
     * Returns a borrowed book from a member.
     *
     * @throws MemberNotFoundException if memberId not found
     * @throws BookNotFoundException   if bookId not found
     */
    public void returnBook(String memberId, String bookId)
            throws MemberNotFoundException, BookNotFoundException {

        Member member = findMemberById(memberId);
        Book   book   = findBookById(bookId);

        // Guard: make sure this member actually borrowed this book
        if (!member.hasBorrowed(bookId)) {
            System.out.println("  [!] Member \"" + member.getName()
                    + "\" did not borrow book ID \"" + bookId + "\".");
            return;
        }

        // Update both sides
        book.markReturned();
        member.returnBook(bookId);

        System.out.println("  ✔ \"" + book.getTitle()
                + "\" successfully returned by " + member.getName());
    }

    /**
     * Shows all books currently borrowed by a specific member.
     *
     * @throws MemberNotFoundException if memberId not found
     */
    public void showBorrowedBooks(String memberId)
            throws MemberNotFoundException {

        Member member = findMemberById(memberId);
        List<String> borrowedIds = member.getBorrowedBookIds();

        System.out.println("  Borrowed books for member: "
                + member.getName() + " [ID: " + memberId + "]");

        if (borrowedIds.isEmpty()) {
            System.out.println("  └─ (no books currently borrowed)");
            return;
        }

        printBookTableHeader();
        for (String bid : borrowedIds) {
            try {
                Book book = findBookById(bid);
                System.out.println(book.toString());
            } catch (BookNotFoundException e) {
                System.out.println("  [!] Orphaned borrow record for book ID: " + bid);
            }
        }
        printBookTableFooter();
    }

    // ==============================================================
    //   POLYMORPHISM DEMONSTRATION
    // ==============================================================

    /**
     * Demonstrates RUNTIME POLYMORPHISM.
     *
     * A Person reference is used to point at both Member and Librarian
     * objects. When displayInfo() is called, Java's dynamic dispatch
     * invokes the correct overridden version at runtime — not the
     * abstract one in Person.
     */
    public void displayAllPersons() {
        System.out.println("\n  ── Members ──────────────────────────");
        // Person reference → Member object (Runtime Polymorphism)
        for (Person p : members) {
            p.displayInfo();  // calls Member.displayInfo() at runtime
        }

        System.out.println("\n  ── Librarians ───────────────────────");
        // Person reference → Librarian object (Runtime Polymorphism)
        for (Person p : librarians) {
            p.displayInfo();  // calls Librarian.displayInfo() at runtime
        }

        if (members.isEmpty() && librarians.isEmpty()) {
            System.out.println("  [!] No persons in the system yet.");
        }
    }

    // ==============================================================
    //   PRIVATE HELPER METHODS
    // ==============================================================

    /** Finds a Member by ID or throws MemberNotFoundException */
    private Member findMemberById(String memberId) throws MemberNotFoundException {
        for (Member m : members) {
            if (m.getPersonId().equalsIgnoreCase(memberId)) {
                return m;
            }
        }
        throw new MemberNotFoundException(memberId);
    }

    /** Finds a Book by ID or throws BookNotFoundException */
    private Book findBookById(String bookId) throws BookNotFoundException {
        for (Book b : books) {
            if (b.getBookId().equalsIgnoreCase(bookId)) {
                return b;
            }
        }
        throw new BookNotFoundException(bookId);
    }

    // ── Table Formatting Helpers ───────────────────────────────────

    public static void printBookTableHeader() {
        System.out.println("  +----------+----------------------------------+--------------------+---------------+-----------+");
        System.out.printf("  | %-8s | %-32s | %-18s | %-13s | %-9s |%n",
                "Book ID", "Title", "Author", "Genre", "Status");
        System.out.println("  +----------+----------------------------------+--------------------+---------------+-----------+");
    }

    public static void printBookTableFooter() {
        System.out.println("  +----------+----------------------------------+--------------------+---------------+-----------+");
    }

    private static void printMemberTableHeader() {
        System.out.println("  +----------+------------------------+------------------------------+---------+");
        System.out.printf("  | %-8s | %-22s | %-28s | %-7s |%n",
                "ID", "Name", "Email", "Books");
        System.out.println("  +----------+------------------------+------------------------------+---------+");
    }

    private static void printMemberTableFooter() {
        System.out.println("  +----------+------------------------+------------------------------+---------+");
    }

    // ── Read-only Getters ──────────────────────────────────────────
    public List<Book>   getBooks()      { return Collections.unmodifiableList(books);      }
    public List<Member> getMembers()    { return Collections.unmodifiableList(members);    }
}
