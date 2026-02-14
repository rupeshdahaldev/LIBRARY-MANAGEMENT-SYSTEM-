package exception;

/**
 * ============================================================
 *  EXCEPTION : BookNotFoundException
 *  PACKAGE   : exception
 *  PURPOSE   : Thrown when a book ID is not found in the catalogue.
 *  TYPE      : Checked Exception (extends Exception)
 * ============================================================
 */
public class BookNotFoundException extends Exception {

    private final String bookId;

    public BookNotFoundException(String bookId) {
        super("Book not found with ID: \"" + bookId + "\"");
        this.bookId = bookId;
    }

    /** @return the book ID that triggered this exception */
    public String getBookId() { return bookId; }
}
