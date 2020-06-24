import java.util.Scanner;
public class Librarian extends User{
    /**
     * Constructor
     * Initializes librarian name, surname, ID and password
     * @param name String for librarian's name
     * @param surname String for librarian's surname
     * @param ID String for librarian's ID
     * @param password String for librarian's password
     */
    public Librarian(String name, String surname, String ID, String password) {
        super(name, surname, ID, password);
    }

    /**
     * This method adds books in to the system
     */
    public void addBook(){
            Scanner scanner = new Scanner(System.in);
            System.out.println(ANSI_BLUE + "Please enter author :" + ANSI_RESET);
            String author = scanner.next();
            System.out.println(ANSI_BLUE + "Please enter book name :" + ANSI_RESET);
            String book_name = scanner.next();
            System.out.println(ANSI_BLUE + "Please enter category name : " + ANSI_RESET);
            String category = scanner.next();
            System.out.println(ANSI_BLUE + "Please enter shelf value :" + ANSI_RESET);
            String shelf = scanner.next();
            System.out.println(ANSI_BLUE + "Please enter order value :" + ANSI_RESET);
            String order = scanner.next();
            String ID = category.charAt(0)  + shelf  + order;
            Book new_book = new Book(ID,category,book_name,author,"available",0);
            LibrarySystem.addBook(new_book);
    }

    /**
     * This method removes book in the system
     */
    public void removeBook(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(ANSI_BLUE + "Please enter bookID :" + ANSI_RESET);
        String bookID = scanner.next();
        LibrarySystem.removeBook(bookID);
    }

    /**
     * Librarian cancels table reservation
     * if the table is abused.
     * This method cancels table reservation
     */
    public void cancelTableReservation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(ANSI_BLUE + "Please enter tableID :" + ANSI_RESET);
        String tableID = scanner.next();
        Table table = LibrarySystem.cancelTableReservation(tableID);
        if (table != null) {
            if (table.getStatus().equals("on break")) {
                System.out.println(ANSI_RED + "Table's owner is on break now." + ANSI_RESET);
            } else if (table.getStatus().equals("available")) {
                System.out.println(ANSI_RED + "Table has already empty." + ANSI_RESET);
            }
            else {
                table.setStatus("available");
                table.setTime(0);
                System.out.println(ANSI_RED + "Table reservation has dropped by librarian." + ANSI_RESET);
             }
        }
        else
            System.out.println(ANSI_RED + "Given " + tableID + " is not exist." + ANSI_RESET);
    }
}
