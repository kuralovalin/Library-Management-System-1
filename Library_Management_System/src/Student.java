//ögrenci icin sahip oldugu kitapları bastırıp
// ona göre sure uzatma talebi gönderebilir

import java.util.ArrayList;
import java.util.Scanner;

public class Student extends User implements Comparable {

    /**
     * Student's reserved table's ID
     */
    private String myTable;
    /**
     * List of student's taken book IDs
     */
    private ArrayList<Book> myBook;
    /**
     * Reserved table counter (max 1)
     */
    private Integer tableCounter;   // max 1
    /**
     * Taken book counter (max 5)
     */
    private Integer bookCounter;    // max 5
    /**
     * Boolean value for student status(in line or not)
     */
    private boolean inLine = false;
    /**
     * Boolean value for student status(on break or not)
     */
    private boolean onBreak = false;

    /**
     * Constructor
     * Initializes student ID
     * @param ID String for student's ID
     */
    public Student(String ID) {
        super(null, null, ID, null);
        tableCounter = 0;
        bookCounter = 0;
    }

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
        tableCounter = 0;
        bookCounter = 0;
    }

   //-TABLE OP----------------------------------------------------------------------------------------

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
            System.out.println("Reservation denied by system.\nYou still have a reserved table. You can't reserve more than 1 table at the same time.");

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
            System.out.println("Permission denied by system.\nYou don't have any reserved table.");
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
            System.out.println("You have already in break for " + this.myTable + ".You can't take another break.");
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
        }
        //student hasn't reserved any table, process failed
        else{
            System.out.println("Permission denied by system.\nYou don't have any reserved table.");
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
            System.out.println("Permission denied by system.\nYou don't have any reserved table.");
    }



    //-BOOK OP---------------------------------------------------------------------------------------------
    public void requestBook(String bookID){
        //  Book new_book = LibrarySystem.requestBook(this.getID(),bookID);
        //    if(new_book != null){   //sistemde öğrenicinin 5 den az kitabı olduğu kontrol edildi
        //sistemdeki data update edildi kitap öğrenciye verildi.
        // burda sadece hangi kitaplara ve masaya sahip olduğumu bilmek için id tutuyorum
        //     my_book.add(new_book);
        //      bookCounter++;
        //  }
    }

    public void extendTimeBook(){
        checkBookTime();
        System.out.println(ANSI_RED + "PLEASE ENTER BOOK ID");
        Scanner scanner = new Scanner(System.in);
        String bookID = scanner.next();
        // LibrarySystem.extendTimeBook(bookID);
    }

    public void checkBookTime(){
        for (Book book : myBook) {
            System.out.println(ANSI_GREEN + "BOOK NAME : " + book.getName()
                    + " BOOK ID : " + book.getID() +  " REMAINING TIME : " + book.getTime() + ANSI_RESET);
        }
    }

    @Override
    public void showBook() {

    }

    @Override
    /**
     * Method to compare student IDs
     * Helper for skip list data structure
     */
    public int compareTo(Object o) {
        int ID1 = Integer.parseInt(this.getID());
        int ID2 = Integer.parseInt(((Student)o).getID());

        if(ID1 < ID2)
            return -1;

        else if(ID1 == ID2)
            return 0;

        else
            return 1;
    }

    
    //getter-setter
    public Integer getTableCounter() {
        return tableCounter;
    }
    public void setTableCounter(Integer tableCounter) {
        this.tableCounter = tableCounter;
    }
    public Integer getBookCounter() {
        return bookCounter;
    }
    public void setBookCounter(Integer bookCounter) {
        this.bookCounter = bookCounter;
    }
    public boolean getInLine() { return inLine; }
    public void setInLine(boolean inLine) { this.inLine = inLine; }

    public String getMyTable() {
        return myTable;
    }

    public void setMyTable(String myTable) {
        this.myTable = myTable;
    }

    public boolean getOnBreak() { return onBreak; }
    public void setOnBreak(boolean onBreak) { this.onBreak = onBreak; }
}
