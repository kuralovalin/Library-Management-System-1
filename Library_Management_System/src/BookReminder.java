import java.util.Timer;
import java.util.TimerTask;

public class BookReminder {
    /** timer for warning*/
    Timer timer;
    /** timer for every day */
    Timer timer2;
    /** student's book object*/
    Book book;
    /** student object*/
    Student student;
    /**
     * Constructor
     * @param seconds is the variable that shows when the warning is given
     * @param b is student's book object
     * @param s is student object
     */
    public BookReminder(int seconds, Book b,Student s){
        timer = new Timer();
        book = b;
        timer2 = new Timer();
        student = s;
        // every one day
        timer2.scheduleAtFixedRate(new RemindTask2(), 1000,1000);
        // warning time
        timer.schedule(new RemindTask(), seconds*1000);
    }

    /**
     * Class for decrease table time (every minutes)
     */
    class RemindTask2 extends TimerTask {
        public void run() {
            book.updateTime();
            if(book.getTime() == 0){
                System.out.println(User.ANSI_RED + student.getName() + " " + student.getSurname() + "'s "
                        + book.getID() + " reservation has expired. Please return the book to library."  + User.ANSI_RESET);
                timer2.cancel();
            }
        }
    }

    /**
     * Class that shows when the warning is given ( 10 minutes expire )
     */
    class RemindTask extends TimerTask {
        public void run() {
            System.out.println(User.ANSI_RED + student.getName() + " " + student.getSurname() +
                    "'s " + book.getID() + " reservation has 5 day to expire."  + User.ANSI_RESET);
        }
    }
}

