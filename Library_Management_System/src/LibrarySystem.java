import java.util.*;

public class LibrarySystem {

    private static SkipList<Student> students;
    private static ArrayList<Librarian> librarians;
    private static PriorityQueue<Table> tables;
    static AVLTree<Book> books = new AVLTree<>();
    private static Queue<Student> tableReservationLine;

    public LibrarySystem(){
        tables = new PriorityQueue<Table>();
        tableReservationLine = new LinkedList<>();
        initializeTableData(tables,10);
        initializeStudentData();
    }

    static {
        students = new SkipList<>();
        librarians = new ArrayList<>();
    }


    //-STUDENT (TABLE)--------------------------------------------------------------------------

    /**
     * Method to initialize table data to priority queue according to their time values
     * Every table status is "available" and time is 0 at the beginning
     * @param tables Priority Queue to store table objects
     * @param capacity Integer for priority queue capacity (number of tables)
     */
    private void initializeTableData(PriorityQueue<Table> tables, int capacity){

        /*UNCOMMENT THIS LATER
        for(int i=0; i<capacity ; i++){
            String tableID = "Table" + (i+1);
            //puts every object to priority queue
            tables.offer(new Table(tableID, "available", 0));
        }*/


        //Initializing table objects to priority queue
       for(int i=0; i<capacity-3 ; i++){
            String tableID = "Table" + (i+1);
            //Initializing each table with -> TableX, Available, Time(now)
            //puts every object to priority queue
            tables.offer(new Table(tableID, "reserved", 60));
        }
        tables.offer(new Table("Table8", "reserved", 50));
        tables.offer(new Table("Table9", "reserved", 40));
        tables.offer(new Table("Table10", "available", 0));

       /* UNCOMMENT TO PRINT
       for(int i=0; i<capacity ; i++){
            Table t =  tables.poll();
            //Table t = tables.getTheData().get(i);
            System.out.println("ID: " +t.getID() + "  Status: " + t.getStatus() + "  Time: " + t.getTime() );

        }*/

    }

    /**
     * Method to initialize student datas to skip list according to their IDs
     * Every student's name,surname,ID and password initialized
     */
    private void initializeStudentData(){

        students.insert(new  Student("Ezgi", "Cakir", "1", "X1"));
        students.insert(new  Student("Alina", "Kuralova", "2", "X2"));
        students.insert(new  Student("Furkan", "Aydın", "3", "X3"));
        students.insert(new  Student("Oguzhan", "Senturk", "4", "X4"));
        students.insert(new  Student("Selimhan", "Meral", "5", "X5"));
        students.insert(new  Student("Sarwar", "Hossain", "6", "X6"));
    }

    /**
     * Method to approve or deny table reservation for student request
     * If there is empty table at the library it takes TableID then according to table's status approves or denies reservation
     * If there is no empty table at the library it asks user whether wait or not
     * then according to answer adds user to waiting queue or not
     * @param student Student object to represent who wants to reserve table
     */
    protected static void acceptRejectTableReservation(Student student){
        //Iterator for table priority queue
        Iterator iter = tables.iterator();
        Table t = new Table();

        //There is/are empty table(s)
        if(!isFull()) {
            //scanning user input for tableId in order to reserve
            Scanner scan =  new Scanner(System.in);
            System.out.println(" Enter tableID to reserve.");
            String tableID = scan.next();

            //searching for table in priority queue
            for (int i = 0; i < tables.size(); i++) {

                //iterating to next element in priority queue
                if (iter.hasNext())
                    t = ((Table) iter.next());

                //given table found and its status is available
                if (t.getID().equals(tableID)) {
                    if (t.getStatus().equals("available")) {
                        student.setMyTable(tableID); //tableID assigned to student
                        student.setTableCounter(student.getTableCounter() + 1); //table counter incremented
                        t.setStatus("reserved"); //table status updated
                        t.setTime(60); //60 minutes
                        student.setInLine(false); //user not waiting for table, not in the queue
                        System.out.println(tableID + " is reserved for " + student.getName() + " " + student.getSurname() + " for 1 hour.");
                        return;
                    }
                    //given table not available (status is reserved or on break)
                    else {
                        System.out.println("Reservation denied by system.\nGiven " + tableID + " is already occupied.");
                        return;
                    }
                }
            }

            //Table ID not valid
            System.out.println("Reservation denied by system.\nGiven " + tableID + " is not exist in library.");
        }

        //All tables occupied
        //Asks user to wait for table, according to answer add user to waiting queue
        else{
            System.out.println("There is no empty table at the library." + tables.peek().getID() + " has " + tables.peek().getTime() + "minutes.Would you like to wait?(Y/N)");
            //Scanning user input
            Scanner input =  new Scanner(System.in);
            String wait = input.next();

            //user answer is YES for waiting queue
            //adding to waiting queue
            if(wait.equals("Y") || wait.equals("y")) {
                //user already in the waiting queue
                if(student.getInLine())
                    System.out.println("You are already in the waiting line for table.Please keep waiting.");
                //user added to waiting queue
                else {
                    tableReservationLine.add(student);
                    System.out.println("You are in the waiting line for table.");
                    student.setInLine(true);
                }
            }
            //user answer is NO for waiting queue
            else{
                System.out.println("Table reservation canceled.");
            }
        }
    }

    /**
     * Method to approve or deny table reservation time extend for 1 hour
     * If there is table already reserved for given student it approves , otherwise denies
     * @param student Student object to represent who wants to extend table time
     */
    protected static void approveTableTimeExtend(Student student){

        String tableID = student.getMyTable(); //store student's reserved tableID
        //Iterator for table priority queue
        Iterator iter = tables.iterator();
        Table t = new Table();

        //searching for table according to tableID
        for(int i=0; i<tables.size(); i++){
            //iterating to next element in priority queue
            if(iter.hasNext())
                t = ((Table)iter.next());

            //table found
            if(t.getID().equals(tableID)){
                t.setTime(t.getTime() + 60); //time extended for another 60 minutes (1 hour)
                System.out.println(tableID + "'s time extended 1 hour for student " + student.getName() + " " + student.getSurname() + " (Remaining time: " + t.getTime() + ")");
                return;
            }
        }

        //there is no reserved table for given student so request denied, process failed
        System.out.println("Table time extension error.");

    }


    /**
     * Method to approve or deny leaving table
     * If there is already reserved table for given student approves , otherwise denies
     * @param student Student object to represent who wants to leave table
     */
    protected static void approveLeaveTable(Student student){

        String tableID = student.getMyTable(); //store student's reserved tableID
        //Iterator for table priority queue
        Iterator iter = tables.iterator();
        Table t = new Table();

        //searching for table according to tableID
        for(int i=0; i<tables.size(); i++){
            if(iter.hasNext())
                //iterating to next element in priority queue
                t = ((Table)iter.next());

            //table found
            if(t.getID().equals(tableID)){
                t.setTime(0); //time initialized to 0
                t.setStatus("available"); //status updated to available
                System.out.println(tableID + "'s reservation is cancelled for student " + student.getName() + " " + student.getSurname() + ".");
                return;
            }
        }

        //Table not found, process failed
        System.out.println("Leaving table error.");

    }

    /**
     * Method to approve or deny taking break
     * If there is already reserved table for given student approves, otherwise denies request
     * @param student Student object to represent who wants to take a break
     */
    protected static void approveTableBreak(Student student){

        String tableID = student.getMyTable(); //store student's reserved tableID
        //Iterator for table priority queue
        Iterator iter = tables.iterator();
        Table t = new Table();

        //searching for table according to tableID
        for(int i=0; i<tables.size(); i++){

            if(iter.hasNext())
                //iterating to next element in priority queue
                t = ((Table)iter.next());

            //table found
            if(t.getID().equals(tableID)){

                //tables remaining time higher than 10
                //valid for break
                if(t.getTime()>10) {
                    t.setStatus("on break"); //status updated to on break
                    student.setOnBreak(true);
                    System.out.println(tableID + "'s status changed, you have 10 minutes for break.\n In case of violation librarian could drop your reservation.");
                }
                //tables remaining time less than 10
                //not valid  for break
                else {
                    System.out.println(tableID + "'s remaining time is " + t.getTime() + " minutes.\nEither you can extend time before break or you can leave the table.");
                }
                return;
            }
        }

        System.out.println("Take break for table error.");

    }

    /**
     * Method to print remaining time for given table
     * @param tableID String for table ID
     */
    protected static void checkTableTime(String tableID){

        //Iterator for table priority queue
        Iterator iter = tables.iterator();
        Table t = new Table();

        //searching for table according to tableID
        for(int i=0; i<tables.size(); i++) {

            if (iter.hasNext()) {
                //iterating to next element in priority queue
                t = ((Table) iter.next());

                //table found
                if (t.getID().equals(tableID))
                    System.out.println("Remaining time for " + tableID + ": " + t.getTime() + " minutes");
            }
        }
    }


    /**
     * Helper method for acceptRejectTableReservation method
     * Method to determine all tables are full or not
     * @return true if all tables full, otherwise false
     */
    private static boolean isFull(){
        //Iterator for table priority queue
        Iterator iter = tables.iterator();
        Table t = new Table();

        //searching for table according to tableID
        for(int i=0; i<tables.size(); i++) {

            if (iter.hasNext()) {
                //iterating to next element in priority queue
                t = ((Table) iter.next());

                //there is an empty table
                if (t.getTime() == 0)
                    return false;
            }
        }
        //there is no empty table
        return true;
    }


    //-LIBRARIAN (BOOK AND TABLE)--------------------------------------------------------------------------
    /**
     * Add book if Id is taken it sends message.
     *
     * @param a the a
     */
    static void addBook(Book a) {

        if (books.contains(a)) {
            System.out.println("This Id has been taken");
        } else {
            books.add(a);
            System.out.println(a + " has been added to the system");
        }

    }

    /**
     * Remove book.
     *
     * @param b the b
     */
    static void removeBook(Book b) {
        System.out.println(b);
        if (books.contains(b)) {
            System.out.println("Removed");
            books.delete(b);
        } else {
            System.out.println("This book not in the system");
        }
    }


    /**
     * Check id in Priorty queue and return this table if it isn't exist it return null
     *
     * @param Id the id
     * @return the table
     */
    static Table cancelTableReservation(String Id) {
        Iterator<Table> itr = tables.iterator();
        while (itr.hasNext())
            if (itr.next().getID() == Id)
                return itr.next();

        return null;
    }

    //-MENU---------------------------------------------------------------------------
    public void mainMenu(){
        Scanner sb = new Scanner(System.in);
        System.out.println("1) Sign Up\n2) Log In\n3) Exit\n");
        int type = sb.nextInt();

        switch (type){
            case 1 :
                signUp();
                mainMenu();
                break;
            case 2:
                System.out.println("1) Student\n2) Librarian\n3) Exit\n");
                type = sb.nextInt();
                switch (type){
                    case 1:
                        Student student = null;
                        studentMenu("student",student);
                        break;
                    case 2:
                        Librarian librarian = null;
                        librarianMenu("librarian",librarian);
                        break;
                    case 3:
                        break;
                    default:
                        System.out.println("Wrong Entry");
                        mainMenu();
                        break;
                }
            case 3:
                break;
            default:
                System.out.println("Wrong Entry");
                mainMenu();
                break;
        }
    }
    private void studentMenu(String type,Student student){
        if(student == null){
            student = (Student) logIn(type);
            if (student == null) {
                System.out.println("Log in process is failed.");
                mainMenu();
                return;
            }
        }
        System.out.println("\nStudent Menu\n");
        System.out.println("1) Edit Profile\n2) Book Option\n3) Table Option\n4) Exit");
        Scanner sb = new Scanner(System.in);
        int choice = sb.nextInt();

        switch (choice){
            case 1:
                profileOption((User) student);
                studentMenu(type,student);
                break;
            case 2:
                studentBookOption(student);
                break;
            case 3:
                studentTableOption(student);
                break;
            case 4:
                break;
            default:
                System.out.println("Wrong Entry\n");
                studentMenu(type,student);

        }
    }
    public void profileOption(User user){
        System.out.println("Profile Information\n");
        System.out.println("Name:      "+user.getName());
        System.out.println("Surname:   "+user.getSurname());
        System.out.println("ID:        "+user.getID());
        System.out.println("Password:  "+user.getPassword());
        System.out.println("\n1) Edit Name\n2) Edit Surname\n3) Edit Password\n4) Exit");

        Scanner sb = new Scanner(System.in);
        String change;
        int choice = sb.nextInt();

        switch (choice){
            case 1:
                System.out.println(" Enter the new name please ");
                change = sb.next();
                user.setName(change);
                profileOption(user);
                break;
            case 2:
                System.out.println(" Enter the new surname please ");
                change = sb.next();
                user.setSurname(change);
                profileOption(user);
                break;
            case 3:
                System.out.println(" Enter the new password please ");
                change = sb.next();
                user.setPassword(change);
                profileOption(user);
                break;
            case 4:
                break;
            default:
                System.out.println("Wrong choice. Please enter a new choice\n");
                profileOption(user);
                break;
        }
    }
    private void studentBookOption(Student user){
        System.out.println("\nBook Option Menu\n");
        System.out.println("1) Search Book\n2) Request Book\n3) Extend Time Book\n4) Check Book Time\n5) Exit");
        Scanner sb = new Scanner(System.in);
        int choice = sb.nextInt();

        switch (choice){
            case 1:
                user.searchBook(); //CHANGE PARAMETER!!!!!
                studentBookOption(user);
                break;
            case 2:
                user.requestBook(null); //CHANGE PARAMETER !!!!!!
                studentBookOption(user);
                break;
            case 3:
                user.extendTimeBook();
                studentBookOption(user);
                break;
            case 4:
                user.checkBookTime();
                studentBookOption(user);
                break;
            case 5:
                studentMenu("student",user);
                break;
            default:
                System.out.println("Wrong Entry\n");
                studentBookOption(user);
                break;
        }
    }
    private void studentTableOption(Student user){
        System.out.println("\nTable Option Menu\n");
        System.out.println("1) Show Table\n"+"2) Reserve Table\n" +
                "3) Take Break\n4) Leave Table\n5) Extend Time Table\n6) Check Table Time\n7) Exit");
        Scanner sb = new Scanner(System.in);
        int choice = sb.nextInt();
        String tableId;


        switch (choice){
            case 1:
                user.showTable();
                studentTableOption(user);
                break;
            case 2:
                user.reserveTable();
                studentTableOption(user);
                break;
            case 3:
                user.takeBreak();
                studentTableOption(user);
                break;
            case 4:
                user.leaveTable();
                studentTableOption(user);
                break;
            case 5:
                user.extendTimeTable();
                studentTableOption(user);
                break;
            case 6:
                user.checkTableTime();
                studentTableOption(user);
            case 7:
                studentMenu("student",user);
                break;
            default:
                System.out.println("Wrong Entry");
                studentTableOption(user);
                break;
        }
    }
    private void librarianMenu(String type,Librarian librarian){
        if(librarian == null){
            librarian = (Librarian) logIn(type);
            if (librarian == null) {
                System.out.println("Log in process is failed.");
                mainMenu();
                return;
            }
        }
        System.out.println("\nLibrarian Menu\n");
        System.out.println("1) Open your profile\n2) Book Option\n3) Table Option\n4) Exit");
        Scanner sb = new Scanner(System.in);
        int choice = sb.nextInt();

        switch (choice){
            case 1:
                profileOption((User) librarian);
                librarianMenu("librarian",librarian);
                break;
            case 2:
                librarianBookOption(librarian);
                break;
            case 3:
                librarianTableOption(librarian);
                break;
            case 4:
                break;
            default:
                System.out.println("Wrong Entry");
                librarianMenu(type,librarian);
                break;
        }

    }
    private void librarianBookOption(Librarian user){
        System.out.println("\nBook Option Menu\n");
        System.out.println("1) Search Book\n2) Add Book\n3) Remove Book\n4) Exit");
        Scanner sb = new Scanner(System.in);
        int choice = sb.nextInt();

        switch (choice){
            case 1:
                user.searchBook();
                librarianBookOption(user);
                break;
            case 2:
                user.addBook();
                librarianBookOption(user);
                break;
            case 3:
                user.removeBook();
                librarianBookOption(user);
                break;
            case 4:
                librarianMenu("librarian",user);
                break;
            default:
                System.out.println("Wrong Entry");
                librarianBookOption(user);
                break;
        }
    }
    private void librarianTableOption(Librarian user){
        System.out.println("\nTable Option Menu\n");
        System.out.println("1) Show Table\n"+"2) Cancel Table Reservation\n3) Exit");
        Scanner sb = new Scanner(System.in);
        int choice = sb.nextInt();

        switch (choice){
            case 1:
                user.showTable();
                librarianTableOption(user);
                break;
            case 2:
                user.cancelTableReservation();
                librarianTableOption(user);
            case 3:
                librarianMenu("librarian",user);
                break;
            default:
                System.out.println("Wrong Entry\n");
                break;
        }
    }


    //-----------------------------------------------------------------


    public void signUp(){
        int type;
        Scanner sb = new Scanner(System.in);
        System.out.println("1) Student\n2) Librarian\n3) Exit\n");
        type = sb.nextInt();
        switch (type){
            case 1:
                User newStudent = createUser(type);
                if(newStudent == null){
                    signUp();
                }else{
                    students.insert((Student) newStudent);
                }
                mainMenu();
                break;
            case 2:
                User newLibrarian = createUser(type);
                if(newLibrarian == null){
                    signUp();
                }else{
                    librarians.add((Librarian) newLibrarian);
                }
                mainMenu();
                break;
            case 3:
                mainMenu();
                break;
            default:
                System.out.println("Wrong entry");
                signUp();
                break;
        }
    }

    private User createUser(int type){
        String name;
        String surname;
        String ID;
        String password;
        Scanner sb = new Scanner(System.in);
        System.out.println("In case you want to leave menu, please write 'exit'.\n");

        System.out.println(" Enter the name please ");
        name = sb.next();
        if (name.equals("exit")){
            return null;
        }

        System.out.println(" Enter the surname please ");
        surname = sb.next();
        if (surname.equals("exit")){
            return null;
        }

        System.out.println(" Enter the ID please");
        ID = sb.next();
        if (ID.equals("exit")){
            return null;
        }

        System.out.println(" Enter the password please");
        password = sb.next();
        if (password.equals("exit")){
            return null;
        }

        if(type == 1) {
            return (User) new Student(name, surname, ID, password); //EXCHANGED PARAMETERS ID AND PASSWORD
        }else{
            return (User) new Librarian(name, surname, ID, password); //EXCHANGED PARAMETERS ID AND PASSWORD
        }
    }

    public User logIn(String type){
        User temp = IDControl(type,0);
        if (temp == null){
            return null;
        }else{
            boolean password = passwordControl(temp,0);

            if (password){
                return temp;
            }else{
                return null;
            }
        }
    }

    private User IDControl(String type,int i){
        if(i == 4){
            return null;
        }

        Scanner sb = new Scanner(System.in);
        System.out.println(" Entry the ID please ");
        String ID = sb.next();
        int index = 0;

        if (type.equals("student")){

            SkipList.SLNode tempHead = students.getHead();
            while (tempHead.hasNext()){
                Student st = ((Student)tempHead.next().data);
                if (st != null && st.getID().equals(ID)){
                    return st;
                }
               tempHead = tempHead.next();
            }

        }else{
            while (librarians.size()>index){
                if (librarians.get(index).getID().equals(ID)){
                    return  librarians.get(index);
                }
                index++;
            }
        }
        System.out.println("Wrong ID. Your remaining ID retry: " +(4-i-1));
        return IDControl(type,i+1);
    }

    private boolean passwordControl(User temp,int i){
        Scanner sb = new Scanner(System.in);
        System.out.println(" Enter password please ");
        String password = sb.next();

        if (i == 4){
            return false;
        }else{
            if (temp.getPassword().equals(password)){
                return true;
            }else{
                System.out.println(" Wrong password. Your remaining password retry:  " +(4-i-1));
                return passwordControl(temp,i+1);
            }
        }
    }

}
