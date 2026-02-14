package exception;

/**
 * ============================================================
 *  EXCEPTION : BorrowLimitExceededException
 *  PACKAGE   : exception
 *  PURPOSE   : Thrown when a member tries to borrow more books
 *              than their allowed limit.
 *  TYPE      : Checked Exception (extends Exception)
 * ============================================================
 */
public class BorrowLimitExceededException extends Exception {

    private final String memberName;
    private final int    limit;

    public BorrowLimitExceededException(String memberName, int limit) {
        super("Member \"" + memberName + "\" has reached the maximum borrow limit of "
                + limit + " book(s). Please return a book before borrowing another.");
        this.memberName = memberName;
        this.limit      = limit;
    }

    public String getMemberName() { return memberName; }
    public int    getLimit()      { return limit;      }
}
