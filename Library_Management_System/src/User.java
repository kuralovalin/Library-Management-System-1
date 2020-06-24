import java.util.Scanner;

public abstract class User implements UserInterface {
    /** Ansi escape codes */
    public static final String ANSI_RESET = "\u001B[0m";
    /** Ansi escape codes */
    public static final String ANSI_RED = "\u001B[31m";
    /** Ansi escape codes */
    public static final String ANSI_GREEN = "\u001B[32m";
    /** Ansi escape codes */
    public static final String ANSI_YELLOW = "\u001B[33m";
    /** Ansi escape codes */
    public static final String ANSI_BLUE = "\u001B[34m";
    /** Holds user's name*/
    private String name;
    /** Holds user's surname*/
    private String surname;
    /** Holds user's ID*/
    private String ID;
    /** Holds user's password*/
    private String password;

     /**
     * Constructor
     * Initializes user name, surname, ID and password
     * @param name String for user's name
     * @param surname String for user's surname
     * @param ID String for user's ID
     * @param password String for user's password
     */
    public User(String name, String surname, String ID, String password) {
        this.name = name;
        this.surname = surname;
        this.ID = ID;
        this.password = password;
    }

    /**
     * This methods shows all book in the system
     */
    @Override
    public void showBook() { LibrarySystem.showBook(); }

    /**
     * This methods shows all table in the system
     */
    @Override
    public void showTable() {
        LibrarySystem.showTable();
    }

    /**
     * This methods search given book name in the system
     */
    @Override
    public void searchBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(ANSI_BLUE + "Please enter book name :" + ANSI_RESET);
        String book_name = scanner.nextLine();
        LibrarySystem.searchBook(book_name);
    }

    //- Getter-Setter ---------------------------------------------------------------------

    /**
     * Returns user's name
     * @return user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name
     * @param name is new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns user's surname
     * @return user's surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets surname
     * @param surname is new surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Returns user's ID
     * @return user's ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Sets ID
     * @param ID is new ID
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Returns user's password
     * @return user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password
     * @param password is new password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}

