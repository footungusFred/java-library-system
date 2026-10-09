import java.util.*;

public class LibrarySystem {
    static class Book {
        String id, title, author;
        boolean borrowed;
        Book(String id, String title, String author) {
            this.id = id; this.title = title; this.author = author;
        }
        public String toString() {
            return String.format("[%s] %-25s by %-20s %s", id, title, author, borrowed ? "❌ Borrowed" : "✅ Available");
        }
    }

    static Map<String, Book> books = new HashMap<>();
    static Scanner sc = new Scanner(System.in);
    static int nextId = 1;

    static void addBook() {
        System.out.print("Title: "); String title = sc.nextLine().trim();
        System.out.print("Author: "); String author = sc.nextLine().trim();
        String id = "B" + String.format("%03d", nextId++);
        books.put(id, new Book(id, title, author));
        System.out.println("✅ Added: " + title + " (ID: " + id + ")");
    }

    static void search() {
        System.out.print("Search (title/author): "); String q = sc.nextLine().trim().toLowerCase();
        boolean found = false;
        for (Book b : books.values())
            if (b.title.toLowerCase().contains(q) || b.author.toLowerCase().contains(q)) { System.out.println("  " + b); found = true; }
        if (!found) System.out.println("  No books found.");
    }

    static void borrow() {
        System.out.print("Book ID to borrow: "); String id = sc.nextLine().trim();
        Book b = books.get(id);
        if (b == null) { System.out.println("Book not found."); return; }
        if (b.borrowed) { System.out.println("Already borrowed."); return; }
        b.borrowed = true; System.out.println("✅ You borrowed: " + b.title);
    }

    static void returnBook() {
        System.out.print("Book ID to return: "); String id = sc.nextLine().trim();
        Book b = books.get(id);
        if (b == null) { System.out.println("Book not found."); return; }
        if (!b.borrowed) { System.out.println("Book was not borrowed."); return; }
        b.borrowed = false; System.out.println("✅ Returned: " + b.title);
    }

    static void listAll() {
        if (books.isEmpty()) { System.out.println("No books in library."); return; }
        System.out.println("
--- Library Catalog ---");
        books.values().stream().sorted(Comparator.comparing(b -> b.title))
            .forEach(b -> System.out.println("  " + b));
        System.out.println();
    }

    public static void main(String[] args) {
        // Seed some books
        books.put("B001", new Book("B001", "Clean Code", "Robert C. Martin"));
        books.put("B002", new Book("B002", "The Pragmatic Programmer", "Andrew Hunt"));
        books.put("B003", new Book("B003", "Design Patterns", "Gang of Four"));
        nextId = 4;
        System.out.println("=== Library Management System ===");
        System.out.println("Commands: add | list | search | borrow | return | quit
");
        while (true) {
            System.out.print("> ");
            switch (sc.nextLine().trim().toLowerCase()) {
                case "add": addBook(); break;
                case "list": listAll(); break;
                case "search": search(); break;
                case "borrow": borrow(); break;
                case "return": returnBook(); break;
                case "quit": System.out.println("Goodbye!"); return;
                default: System.out.println("Unknown command.");
            }
        }
    }
}
