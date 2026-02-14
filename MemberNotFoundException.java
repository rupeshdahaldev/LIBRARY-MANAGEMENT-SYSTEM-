package exception;

/**
 * ============================================================
 *  EXCEPTION : MemberNotFoundException
 *  PACKAGE   : exception
 *  PURPOSE   : Thrown when a member ID does not match any
 *              registered member in the system.
 *  TYPE      : Checked Exception (extends Exception)
 * ============================================================
 */
public class MemberNotFoundException extends Exception {

    private final String memberId;

    public MemberNotFoundException(String memberId) {
        super("Member not found with ID: \"" + memberId + "\"");
        this.memberId = memberId;
    }

    public String getMemberId() { return memberId; }
}
