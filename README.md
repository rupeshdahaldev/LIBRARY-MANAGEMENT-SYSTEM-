# LIBRARY-MANAGEMENT-SYSTEM-
A menu-driven Java Library Management System showcasing OOP: abstract classes, inheritance, runtime polymorphism, method overloading, and custom exception handling.t
# 📚 Library Management System

> A console-based Library Management System built in Java, demonstrating core Object-Oriented Programming concepts as a university group assignment.

![Java](https://img.shields.io/badge/Java-8%2B-ED8B00?style=flat&logo=openjdk&logoColor=white)
![OOP](https://img.shields.io/badge/Paradigm-OOP-blue?style=flat)
![Console](https://img.shields.io/badge/Interface-Console-lightgrey?style=flat)
![License](https://img.shields.io/badge/License-MIT-green?style=flat)

---

## 📖 About

This project manages **Books**, **Members**, **Librarians**, and **Borrow/Return** operations through a menu-driven console interface. It is designed to clearly demonstrate every major OOP concept covered in a university-level Java course.

---

## ✨ Features

| # | Feature |
|---|---------|
| 1 | ➕ Add a book (with or without ISBN) |
| 2 | 📋 View all books in a formatted table |
| 3 | 👤 Register a new member |
| 4 | 📤 Borrow a book |
| 5 | 📥 Return a book |
| 6 | 🔍 Search books by keyword, or by title + author |
| 7 | 📑 View all books currently borrowed by a member |
| 8 | 👥 View all registered members |
| 9 | 🔬 Display all persons *(Runtime Polymorphism demo)* |

---

## 🗂️ Project Structure

```
LibraryManagementSystem/
├── src/
│   ├── model/
│   │   ├── Person.java          ← Abstract base class
│   │   ├── Member.java          ← Extends Person
│   │   ├── Librarian.java       ← Extends Person
│   │   └── Book.java            ← Book entity
│   │
│   ├── service/
│   │   └── LibraryService.java  ← All business logic
│   │
│   ├── exception/
│   │   ├── BookNotFoundException.java
│   │   ├── BookNotAvailableException.java
│   │   ├── MemberNotFoundException.java
│   │   ├── DuplicateEntryException.java
│   │   └── BorrowLimitExceededException.java
│   │
│   └── main/
│       └── Main.java            ← Entry point & menu UI
│
├── out/                         ← Compiled .class files (git-ignored)
├── compile.sh                   ← One-command build & run script
└── README.md
```

---

## 🧠 OOP Concepts Demonstrated

| Concept | Where & How |
|---------|------------|
| **Classes & Objects** | `Book`, `Member`, `Librarian`, `LibraryService` instantiated in `Main.java` |
| **Constructors** | Every model class uses parameterized constructors; subclasses call `super()` |
| **Encapsulation** | All fields are `private`; exposed only through getters and setters |
| **Inheritance** | `Member` and `Librarian` both extend the abstract `Person` class |
| **Polymorphism** | `for (Person p : members)` → `p.displayInfo()` resolves to the correct subclass at runtime |
| **Method Overloading** | `addBook()` has 3 versions; `searchBook()` has 2; `Book` has 2 constructors |
| **Method Overriding** | `getRole()` and `displayInfo()` overridden in both `Member` and `Librarian`; `toString()` in `Book` |
| **Exception Handling** | 5 custom checked exceptions; multi-`catch` blocks in `Main.java` |
| **Packages** | Cleanly separated into `model`, `service`, `exception`, and `main` |

---

## 🚀 Getting Started

### Prerequisites
- Java JDK 8 or higher
- A terminal / command prompt

### Compile

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

### Run

```bash
java -cp out main.Main
```

### Or use the convenience script

```bash
chmod +x compile.sh
./compile.sh
```

---

## 🗃️ Pre-loaded Sample Data

The system boots with sample data so every feature can be tested immediately.

**Books**

| ID | Title | Author | Genre |
|----|-------|--------|-------|
| B001 | Clean Code | Robert C. Martin | Programming |
| B002 | The Pragmatic Programmer | Andrew Hunt | Programming |
| B003 | Introduction to Algorithms | Thomas Cormen | Computer Science |
| B004 | Design Patterns | Gang of Four | Software Engineering |
| B005 | The Great Gatsby | F. Scott Fitzgerald | Fiction |
| B006 | 1984 | George Orwell | Dystopian Fiction |
| B007 | Effective Java | Joshua Bloch | Programming |

**Members**

| ID | Name | Pre-borrowed |
|----|------|-------------|
| M001 | Alice Johnson | B001, B005 |
| M002 | Bob Williams | B003 |
| M003 | Carol Davis | *(none)* |

**Librarians:** Dr. Sarah Chen (Reference & Research), Mr. James Park (Circulation)

---

## 🔬 Key OOP Design Decisions

**Why is `Person` abstract?**
`Person` declares `getRole()` as abstract, creating a compile-time contract. Java will not allow `Member` or `Librarian` to be instantiated without providing their own implementation — this is the essence of abstraction.

**How does Runtime Polymorphism work here?**
In `LibraryService.displayAllPersons()`, both `members` and `librarians` lists are iterated using a `Person` reference. The JVM decides at runtime which version of `displayInfo()` to call — `Member`'s or `Librarian`'s. The calling code never needs to know the concrete type.

**Why do overloaded methods delegate upward?**
`addBook(id, title, author, genre)` internally calls `addBook(new Book(...))`. This keeps all validation logic in one place and avoids code duplication — a core principle of clean design.

**Why are exceptions checked (not runtime)?**
All five custom exceptions extend `Exception`, not `RuntimeException`. This forces every caller to explicitly handle them, making error paths visible and intentional in the API.

---

## 👨‍💻 Authors

> University Group Assignment — Object-Oriented Programming

---

## 📄 License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).
