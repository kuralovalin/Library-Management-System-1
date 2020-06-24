import java.util.Timer;
import java.util.TimerTask;

/**
 * Simple demo that uses java.util.Timer to schedule a task
 * to execute once 5 seconds have passed.
 */

public class BookReminder {
    Timer timer;
    Timer timer2;
    Book book;
    Student student;
    public BookReminder(int seconds, Book b,Student s){
        timer = new Timer();
        book = b;
        timer2 = new Timer();
        student = s;
        timer2.scheduleAtFixedRate(new RemindTask2(), 1000,1000);
        timer.schedule(new RemindTask(), seconds*1000);
    }

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
    class RemindTask extends TimerTask {
        public void run() {
            System.out.println(User.ANSI_RED + student.getName() + " " + student.getSurname() +
                    "'s " + book.getID() + " reservation has 5 day to expire."  + User.ANSI_RESET);
        }

    }

}

