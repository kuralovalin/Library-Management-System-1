import java.util.ArrayList;
import java.util.Scanner;

public class Student extends User {
    public static final Integer MAX_TABLE_COUNT = 1;
    public static final Integer MAX_BOOK_COUNT = 5;
    private Integer tableCounter;
    private Integer bookCounter;
    private String my_table;
    private ArrayList<Book> my_book;

    public Student(String name, String surname, String ID, String password) {
        super(name, surname, ID, password);
        tableCounter = 0;
        bookCounter = 0;
    }

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

    public void requestBook(String bookID){
        //  Book new_book = LibrarySystem.requestBook(this.getID(),bookID);
        //  if(new_book != null){
        //     my_book.add(new_book);
        //     bookCounter++;
        //  }
    }

    public void reserveTable(String tableID){
       // String new_table = LibrarySystem.requesBook(this.getID(),tableID);
        //    if(new_table != null){
        //      my_table = new_table;
        //      tableCounter ++;
        //  }
    }

    public void extendTimeBook(){
        checkBookTime();
        System.out.println(ANSI_RED + "PLEASE ENTER BOOK ID");
        Scanner scanner = new Scanner(System.in);
        String bookID = scanner.next();
        // LibrarySystem.extendTimeBook(bookID);
    }

    public void extendTimeTable(){
       // LibrarySystem.extendTimeTable(this.getID(),my_table.getID());
    }

    public void takeBreak(){
       // LibrarySystem.takeBreak(this.getID(),my_table.getID());
    }

    public void leaveTable(){
        // LibrarySystem.leaveTable(this.getID(),my_table.getID());
    }

    public void checkTableTime(){
        // LibrarySystem.checkTableTime(this.getID(),my_table.getID());
    }

    public void checkBookTime(){
        for (Book book : my_book) {
            System.out.println(ANSI_GREEN + "BOOK NAME : " + book.getName()
                    + " BOOK ID : " + book.getID() +  " REMAINING TIME : " + book.getTime() + ANSI_RESET);
        }
    }
}
