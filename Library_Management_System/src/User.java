import java.util.Scanner;

public abstract class User implements UserInterface {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    private String name;
    private String surname;
    private String ID;
    private String password;

    public User(String name, String surname, String ID, String password) {
        this.name = name;
        this.surname = surname;
        this.ID = ID;
        this.password = password;
    }


    @Override
    public void showTable() {
        //LibrarySystem.showTable();
    }

    @Override
    public void searchBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(ANSI_RED + "PLEASE ENTER BOOK NAME" + ANSI_RESET);
        String book_name = scanner.next();
        //LibrarySystem.searchBook(book_name);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

