package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ============================================================
 *  CLASS   : Member
 *  PACKAGE : model
 *  EXTENDS : Person
 *  PURPOSE : Represents a library member who can borrow books.
 *
 *  OOP CONCEPTS DEMONSTRATED:
 *    - Inheritance      : extends Person
 *    - Method Overriding: getRole() and displayInfo() overridden
 *    - Encapsulation    : borrowedBookIds is private; returned as copy
 *    - Constructors     : calls super() to initialize parent fields
 * ============================================================
 */
public class Member extends Person {

    // ── Private Fields (Encapsulation) ─────────────────────────────
    private final List<String> borrowedBookIds; // IDs of currently borrowed books
    private int totalBooksBorrowed;             // historical borrow counter

    // ── Constant: max books a member can hold at once ──────────────
    private static final int MAX_BORROW_LIMIT = 3;

    // ── Constructor ────────────────────────────────────────────────
    public Member(String memberId, String name, String email, String phone) {
        super(memberId, name, email, phone);    // ← calls Person constructor
        this.borrowedBookIds    = new ArrayList<>();
        this.totalBooksBorrowed = 0;
    }

    // ── Method Overriding (Polymorphism) ───────────────────────────
    // Provides Member-specific role label
    @Override
    public String getRole() {
        return "Member";
    }

    // ── Method Overriding: extends parent's displayInfo() ──────────
    // Calls super.displayInfo() first, then adds Member-specific data
    @Override
    public void displayInfo() {
        super.displayInfo();                    // ← parent fields first
        System.out.println("  │  Currently Borrowed : " +
                borrowedBookIds.size() + " / " + MAX_BORROW_LIMIT);
        System.out.println("  │  Total Ever Borrowed: " + totalBooksBorrowed);
        System.out.println("  └─────────────────────────────────────");
    }

    // ── Business Logic Methods ─────────────────────────────────────

    /** @return true if member is below the borrow limit */
    public boolean canBorrow() {
        return borrowedBookIds.size() < MAX_BORROW_LIMIT;
    }

    /** Adds a book ID to the member's borrowed list */
    public void borrowBook(String bookId) {
        borrowedBookIds.add(bookId);
        totalBooksBorrowed++;                   // increment historical counter
    }

    /** Removes a book ID when it is returned */
    public void returnBook(String bookId) {
        borrowedBookIds.remove(bookId);
    }

    /** Checks if this member has already borrowed a specific book */
    public boolean hasBorrowed(String bookId) {
        return borrowedBookIds.contains(bookId);
    }

    // ── Getters (Encapsulation) ────────────────────────────────────
    /** Returns an unmodifiable copy to protect internal list */
    public List<String> getBorrowedBookIds() {
        return Collections.unmodifiableList(borrowedBookIds);
    }

    public int getBorrowedCount()       { return borrowedBookIds.size(); }
    public int getTotalBooksBorrowed()  { return totalBooksBorrowed;     }
    public static int getMaxBorrowLimit() { return MAX_BORROW_LIMIT;    }
}
