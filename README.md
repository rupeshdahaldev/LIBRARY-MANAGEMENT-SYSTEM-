# Library Management System
### A Console-Based Java OOP Project

---

## Project Structure

```
LibraryManagementSystem/
├── src/
│   ├── model/
│   │   ├── Person.java          ← Abstract base class (Encapsulation + Abstraction)
│   │   ├── Member.java          ← Extends Person (Inheritance + Overriding)
│   │   ├── Librarian.java       ← Extends Person (Inheritance + Overriding)
│   │   └── Book.java            ← Entity class (Encapsulation + Overloaded constructors)
│   │
│   ├── service/
│   │   └── LibraryService.java  ← Business logic (Overloading + Polymorphism)
│   │
│   ├── exception/
│   │   ├── BookNotFoundException.java
│   │   ├── BookNotAvailableException.java
│   │   ├── MemberNotFoundException.java
│   │   ├── DuplicateEntryException.java
│   │   └── BorrowLimitExceededException.java
│   │
│   └── main/
│       └── Main.java            ← Entry point, menu-driven UI
│
├── out/                         ← Compiled .class files (auto-created)
├── compile.sh
└── README.md
```

---

## OOP Concepts Map

| Concept              | Where Demonstrated                                                  |
|----------------------|---------------------------------------------------------------------|
| Classes & Objects    | Book, Member, Librarian, LibraryService instances in Main           |
| Constructors         | All model classes have parameterized constructors                   |
| Encapsulation        | All fields private; accessed via getters/setters only               |
| Inheritance          | Member → Person, Librarian → Person                                 |
| Polymorphism         | `Person p = new Member(...)` → `p.displayInfo()` calls Member's    |
| Method Overloading   | `addBook()` × 3, `searchBook()` × 2, `Book()` constructor × 2      |
| Method Overriding    | `getRole()` and `displayInfo()` overridden in Member & Librarian    |
| Exception Handling   | 5 custom exceptions, multi-catch in Main.java                       |
| Packages             | model / service / exception / main                                  |

---

## Compilation & Running

### Step 1 – Compile
```bash
mkdir -p out
javac -d out \
  src/model/Person.java \
  src/model/Book.java \
  src/model/Member.java \
  src/model/Librarian.java \
  src/exception/BookNotFoundException.java \
  src/exception/BookNotAvailableException.java \
  src/exception/MemberNotFoundException.java \
  src/exception/DuplicateEntryException.java \
  src/exception/BorrowLimitExceededException.java \
  src/service/LibraryService.java \
  src/main/Main.java
```

### Step 2 – Run
```bash
java -cp out main.Main
```

### Or use the provided script
```bash
chmod +x compile.sh
./compile.sh
```

---

## Sample Data (pre-loaded)

| Books         | Members            | Librarians       |
|---------------|--------------------|------------------|
| B001 – Clean Code       | M001 – Alice Johnson | L001 – Dr. Sarah Chen |
| B002 – The Pragmatic Programmer | M002 – Bob Williams  | L002 – Mr. James Park |
| B003 – Introduction to Algorithms | M003 – Carol Davis |              |
| B004 – Design Patterns  |                    |                  |
| B005 – The Great Gatsby |                    |                  |
| B006 – 1984             |                    |                  |
| B007 – Effective Java   |                    |                  |

Alice (M001) already has B001 and B005 borrowed.
Bob (M002) already has B003 borrowed.

---

## Menu Options

```
1. Add Book
2. View All Books
3. Register Member
4. Borrow Book
5. Return Book
6. Search Book
7. Show Borrowed Books (by Member)
8. View All Members
9. View All Persons  [Polymorphism Demo]
0. Exit
```

---

## Key Learning Points

1. **`getRole()` is abstract** in Person — Java forces both Member and Librarian
   to provide their own implementation. This is the contract of abstraction.

2. **`displayInfo()` is overridden** — both subclasses call `super.displayInfo()`
   first, then append their own fields. This is method overriding with super usage.

3. **Runtime Polymorphism (Option 9)** — `List<Member>` is iterated as
   `for (Person p : members)`. The call `p.displayInfo()` dispatches to
   `Member.displayInfo()` at runtime, not `Person.displayInfo()`.

4. **Method Overloading** — `addBook()` has three forms:
   - `addBook(Book book)`
   - `addBook(String id, String title, String author, String genre)`
   - `addBook(String id, String title, String author, String genre, String isbn)`
   All lower overloads delegate upward to the first.

5. **Exception Hierarchy** — All custom exceptions extend `Exception` (checked),
   forcing callers to either handle or declare them. This is good API design.
