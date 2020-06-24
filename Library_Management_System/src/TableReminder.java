import java.util.Timer;
import java.util.TimerTask;

public class TableReminder {
    /** timer for warning*/
    Timer timer;
    /** timer for every minute */
    Timer timer2;
    /** student's table object*/
    Table table;
    /** student object*/
    Student student;
    /**
     * Constructor
     * @param seconds is the variable that shows when the warning is given
     * @param t is student's table object
     * @param s is student object
     */
    public TableReminder(int seconds, Table t,Student s){
        timer = new Timer();
        table = t;
        timer2 = new Timer();
        student = s;
        //every one minute
        timer2.scheduleAtFixedRate(new RemindTask2(), 1000,1000);
        //warning time
        timer.schedule(new RemindTask(), seconds*1000);
    }

    /**
     * Class for decrease table time (every minutes)
     */
    class RemindTask2 extends TimerTask {
        public void run() {
            table.updateTime();
            if(table.getTime() == 0){
                System.out.println(User.ANSI_RED + student.getName() + " " + student.getSurname() + "'s "
                        + table.getID() + " reservation is dropped by system."  + User.ANSI_RESET);
                student.leaveTable();
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
                    "'s " + table.getID() + " reservation has 10 minutes to expire."  + User.ANSI_RESET);
        }
    }

}

