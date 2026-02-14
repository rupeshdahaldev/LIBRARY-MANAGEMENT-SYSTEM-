package exception;

/**
 * ============================================================
 *  EXCEPTION : DuplicateEntryException
 *  PACKAGE   : exception
 *  PURPOSE   : Thrown when a duplicate ID is detected
 *              (used for both Books and Members).
 *  TYPE      : Checked Exception (extends Exception)
 * ============================================================
 */
public class DuplicateEntryException extends Exception {

    private final String id;
    private final String entityType; // e.g. "Book" or "Member"

    public DuplicateEntryException(String entityType, String id) {
        super(entityType + " with ID \"" + id + "\" already exists in the system.");
        this.entityType = entityType;
        this.id         = id;
    }

    public String getId()         { return id;         }
    public String getEntityType() { return entityType; }
}
