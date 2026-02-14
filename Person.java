package model;

/**
 * ============================================================
 *  CLASS   : Person (Abstract)
 *  PACKAGE : model
 *  PURPOSE : Base class for all persons in the library system.
 *
 *  OOP CONCEPTS DEMONSTRATED:
 *    - Encapsulation  : All fields are private with getters/setters
 *    - Abstraction    : Abstract class with abstract method getRole()
 *    - Polymorphism   : getRole() and displayInfo() are overridden
 *                       by subclasses (Member, Librarian)
 *    - Constructors   : Parameterized constructor
 * ============================================================
 */
public abstract class Person {

    // ── Encapsulation: private fields cannot be accessed directly ──
    private String personId;
    private String name;
    private String email;
    private String phone;

    // ── Parameterized Constructor ──
    public Person(String personId, String name, String email, String phone) {
        this.personId = personId;
        this.name     = name;
        this.email    = email;
        this.phone    = phone;
    }

    // ── Abstract Method ────────────────────────────────────────────
    // Every subclass MUST implement this → enables Runtime Polymorphism
    public abstract String getRole();

    // ── Concrete Method (can be overridden) ────────────────────────
    // Subclasses call super.displayInfo() then add their own fields
    public void displayInfo() {
        System.out.println("  ┌─────────────────────────────────────");
        System.out.println("  │  Role   : " + getRole());          // Polymorphic call
        System.out.println("  │  ID     : " + personId);
        System.out.println("  │  Name   : " + name);
        System.out.println("  │  Email  : " + email);
        System.out.println("  │  Phone  : " + phone);
    }

    // ── Getters (Encapsulation – read access) ──────────────────────
    public String getPersonId() { return personId; }
    public String getName()     { return name;     }
    public String getEmail()    { return email;    }
    public String getPhone()    { return phone;    }

    // ── Setters (Encapsulation – controlled write access) ──────────
    public void setName(String name)   { this.name  = name;  }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }

    // ── toString() Overridden ──────────────────────────────────────
    @Override
    public String toString() {
        return String.format("[%-10s] %-20s (ID: %s)", getRole(), name, personId);
    }
}
