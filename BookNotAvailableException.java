package exception;

/**
 * ============================================================
 *  EXCEPTION : BookNotAvailableException
 *  PACKAGE   : exception
 *  PURPOSE   : Thrown when a member tries to borrow a book
 *              that is already checked out by another member.
 *  TYPE      : Checked Exception (extends Exception)
 * ============================================================
 */
public class BookNotAvailableException extends Exception {

    private final String bookId;

    public BookNotAvailableException(String bookId) {
        super("Book with ID \"" + bookId + "\" is currently borrowed and not available.");
        this.bookId = bookId;
    }

    public String getBookId() { return bookId; }
}
