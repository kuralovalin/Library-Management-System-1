import java.util.Timer;
import java.util.TimerTask;

/**
 * Simple demo that uses java.util.Timer to schedule a task
 * to execute once 5 seconds have passed.
 */

public class TableReminder {
    Timer timer;
    Timer timer2;
    Table table;
    Student student;
    public TableReminder(int seconds, Table t,Student s){
        timer = new Timer();
        table = t;
        timer2 = new Timer();
        student = s;
        timer2.scheduleAtFixedRate(new RemindTask2(), 1000,1000);
        timer.schedule(new RemindTask(), seconds*1000);

    }

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
    class RemindTask extends TimerTask {
        public void run() {
            System.out.println(User.ANSI_RED + student.getName() + " " + student.getSurname() +
                    "'s " + table.getID() + " reservation has 10 minutes to expire."  + User.ANSI_RESET);
        }

    }

}

