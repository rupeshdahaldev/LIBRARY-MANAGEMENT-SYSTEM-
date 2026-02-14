package model;

/**
 * ============================================================
 *  CLASS   : Book
 *  PACKAGE : model
 *  PURPOSE : Represents a book in the library catalogue.
 *
 *  OOP CONCEPTS DEMONSTRATED:
 *    - Encapsulation        : All fields are private
 *    - Constructors         : Two constructors (full & without ISBN)
 *    - Method Overloading   : Two constructors with different params
 *    - toString() Overriding: Formatted one-line table row
 * ============================================================
 */
public class Book {

    // ── Private Fields (Encapsulation) ─────────────────────────────
    private final String bookId;     // unique identifier, set once
    private String title;
    private String author;
    private String genre;
    private String isbn;
    private boolean isAvailable;           // true = on shelf, false = borrowed
    private String  borrowedByMemberId;    // null when on shelf

    // ── Constructor 1: Full details (with ISBN) ────────────────────
    // Method Overloading: two constructors, different parameter lists
    public Book(String bookId, String title, String author,
                String genre, String isbn) {
        this.bookId              = bookId;
        this.title               = title;
        this.author              = author;
        this.genre               = genre;
        this.isbn                = isbn;
        this.isAvailable         = true;   // default: available
        this.borrowedByMemberId  = null;
    }

    // ── Constructor 2: Without ISBN (Method Overloading) ──────────
    // Delegates to the full constructor using "this(...)"
    public Book(String bookId, String title, String author, String genre) {
        this(bookId, title, author, genre, "N/A");
    }

    // ── State-Changing Methods ─────────────────────────────────────

    /** Called when a member borrows this book */
    public void markBorrowed(String memberId) {
        this.isAvailable        = false;
        this.borrowedByMemberId = memberId;
    }

    /** Called when a member returns this book */
    public void markReturned() {
        this.isAvailable        = true;
        this.borrowedByMemberId = null;
    }

    // ── Display Method ─────────────────────────────────────────────
    public void displayInfo() {
        System.out.println("  ┌─────────────────────────────────────");
        System.out.println("  │  Book ID  : " + bookId);
        System.out.println("  │  Title    : " + title);
        System.out.println("  │  Author   : " + author);
        System.out.println("  │  Genre    : " + genre);
        System.out.println("  │  ISBN     : " + isbn);
        System.out.printf ("  │  Status   : %s%n",
                isAvailable ? "✔ Available" : "✘ Borrowed by [" + borrowedByMemberId + "]");
        System.out.println("  └─────────────────────────────────────");
    }

    // ── toString() Overriding – produces a formatted table row ─────
    // The 2-space leading indent matches the table headers in LibraryService
    @Override
    public String toString() {
        return String.format("  | %-8s | %-32s | %-18s | %-13s | %-9s |",
                bookId,
                truncate(title,  32),
                truncate(author, 18),
                truncate(genre,  13),
                isAvailable ? "Available" : "Borrowed");
    }

    /** Truncate long strings to fit table columns */
    private String truncate(String s, int max) {
        return (s.length() > max) ? s.substring(0, max - 2) + ".." : s;
    }

    // ── Getters (Encapsulation) ────────────────────────────────────
    public String  getBookId()             { return bookId;             }
    public String  getTitle()              { return title;              }
    public String  getAuthor()             { return author;             }
    public String  getGenre()              { return genre;              }
    public String  getIsbn()               { return isbn;               }
    public boolean isAvailable()           { return isAvailable;        }
    public String  getBorrowedByMemberId() { return borrowedByMemberId; }

    // ── Setters (Encapsulation) ────────────────────────────────────
    public void setTitle(String title)   { this.title  = title;  }
    public void setAuthor(String author) { this.author = author; }
    public void setGenre(String genre)   { this.genre  = genre;  }
    public void setIsbn(String isbn)     { this.isbn   = isbn;   }
}
