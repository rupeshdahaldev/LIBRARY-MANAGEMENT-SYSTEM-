#!/bin/bash
# ============================================================
#  compile.sh — Build and run the Library Management System
#  Usage: chmod +x compile.sh && ./compile.sh
# ============================================================

echo "================================================"
echo "  Library Management System — Build Script"
echo "================================================"

# Create output directory for .class files
mkdir -p out

echo "[1/2] Compiling Java source files..."

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

# Check if compilation succeeded
if [ $? -eq 0 ]; then
  echo "[✔] Compilation successful!"
  echo ""
  echo "[2/2] Starting application..."
  echo "================================================"
  java -cp out main.Main
else
  echo "[✘] Compilation failed. Please check errors above."
  exit 1
fi
