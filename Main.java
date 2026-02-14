package main;

import exception.*;
import model.*;
import service.LibraryService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * ============================================================
 *  CLASS   : Main
 *  PACKAGE : main
 *  PURPOSE : Entry point of the Library Management System.
 *            Provides a menu-driven console interface.
 *
 *  OOP CONCEPTS DEMONSTRATED:
 *    - Objects            : LibraryService, Book, Member, Librarian
 *    - Exception Handling : try-catch blocks for all service calls
 *    - Polymorphism       : demonstrated via Option 9 in menu
 * ============================================================
 */
public class Main {

    // ── Single shared service instance ────────────────────────────
    private static final LibraryService libraryService = new LibraryService();
    private static final Scanner        scanner        = new Scanner(System.in);

    // ==============================================================
    //   ENTRY POINT
    // ==============================================================
    public static void main(String[] args) {

        printBanner();
        loadSampleData();   // pre-populate with demo data

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("  Enter your choice: ");

            switch (choice) {
                case 1:  handleAddBook();            break;
                case 2:  handleViewBooks();           break;
                case 3:  handleRegisterMember();      break;
                case 4:  handleBorrowBook();          break;
                case 5:  handleReturnBook();          break;
                case 6:  handleSearchBook();          break;
                case 7:  handleShowBorrowedBooks();   break;
                case 8:  handleViewMembers();         break;
                case 9:  handleDisplayAllPersons();   break;
                case 0:
                    System.out.println("\n  ╔══════════════════════════════════════════╗");
                    System.out.println("  ║  Thank you for using the Library System! ║");
                    System.out.println("  ╚══════════════════════════════════════════╝\n");
                    running = false;
                    break;
                default:
                    System.out.println("  [!] Invalid choice. Please select 0–9.");
            }
        }

        scanner.close();
    }

    // ==============================================================
    //   MENU HANDLERS
    // ==============================================================

    /**
     * HANDLER 1 — Add a new book
     * Demonstrates: Method Overloading (addBook with ISBN vs without)
     */
    private static void handleAddBook() {
        printSectionHeader("ADD BOOK");
        System.out.print("  Enter Book ID     : "); String id     = scanner.nextLine().trim();
        System.out.print("  Enter Title       : "); String title  = scanner.nextLine().trim();
        System.out.print("  Enter Author      : "); String author = scanner.nextLine().trim();
        System.out.print("  Enter Genre       : "); String genre  = scanner.nextLine().trim();
        System.out.print("  Enter ISBN (or press Enter to skip): ");
        String isbn = scanner.nextLine().trim();

        try {
            if (isbn.isEmpty()) {
                // Calls addBook OVERLOAD 2 — without ISBN
                libraryService.addBook(id, title, author, genre);
            } else {
                // Calls addBook OVERLOAD 3 — with ISBN
                libraryService.addBook(id, title, author, genre, isbn);
            }
        } catch (DuplicateEntryException e) {
            // Exception Handling: user-friendly message for duplicates
            System.out.println("  [✘] Error: " + e.getMessage());
        }
    }

    /**
     * HANDLER 2 — View all books
     */
    private static void handleViewBooks() {
        printSectionHeader("ALL BOOKS IN CATALOGUE");
        libraryService.viewAllBooks();
    }

    /**
     * HANDLER 3 — Register a new member
     * Creates a Member object and calls registerMember()
     */
    private static void handleRegisterMember() {
        printSectionHeader("REGISTER MEMBER");
        System.out.print("  Enter Member ID   : "); String id    = scanner.nextLine().trim();
        System.out.print("  Enter Full Name   : "); String name  = scanner.nextLine().trim();
        System.out.print("  Enter Email       : "); String email = scanner.nextLine().trim();
        System.out.print("  Enter Phone       : "); String phone = scanner.nextLine().trim();

        // Create a Member object (demonstrates Objects & Constructors)
        Member member = new Member(id, name, email, phone);

        try {
            libraryService.registerMember(member);
        } catch (DuplicateEntryException e) {
            System.out.println("  [✘] Error: " + e.getMessage());
        }
    }

    /**
     * HANDLER 4 — Borrow a book
     * Demonstrates Exception Handling for multiple checked exceptions
     */
    private static void handleBorrowBook() {
        printSectionHeader("BORROW BOOK");
        System.out.print("  Enter Member ID   : "); String memberId = scanner.nextLine().trim();
        System.out.print("  Enter Book ID     : "); String bookId   = scanner.nextLine().trim();

        try {
            libraryService.borrowBook(memberId, bookId);

        } catch (MemberNotFoundException e) {
            // Specific catch for member not found
            System.out.println("  [✘] Member Error : " + e.getMessage());

        } catch (BookNotFoundException e) {
            // Specific catch for book not found
            System.out.println("  [✘] Book Error   : " + e.getMessage());

        } catch (BookNotAvailableException e) {
            // Book exists but is already borrowed
            System.out.println("  [✘] Unavailable  : " + e.getMessage());

        } catch (BorrowLimitExceededException e) {
            // Member is at their borrowing limit
            System.out.println("  [✘] Limit Reached: " + e.getMessage());
        }
    }

    /**
     * HANDLER 5 — Return a borrowed book
     */
    private static void handleReturnBook() {
        printSectionHeader("RETURN BOOK");
        System.out.print("  Enter Member ID   : "); String memberId = scanner.nextLine().trim();
        System.out.print("  Enter Book ID     : "); String bookId   = scanner.nextLine().trim();

        try {
            libraryService.returnBook(memberId, bookId);

        } catch (MemberNotFoundException e) {
            System.out.println("  [✘] Member Error: " + e.getMessage());

        } catch (BookNotFoundException e) {
            System.out.println("  [✘] Book Error  : " + e.getMessage());
        }
    }

    /**
     * HANDLER 6 — Search for a book
     * Demonstrates: Method Overloading for searchBook()
     */
    private static void handleSearchBook() {
        printSectionHeader("SEARCH BOOK");
        System.out.println("  Search by:");
        System.out.println("    1. Keyword (title / author / genre)");
        System.out.println("    2. Title AND Author (combined)");
        int mode = readInt("  Select mode: ");

        List<Book> results;

        if (mode == 2) {
            // Calls searchBook OVERLOAD 2 — title + author
            System.out.print("  Enter Title keyword : "); String t = scanner.nextLine().trim();
            System.out.print("  Enter Author keyword: "); String a = scanner.nextLine().trim();
            results = libraryService.searchBook(t, a);
        } else {
            // Calls searchBook OVERLOAD 1 — single keyword
            System.out.print("  Enter keyword: "); String kw = scanner.nextLine().trim();
            results = libraryService.searchBook(kw);
        }

        if (results.isEmpty()) {
            System.out.println("  [!] No books matched your search.");
        } else {
            System.out.println("  Found " + results.size() + " result(s):");
            LibraryService.printBookTableHeader();
            for (Book b : results) {
                System.out.println(b.toString());
            }
            LibraryService.printBookTableFooter();
        }
    }

    /**
     * HANDLER 7 — Show borrowed books for a member
     */
    private static void handleShowBorrowedBooks() {
        printSectionHeader("BORROWED BOOKS");
        System.out.print("  Enter Member ID: "); String memberId = scanner.nextLine().trim();

        try {
            libraryService.showBorrowedBooks(memberId);
        } catch (MemberNotFoundException e) {
            System.out.println("  [✘] Error: " + e.getMessage());
        }
    }

    /**
     * HANDLER 8 — View all registered members
     */
    private static void handleViewMembers() {
        printSectionHeader("ALL REGISTERED MEMBERS");
        libraryService.viewAllMembers();
    }

    /**
     * HANDLER 9 — Display all persons (RUNTIME POLYMORPHISM DEMO)
     *
     * Person references point to Member and Librarian objects.
     * The correct overridden displayInfo() is called at runtime
     * — this is the essence of Runtime Polymorphism / dynamic dispatch.
     */
    private static void handleDisplayAllPersons() {
        printSectionHeader("ALL PERSONS  [POLYMORPHISM DEMO]");
        System.out.println("  ℹ  Person references call overridden displayInfo()");
        System.out.println("     at runtime for both Members and Librarians.\n");
        libraryService.displayAllPersons();
    }

    // ==============================================================
    //   UI HELPERS
    // ==============================================================

    private static void printBanner() {
        System.out.println();
        System.out.println("  ╔═══════════════════════════════════════════════╗");
        System.out.println("  ║        LIBRARY MANAGEMENT SYSTEM  v1.0        ║");
        System.out.println("  ║         Built with Core Java & OOP            ║");
        System.out.println("  ╚═══════════════════════════════════════════════╝");
        System.out.println();
    }

    private static void printMenu() {
        System.out.println("\n  ╔══════════════════════════════════════════════╗");
        System.out.println("  ║                  MAIN MENU                   ║");
        System.out.println("  ╠══════════════════════════════════════════════╣");
        System.out.println("  ║  1. Add Book                                 ║");
        System.out.println("  ║  2. View All Books                           ║");
        System.out.println("  ║  3. Register Member                          ║");
        System.out.println("  ║  4. Borrow Book                              ║");
        System.out.println("  ║  5. Return Book                              ║");
        System.out.println("  ║  6. Search Book                              ║");
        System.out.println("  ║  7. Show Borrowed Books (by Member)          ║");
        System.out.println("  ║  8. View All Members                         ║");
        System.out.println("  ║  9. View All Persons  [Polymorphism Demo]    ║");
        System.out.println("  ║  0. Exit                                     ║");
        System.out.println("  ╚══════════════════════════════════════════════╝");
    }

    private static void printSectionHeader(String title) {
        System.out.println("\n  ┌──────────────────────────────────────────────");
        System.out.println("  │  " + title);
        System.out.println("  └──────────────────────────────────────────────");
    }

    /**
     * Safely reads an integer from the console.
     * Handles InputMismatchException to avoid crashes on bad input.
     */
    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("  [!] Please enter a valid number.");
            }
        }
    }

    // ==============================================================
    //   SAMPLE DATA LOADER
    // ==============================================================

    /**
     * Pre-loads sample books, members, and librarians so the system
     * can be demonstrated immediately without manual data entry.
     */
    private static void loadSampleData() {
        System.out.println("  Loading sample data...");

        // ── Add Books ──────────────────────────────────────────────
        // Uses addBook OVERLOAD 3 (with ISBN) and OVERLOAD 2 (without)
        try {
            libraryService.addBook("B001", "Clean Code",
                    "Robert C. Martin", "Programming", "978-0132350884");
            libraryService.addBook("B002", "The Pragmatic Programmer",
                    "Andrew Hunt", "Programming", "978-0201616224");
            libraryService.addBook("B003", "Introduction to Algorithms",
                    "Thomas Cormen", "Computer Science", "978-0262033848");
            libraryService.addBook("B004", "Design Patterns",
                    "Gang of Four", "Software Engineering", "978-0201633610");
            libraryService.addBook("B005", "The Great Gatsby",
                    "F. Scott Fitzgerald", "Fiction");             // no ISBN (Overload 2)
            libraryService.addBook("B006", "1984",
                    "George Orwell", "Dystopian Fiction");
            libraryService.addBook("B007", "Effective Java",
                    "Joshua Bloch", "Programming", "978-0134685991");

        } catch (DuplicateEntryException e) {
            System.out.println("  [!] Sample data warning: " + e.getMessage());
        }

        // ── Register Members ───────────────────────────────────────
        // Creates Member objects — demonstrates Classes & Objects
        try {
            libraryService.registerMember(
                    new Member("M001", "Alice Johnson", "alice@email.com", "555-0101"));
            libraryService.registerMember(
                    new Member("M002", "Bob Williams", "bob@email.com",   "555-0102"));
            libraryService.registerMember(
                    new Member("M003", "Carol Davis",  "carol@email.com", "555-0103"));

        } catch (DuplicateEntryException e) {
            System.out.println("  [!] Sample data warning: " + e.getMessage());
        }

        // ── Add Librarians ─────────────────────────────────────────
        // Creates Librarian objects — another subclass of Person
        libraryService.addLibrarian(new Librarian(
                "L001", "Dr. Sarah Chen", "s.chen@library.com",
                "555-0201", "STF-001", "Reference & Research"));
        libraryService.addLibrarian(new Librarian(
                "L002", "Mr. James Park", "j.park@library.com",
                "555-0202", "STF-002", "Circulation"));

        // ── Pre-borrow a book (to demo borrowed state) ─────────────
        try {
            libraryService.borrowBook("M001", "B001"); // Alice borrows Clean Code
            libraryService.borrowBook("M001", "B005"); // Alice borrows The Great Gatsby
            libraryService.borrowBook("M002", "B003"); // Bob borrows Intro to Algorithms

        } catch (Exception e) {
            System.out.println("  [!] Sample borrow warning: " + e.getMessage());
        }

        System.out.println("  ✔ Sample data loaded successfully!\n");
    }
}
