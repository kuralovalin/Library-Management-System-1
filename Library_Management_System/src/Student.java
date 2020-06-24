import java.util.ArrayList;
import java.util.Scanner;

public class Student extends User implements Comparable {
    /** Student's reserved table's ID*/
    private String myTable;
    /** List of student's taken book ID*/
    private final ArrayList<String> myBook;
    /** Reserved table counter (max 1)*/
    private Integer tableCounter;   // max 1
    /** Taken book counter (max 5) */
    private Integer bookCounter;    // max 5
    /** Boolean value for student status(in line or not) */
    private boolean inLine = false;
    /** Boolean value for student status(on break or not)*/
    private boolean onBreak = false;

    /**
     * Constructor
     * Initializes student name, surname, ID and password
     * @param name String for student's name
     * @param surname String for student's surname
     * @param ID String for student's ID
     * @param password String for student's password
     */
    public Student(String name, String surname, String ID, String password) {
        super(name, surname, ID, password);
        myBook = new ArrayList<>();
        myTable = null;
        tableCounter = 0;
        bookCounter = 0;
    }

   //-TABLE OPERATIONS------------------------------------------------------------------------------------

    /**
     * Method to reserve table
     * If table counter is 0 calls LibrarySystem acceptRejectTableReservation method
     * Otherwise denies request
     */
    public void reserveTable(){
        //check table counter in order to determine student has table or not
        if(this.getTableCounter() == 0)
             LibrarySystem.acceptRejectTableReservation(this);

        //student has already reserved table, process failed
        else
            System.out.println(ANSI_RED + "Reservation denied by system.\nYou still have a reserved table." +
                    " You can't reserve more than 1 table at the same time." + ANSI_RESET);
    }

    /**
     * Method to extend table time for 1 hour
     * If table counter is 1 calls LibrarySystem approveTableTimeExtend method
     * Otherwise denies request
     */
    public void extendTimeTable(){
        //check table counter in order to determine student has table or not
        if(this.getTableCounter() == 1)
            LibrarySystem.approveTableTimeExtend(this);

        //student hasn't reserved any table, process failed
        else
            System.out.println(ANSI_RED + "Permission denied by system.\n" +
                    "You don't have any reserved table."+ User.ANSI_RESET);
    }

    /**
     * Method to take a break for 10 minutes
     * If student not already on break calls LibrarySystem approveTableTimeExtend method
     * Otherwise denies request
     */
    public void takeBreak(){
        if(!this.onBreak)
            LibrarySystem.approveTableBreak(this);

        //still on break can't take another at the same time
        else
            System.out.println(ANSI_RED + "You have already in break for " +
                    this.myTable + ".You can't take another break."+ User.ANSI_RESET);
    }

    /**
     * Method to leave the table
     * If student has already reserved a table calls LibrarySystem approveLeaveTable method
     * Otherwise denies request
     */
    public void leaveTable(){
        //check table counter in order to determine student has table or not
        if(this.getTableCounter() == 1) {
            LibrarySystem.approveLeaveTable(this);
            this.tableCounter = 0; //there is 0 table for this student now
            this.myTable = null;  //there is no table for this student now
            this.onBreak = false;
        }
        //student hasn't reserved any table, process failed
        else{
            System.out.println(ANSI_RED +"Permission denied by system.\n" +
                    "You don't have any reserved table."+ User.ANSI_RESET);
        }
    }

    /**
     *Method to show remaining reserved time for table
     * If there is already reserved table for student calls checkTableTime method
     * Otherwies denies request
     */
    public void checkTableTime(){
        //check table counter in order to determine student has table or not
        if(this.getTableCounter() == 1)
            LibrarySystem.checkTableTime(this.myTable);
        //student hasn't reserved any table, process failed
        else
            System.out.println(ANSI_RED +"Permission denied by system.\n" +
                    "You don't have any reserved table."+ User.ANSI_RESET);
    }

    //-BOOK OPERATIONS----------------------------------------------------------------------------------

    /**
     * Method to request book
     * If student have book less than 5 and requested book is available in the system
     * Book request will approve, otherwise book request will reject
     */
    public void requestBook() {
        //check book counter
        try {
            if (this.getBookCounter() < 5) {
                Scanner scan = new Scanner(System.in);
                System.out.print(ANSI_BLUE + "Please enter bookID to reserve: " + ANSI_RESET);
                String bookID = scan.next();
                Book book = LibrarySystem.acceptRejectBookRequest(this, bookID);
                if (book != null) {
                    myBook.add(bookID);
                    bookCounter++;
                }
            }
            //student has already reserved 5 book, process failed
            else
                System.out.println(ANSI_RED + "Request denied by system.\nYou already have a 5 reserved book. " +
                        "You can't reserve more than 5 book." + ANSI_RESET);
        }catch (Exception o){
            System.out.println(ANSI_RED + "Wrong ID" + ANSI_RESET);
        }
    }

    /**
     * Method to extend time book
     */
    public void extendTimeBook(){
        try {
            if (this.getBookCounter() >= 1) {
                showBookInfo();
                System.out.println(ANSI_BLUE + "Please enter bookID :" + ANSI_RESET);
                Scanner scanner = new Scanner(System.in);
                String bookID = scanner.next();
                LibrarySystem.approveBookTimeExtend(this, bookID);
            } else
                System.out.println(User.ANSI_RED + " You haven't taken any book." + User.ANSI_RESET);
        }
        catch (Exception e){
            System.out.println("Book ID is not valid.");
        }
    }

    /**
     * This method shows all book student have
     */
    public void showBookInfo(){
       if(myBook.size() != 0)
           LibrarySystem.showBookInfo(myBook);
       else
           System.out.println(ANSI_RED + "There is no reserved book for you." + ANSI_RESET);
    }

    /**
     * Method to compare student IDs
     * Helper for skip list data structure
     * @param o is Object want to compare
     */
    @Override
    public int compareTo(Object o) {
        try {
            int ID1 = Integer.parseInt(this.getID());
            int ID2 = Integer.parseInt(((Student) o).getID());
            return Integer.compare(ID1, ID2);
        }
        catch (Exception e){
            //System.out.println(User.ANSI_RED + "Wrong ID for student." + User.ANSI_RESET);
            return -2;
        }
    }

    //getter-setter -------------------------------------------------------------------------

    /**
     * Returns reserved table counter
     * @return reserved table counter
     */
    public Integer getTableCounter() {
        return tableCounter;
    }

    /**
     * Sets table counter
     * @param tableCounter is new table counter
     */
    public void setTableCounter(Integer tableCounter) {
        this.tableCounter = tableCounter;
    }

    /**
     * Returns taken book count
     * @return taken book count
     */
    public Integer getBookCounter() {
        return bookCounter;
    }

    /**
     * Sets book counter
     * @param bookCounter is new book counter
     */
    public void setBookCounter(Integer bookCounter) {
        this.bookCounter = bookCounter;
    }

    /**
     * Returns student's status(line or not)
     * @return student's status(line or not)
     */
    public boolean getInLine() { return inLine; }

    /**
     * Sets student's status(line or not)
     * @param inLine is new student's status for variable of inLine
     */
    public void setInLine(boolean inLine) { this.inLine = inLine; }

    /**
     * Returns student's table ID
     * @return tstudent's table ID
     */
    public String getMyTable() {
        return myTable;
    }

    /**
     * Sets student's table ID
     * @param myTable is new student's table ID
     */
    public void setMyTable(String myTable) {
        this.myTable = myTable;
    }

    /**
     * Returns list of student's book
     * @return list of student's book
     */
    public ArrayList<String> getMyBook() {
        return myBook;
    }

    /**
     * Returns flag of break
     * @return flag of Break
     */
    public boolean getOnBreak() { return onBreak; }

    /**
     * Sets flag of break
     * @param onBreak is new value for variable of onBreak
     */
    public void setOnBreak(boolean onBreak) { this.onBreak = onBreak; }
}
