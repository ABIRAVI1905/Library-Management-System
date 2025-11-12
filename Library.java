
import java.util.*;

class Book {
    String isbn;
    String name;
    int quantity;
    double cost;

    Book(String isbn, String name, int quantity, double cost) {
        this.isbn = isbn;
        this.name = name;
        this.quantity = quantity;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "ISBN: " + isbn + ", Name: " + name + ", Quantity: " + quantity + ", Cost: ₹" + cost;
    }
}

class Borrower {
    String email;
    String password;
    double deposit;
    ArrayList<String> borrowed = new ArrayList<>();
    ArrayList<String> fines = new ArrayList<>();

    Borrower(String email, String password) {
        this.email = email;
        this.password = password;
        this.deposit = 500; // default deposit for example
    }
}

public class Library {
    static HashMap<String, Book> books = new HashMap<>();
    static HashMap<String, Borrower> borrowers = new HashMap<>();
    static HashMap<String, String> admins = new HashMap<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        admins.put("admintp@gmail.com", "admin@tp");
        borrowers.put("usertp@gmail.com", new Borrower("usertp@gmail.com", "user@tp"));
        books.put("ISBN01", new Book("ISBN01", "Java Basics", 5, 300));

        while (true) {
            System.out.println("\n--- Welcome to Library ---");
            System.out.println("\nLOGIN:");
            System.out.print("Enter your Email: ");
            String email = sc.nextLine();
            System.out.print("Enter your Password: ");
            String pass = sc.nextLine();

            if (admins.containsKey(email) && admins.get(email).equals(pass)) {
                adminMenu();
            } else if (borrowers.containsKey(email) && borrowers.get(email).password.equals(pass)) {
                borrowerMenu(borrowers.get(email));
            } else {
                System.out.println("Invalid login, Please enter valid details.");
            }
        }
    }

    static void adminMenu() {
        while (true) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. Add Book");
            System.out.println("2. Modify Book Quantity");
            System.out.println("3. Delete Book");
            System.out.println("4. View All Books");
            System.out.println("5. Search Book");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");
            int a = Integer.parseInt(sc.nextLine());

            switch (a) {
                case 1: {
                    System.out.print("Enter ISBN: ");
                    String isbn = sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Quantity: ");
                    int q = Integer.parseInt(sc.nextLine());
                    System.out.print("Enter Cost: ");
                    double c = Double.parseDouble(sc.nextLine());
                    books.put(isbn, new Book(isbn, name, q, c));
                    System.out.println("Book Added Successfully!");
                    break;
                }
                case 2: {
                    System.out.print("Enter ISBN: ");
                    String isbn = sc.nextLine();
                    if (books.containsKey(isbn)) {
                        System.out.print("Enter New Quantity: ");
                        int q = Integer.parseInt(sc.nextLine());
                        books.get(isbn).quantity = q;
                        System.out.println("Quantity Updated Successfully!");
                    } else {
                        System.out.println("Book Not Found!");
                    }
                    break;
                }
                case 3: {
                    System.out.print("Enter ISBN: ");
                    String isbn = sc.nextLine();
                    if (books.remove(isbn) != null)
                        System.out.println("Book Deleted Successfully!");
                    else
                        System.out.println("Book Not Found!");
                    break;
                }
                case 4: {
                    viewBooks();
                    break;
                }
                case 5: {
                    System.out.print("Enter Book Name or ISBN: ");
                    String key = sc.nextLine();
                    boolean found = false;
                    for (Book b : books.values()) {
                        if (b.name.equalsIgnoreCase(key) || b.isbn.equalsIgnoreCase(key)) {
                            System.out.println(b);
                            found = true;
                            break;
                        }
                    }
                    if (!found)
                        System.out.println("Book Not Found!");
                    break;
                }
                case 6:
                    return;
                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }

    static void viewBooks() {
        System.out.println("\n--- BOOK LIST ---");
        if (books.isEmpty()) {
            System.out.println("No books available!");
            return;
        }
        for (Book b : books.values())
            System.out.println(b);
    }

    static void borrowerMenu(Borrower br) {
        while (true) {
            System.out.println("\n--- USER MENU ---");
            System.out.println("1. View All Books");
            System.out.println("2. Checkout Book");
            System.out.println("3. Return Book");
            System.out.println("4. View Fines");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int a = Integer.parseInt(sc.nextLine());

            switch (a) {
                case 1:
                    viewBooks();
                    break;
                case 2: {
                    if (br.borrowed.size() >= 3) {
                        System.out.println("Maximum 3 books allowed at a time!");
                        break;
                    }
                    System.out.print("Enter ISBN: ");
                    String isbn = sc.nextLine();
                    if (!books.containsKey(isbn)) {
                        System.out.println("Book Not Found!");
                        break;
                    }
                    Book b = books.get(isbn);
                    if (b.quantity <= 0) {
                        System.out.println("Book is Not Available!");
                        break;
                    }
                    if (br.borrowed.contains(isbn)) {
                        System.out.println("You already borrowed this book!");
                        break;
                    }
                    if (br.deposit < 500) {
                        System.out.println("Deposit must be at least ₹500!");
                        break;
                    }
                    b.quantity--;
                    br.borrowed.add(isbn);
                    System.out.println("Book Borrowed Successfully!");
                    break;
                }
                case 3: {
                    System.out.print("Enter ISBN: ");
                    String isbn = sc.nextLine();
                    if (!br.borrowed.contains(isbn)) {
                        System.out.println("You didn’t borrow this book!");
                        break;
                    }
                    System.out.print("How many days you kept the book?: ");
                    int days = Integer.parseInt(sc.nextLine());
                    if (days > 15) {
                        int extra = days - 15;
                        double fine = extra * 2;
                        br.deposit -= fine;
                        br.fines.add("Late fine ₹" + fine);
                        System.out.println("Fine of ₹" + fine + " deducted from your deposit.");
                    }
                    books.get(isbn).quantity++;
                    br.borrowed.remove(isbn);
                    System.out.println("Book Returned Successfully!");
                    break;
                }
                case 4:
                    if (br.fines.isEmpty())
                        System.out.println("No fines pending!");
                    else
                        for (String f : br.fines)
                            System.out.println(f);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}
