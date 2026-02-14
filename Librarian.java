package model;

/**
 * ============================================================
 *  CLASS   : Librarian
 *  PACKAGE : model
 *  EXTENDS : Person
 *  PURPOSE : Represents a library staff member.
 *
 *  OOP CONCEPTS DEMONSTRATED:
 *    - Inheritance      : extends Person
 *    - Method Overriding: getRole() and displayInfo() overridden
 *    - Encapsulation    : staffId and department are private
 *    - Constructors     : calls super() to initialize parent fields
 * ============================================================
 */
public class Librarian extends Person {

    // ── Private Fields (Encapsulation) ─────────────────────────────
    private String staffId;
    private String department;

    // ── Constructor ────────────────────────────────────────────────
    public Librarian(String personId, String name, String email,
                     String phone, String staffId, String department) {
        super(personId, name, email, phone);    // ← calls Person constructor
        this.staffId    = staffId;
        this.department = department;
    }

    // ── Method Overriding (Polymorphism) ───────────────────────────
    // Same method name as Member.getRole() → different behavior
    @Override
    public String getRole() {
        return "Librarian";
    }

    // ── Method Overriding: extends parent's displayInfo() ──────────
    @Override
    public void displayInfo() {
        super.displayInfo();                    // ← parent fields first
        System.out.println("  │  Staff ID  : " + staffId);
        System.out.println("  │  Department: " + department);
        System.out.println("  └─────────────────────────────────────");
    }

    // ── Getters & Setters (Encapsulation) ─────────────────────────
    public String getStaffId()          { return staffId;    }
    public String getDepartment()       { return department; }
    public void setDepartment(String d) { this.department = d; }
}
