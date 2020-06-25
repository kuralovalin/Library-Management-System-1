import java.util.*;

public class LibrarySystem {

    /** students also registers those who are registered as students. Skip List is sorted by id.*/
    private static SkipList<Student> students;
    /** librarians also registers those who are registered as students.*/
    private static ArrayList<Librarian> librarians;
    /**All tables are sorted with  PriorityQueue according to the duration of the student sitting at the table.*/
    private static PriorityQueue<Table> tables;
    /** All books coming to the system are stored in the AVL tree. While listing the AVL tree, unique ids are checked.*/
    private static AVLTree<Book> books = new AVLTree<>();
    /**Students who want to buy a table after all tables are filled are registered in this tableReservationLine.
     tableReservationLine is a queue.*/
    private static Queue<Student> tableReservationLine;
    /*** Table reminder for warning*/
    static TableReminder t_reminder;
    /**Book reminder for warning*/
    static BookReminder  b_reminder;
    /** Library Map*/
    private static ListGraph myGraph;

    /**
     * Default constructor of LibrarySystem
     */
    public LibrarySystem(){
        tables = new PriorityQueue<Table>();
        tableReservationLine = new LinkedList<>();
        initializeTableData(50);
        initializeStudentData();
        initializeBookData();
        initializeLibrarianData();
    }

    static {
        students = new SkipList<>();
        librarians = new ArrayList<>();
        myGraph = new ListGraph(6,false);
    }

    //-STUDENT (TABLE)--------------------------------------------------------------------------

    /**
     * Method to initialize table data to priority queue according to their time values
     * Every table status is "available" and time is 0 at the beginning
     * @param capacity Integer for priority queue capacity (number of tables)
     */
    private void initializeTableData(int capacity){

        //Initializing table objects to priority queue
       for(int i=0; i<capacity-5 ; i++){
            String tableID = ("Table" + (Integer)(i+1));
            //Initializing each table with -> TableX, Reserved, Time(60)
            //puts every object to priority queue
            tables.offer(new Table(tableID, "reserved", 60));
        }
        //initialize 3 table with different time values
        tables.offer(new Table("Table46", "reserved", 20));
        tables.offer(new Table("Table47", "reserved", 40));
        tables.offer(new Table("Table48", "reserved", 50));
        //initialize 2 table empty
        tables.offer(new Table("Table49", "reserved", 30));
        tables.offer(new Table("Table50", "available", 0));

    }

    /**
     * Method to initialize student datas to skip list according to their IDs
     * Every student's name,surname,ID and password initialized
     */
    private void initializeStudentData(){

        students.insert(new  Student("Ezgi", "Cakir", "151044054", "gtuO"));
        students.insert(new  Student("Alina", "Kuralova", "171044094", "gtuO"));
        students.insert(new  Student("Furkan", "Aydın", "3", "gtuO"));
        students.insert(new  Student("Oguzhan", "Senturk", "151044067", "gtuO"));
        students.insert(new  Student("Selimhan", "Meral", "161044009", "gtuO"));
        students.insert(new  Student("Sarwar", "Hossain", "6", "gtuO"));
    }

    /**
     * Method to initialize book datas to AVL tree according to their IDs
     * Every book's name,author name,ID , time and status initialized
     */
    private  void initializeBookData(){
        books.add(new Book("N11", "Novel", "Suc ve Ceza", "Fyodor Dostoyevski", "available",0));
        books.add(new Book("N12", "Novel", "Yer Altından Notlar", "Fyodor Dostoyevski", "available",0));
        books.add(new Book("S21", "Story",  "Kurt Mantolu Madonna", "Sabahattin Ali", "available" , 0));
        books.add(new Book("S22", "Story",  "Kuyucaklı Yusuf","Sabahattin Ali", "available" , 0 ));
        books.add(new Book("N31", "Novel",  "Masumiyet Muzesi", "Orhan Pamuk", "available" , 0));
        books.add(new Book("N32", "Novel",  "Dava","Franz Kafka", "available" , 0 ));
        books.add(new Book("N34", "Novel",  "1984", "George Orwell", "available" , 0));
        books.add(new Book("H41", "History",  "Nutuk", "Mustafa Kemal Ataturk", "available" , 0));
        books.add(new Book("S23", "Story",  "Sırca Kosk", "Sabahattin Ali", "available" , 0));
        books.add(new Book("S24", "Story",  "Stefan Zweig", "Amok Kosucusu", "available" , 0));
    }

    /**
     * Method to initialize librarian datas to skip list according to their IDs
     * Every librarian's name,surname,ID and password initialized
     */
    private  void initializeLibrarianData(){
        librarians.add(new Librarian("Ali", "Boz", "1", "gtuL") );
        librarians.add(new Librarian("Idil", "Kaya", "2", "gtuL") );
        librarians.add(new Librarian("Zeynep", "Yılmaz", "3", "gtuL") );
        librarians.add(new Librarian("Murat", "Çelik", "4", "gtuL") );
        librarians.add(new Librarian("Eda", "Yazar", "5", "gtuL") );
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
            System.out.println(User.ANSI_BLUE +"Enter tableID to reserve." + User.ANSI_RESET);
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
                        rearrangeTableOrder(); //rearrange order of tables according to times
                        student.setInLine(false); //user not waiting for table, not in the queue
                        System.out.println(User.ANSI_GREEN + tableID + " is reserved for " + student.getName()
                                + " " + student.getSurname() + " for 1 hour." + User.ANSI_RESET);
                        ListGraph.map(myGraph,t.getID().substring(5));
                        t_reminder = new TableReminder(t.getTime()- 10,t,student); //time reminder
                        return;
                    }
                    //given table not available (status is reserved or on break)
                    else {
                        System.out.println(User.ANSI_RED +"Reservation denied by system.\n" +
                                "Given " + tableID + " is already occupied." + User.ANSI_RESET);
                        return;
                    }
                }
            }

            //Table ID not valid
            System.out.println(User.ANSI_RED + "Reservation denied by system.\n" +
                    "Given " + tableID + " is not exist in library." + User.ANSI_RESET);
        }

        //All tables occupied
        //Asks user to wait for table, according to answer add user to waiting queue
        else{
            System.out.println(User.ANSI_BLUE + "There is no empty table at the library." + tables.peek().getID()
                    + " has " + tables.peek().getTime() + "minutes.Would you like to wait?(Y/N)"+ User.ANSI_RESET);
            //Scanning user input
            Scanner input =  new Scanner(System.in);
            String wait = input.next();

            //user answer is YES for waiting queue
            //adding to waiting queue
            if(wait.equals("Y") || wait.equals("y")) {
                //user already in the waiting queue
                if(student.getInLine())
                    System.out.println(User.ANSI_GREEN +"You are already in the waiting line for table.Please keep waiting."+ User.ANSI_RESET);
                //user added to waiting queue
                else {
                    tableReservationLine.add(student);
                    System.out.println(User.ANSI_GREEN +"You are in the waiting line for table."+ User.ANSI_RESET);
                    student.setInLine(true);
                }
            }
            //user answer is NO for waiting queue
            else{
                System.out.println(User.ANSI_GREEN + "Table reservation canceled."+ User.ANSI_RESET);
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
                rearrangeTableOrder();
                System.out.println(User.ANSI_GREEN + tableID + "'s time extended 1 hour for student " + student.getName()
                        + " " + student.getSurname() + " (Remaining time: " + t.getTime() + ")" + User.ANSI_RESET);
                //time extend reminder
                t_reminder.timer.cancel();
                t_reminder.timer2.cancel();
                t_reminder = new TableReminder(t.getTime()-10,t,student);
                return;
            }
        }

        //there is no reserved table for given student so request denied, process failed
        System.out.println(User.ANSI_RED +"Table time extension error."+ User.ANSI_RESET);
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
                System.out.println(User.ANSI_GREEN + tableID + "'s reservation is cancelled for student " +
                        student.getName() + " " + student.getSurname() + "." + User.ANSI_RESET);

                if (!tableReservationLine.isEmpty()) {
                    Student temp = tableReservationLine.poll();
                    temp.setTableCounter(1);
                    temp.setMyTable(tableID);
                    t.setTime(60);
                    t.setStatus("reserved"); //status updated to available
                    System.out.println(User.ANSI_YELLOW + "Reservation pulled from queue for  " + t.getID() +
                            " to " + temp.getName() + " " + temp.getSurname() + User.ANSI_RESET);
                    t_reminder.timer.cancel();
                    t_reminder.timer2.cancel();
                    t_reminder = new TableReminder(t.getTime()-10,t,temp);
                }
                rearrangeTableOrder();
                return;
            }
        }
        //Table not found, process failed
        System.out.println(User.ANSI_RED + "Leaving table error."+ User.ANSI_RESET);
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
                    System.out.println(User.ANSI_GREEN + tableID + "'s status changed, you have 10 minutes for break.\n " +
                            "In case of violation librarian could drop your reservation." + User.ANSI_RESET);
                }
                //tables remaining time less than 10
                //not valid  for break
                else {
                    System.out.println(User.ANSI_RED +tableID + "'s remaining time is " + t.getTime() + " minutes.\n" +
                            "Either you can extend time before break or you can leave the table." + User.ANSI_RESET);
                }
                return;
            }
        }
        System.out.println(User.ANSI_RED  + "Take break for table error." + User.ANSI_RESET);

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
                    System.out.println(User.ANSI_GREEN + "Remaining time for " + tableID + ": "
                            + t.getTime() + " minutes" + User.ANSI_RESET);
            }
        }
    }

    /**
     * Shows all tables with its information
     */
    protected static void showTable(){
        Iterator iter = tables.iterator();
        Table t = new Table();
        //searching for table according to tableID
        for(int i=0; i<tables.size(); i++) {
            if (iter.hasNext()) {
                //iterating to next element in priority queue
                t = ((Table) iter.next());
                System.out.println(User.ANSI_GREEN + "TableID: " + t.getID() + "  Status: "
                        + t.getStatus() + "  Time: " + t.getTime() + User.ANSI_RESET);
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

    /**
     * Method to rearrange tables according to updated remaining time values
     */
    public static void rearrangeTableOrder(){
        Table[] oldTables = new Table[tables.size()];
        int cap = tables.size();

        for(int i=0; i<cap; i++) {
            oldTables[i] = tables.poll();
        }

        for(int i=0; i<cap; i++)
            tables.offer(oldTables[i]);
    }

    //-STUDENT(BOOK) OP------------------------------------------------------------------------------------

    /**
     * Method to approve book request
     * @param student Student object which represents student
     * @param bookID String fpr book ID
     */
    protected static Book acceptRejectBookRequest (Student student, String bookID) {

        Book book = books.find(bookID);

        if (book != null) {
            if(book.isAvailable()) {
                book.changeStatus("reserved");
                book.setTime(book.getTime() + 15);
                System.out.println(User.ANSI_GREEN +bookID + " is reserved for "+ student.getName()
                        + " " + student.getSurname() + " for 15 days."+ User.ANSI_RESET);
                b_reminder = new BookReminder(book.getTime()- 5,book,student); //time reminder
                return new Book(book);
            }
            else{
                System.out.println(User.ANSI_RED + bookID + " already taken." +  User.ANSI_RESET);
                return null;
            }
        }

        else
            System.out.println(User.ANSI_RED + "There is no book in the library for given bookID." + User.ANSI_RESET);
             return null;
    }

    /**
     * Method to approve time extend request of student
     * @param student Student object which represents student
     * @param bookID String fpr book ID
     */
  protected static void approveBookTimeExtend(Student student, String bookID){
        //searching for book
      Book book = books.find(bookID);
        if(book != null && book.getTimeExtendCounter() < 3){
            book.setTime(book.getTime() + 15);    //time extended for 15
            System.out.println(User.ANSI_GREEN +bookID + "'s time extended 15 days for student " + student.getName() +
                    " " + student.getSurname() + " (Remaining time: " + book.getTime() + ")" + User.ANSI_RESET);
            book.setTimeExtendCounter(book.getTimeExtendCounter() + 1);
            b_reminder.timer.cancel();
            b_reminder.timer2.cancel();
            b_reminder = new BookReminder(book.getTime()-5,book,student);
            return;

        }
        System.out.println(User.ANSI_RED + "You cannot extend the book time" + User.ANSI_RESET);
    }


    /**
     * Method to show student's book(s) informations
     * It prints books according to their time values (lower to higher)
     * @param bookIDs Array list for student's reserved book(s) ID(s)
     */
    protected static void showBookInfo(ArrayList<String> bookIDs){

        //Integer[] bookTimes = new Integer[bookIDs.size()]; //array to hold time values
        Book [] sortedBooks = new Book[bookIDs.size()]; //array to hold time values

        //storing book objects time values and book objects
        for(int i=0; i<bookIDs.size(); i++){
            Book book = books.find(bookIDs.get(i));
            // bookTimes[i] = book.getTime();
            sortedBooks[i]=book;
        }

        //sorting time values (largee to smaller)
        QuickSort.quickSort(sortedBooks);

        for(int i=sortedBooks.length-1; i>=0; i--) {
            Book book = sortedBooks[i];
            if (book != null) {
                System.out.println(User.ANSI_GREEN + "\nBook Name      : " + book.getName()+ User.ANSI_RESET);
                System.out.println(User.ANSI_GREEN + "Author         : " + book.getAuthor()+ User.ANSI_RESET);
                System.out.println(User.ANSI_GREEN + "Book ID        : " + book.getID()+ User.ANSI_RESET);
                System.out.println(User.ANSI_GREEN + "Remaining Time : " + book.getTime()+ User.ANSI_RESET);
                if(bookIDs.size() != 1)
                    System.out.println(User.ANSI_GREEN + " " + User.ANSI_RESET);
            }
        }
    }

    /**
     * This method travel tree and add them to a list after for Id it categorize books for categories
     * and for authors and we implemented MultiMap class for it it hold books in same Key in MultiMap
     * and it show the book's details if this category or author doesn't exist
     *
     * it doesn't print and print error to screen
     */
    protected static void showBook(){
        Scanner scn = new Scanner(System.in);
        List<Book> book = books.inorderList();
        MultiMap<Character, Book> category = new MultiMap();
        for (int i = 0; i < book.size(); i++) {
            category.put(book.get(i).getID().charAt(0),book.get(i));
        }
        MultiMap<String, Book> author = new MultiMap();
        for (int i = 0; i < book.size(); i++) {
            author.put(book.get(i).getAuthor(),book.get(i));
        }
        //System.out.println("\n" + category.entrySet());
        System.out.println(User.ANSI_BLUE+ "1) With Category" +User.ANSI_RESET);
        System.out.println(User.ANSI_BLUE+ "2) With Author"+ User.ANSI_RESET);
        System.out.println(User.ANSI_BLUE+ "3) All Books" + User.ANSI_RESET);
        System.out.println(User.ANSI_BLUE+ "4) Exit" + User.ANSI_RESET);
        String  inp = scn.nextLine();

        if(inp.equals("1")) {
            System.out.println("Please enter a category:" );
            String b = scn.nextLine();
            String x = b.substring(0, 1).toUpperCase() + b.substring(1);
            if(category.get(x.charAt(0)) != null)
                System.out.println(category.get(x.charAt(0)));
            else
                System.out.println(User.ANSI_RED + "There is not book in this category" + User.ANSI_RESET );
        }
        else if(inp.equals("2")) {
            System.out.println("Please enter a author (name and surname):" );
            String b = scn.nextLine();
            String x = b.substring(0, 1).toUpperCase() + b.substring(1);
            if(author.get(x) != null)
                System.out.println(author.get(x));
            else
                System.out.println(User.ANSI_RED  + "There is not book of this Author" + User.ANSI_RESET);

        }
        else if(inp.equals("3")) {
            for (int i = 0; i <book.size() ; i++) {
                System.out.print(book.get(i));
            }
        }
        else if(inp.equals("4")) {
            return;
        }
        else {
            System.out.println(User.ANSI_RED + "Wrong entry.Please try again." + User.ANSI_RESET);
            showBook();
        }
    }
    /**
     *  It search book in library with name of book and show book's details.
     *  it convert tree as a list and find inside
     * @param name of book
     */
    protected static void searchBook(String name) {
        List<Book> book = books.inorderList();
        for (int i = 0; i < book.size(); i++) {
            if(book.get(i).getName().equals(name)) {
                System.out.println(User.ANSI_GREEN+"This book is in library " + User.ANSI_RESET);
                System.out.println(User.ANSI_GREEN+book.get(i).getName() + "'s ID is  " + book.get(i).getID()+ User.ANSI_RESET) ;
                return;
            }
        }
        System.out.println(User.ANSI_RED+ "This book is not in library " + User.ANSI_RESET);

    }
    
    //-LIBRARIAN (BOOK AND TABLE)--------------------------------------------------------------------------
    /**
     *Check Id if This id is taken or not
     * @param id of book
     */
    protected static boolean CheckId(String id){
        if(books.find(id) != null)
            return false;
        else
            return true;
    }

    /**
     * Add book if Id is taken it sends message.
     *
     * @param a the a
     */
    protected static boolean addBook(Book a) {

        if (books.contains(a)) {
            System.out.println(User.ANSI_RED + "This ID has been taken" + User.ANSI_RESET);
            return false;
        } else {
            books.add(a);
            System.out.println(User.ANSI_GREEN + a + " has been added to the system" + User.ANSI_RESET);
            return true;
        }
    }

    /**
     * This method checks book in system or not if it is in system it deletes from system and tree
     *  if it is not exist it print error to screen
     *  And we override find method for finding books in tree with Id
     * @param Id is Book's Id
     */
    protected static void removeBook(String Id) {
        if(books.find(Id) != null ) {
            books.remove(books.find(Id));
            System.out.println(User.ANSI_GREEN + "Book removed" + User.ANSI_RESET);
        }else
            System.out.println(User.ANSI_RED + "This book not in the system" + User.ANSI_RESET);
    }



    /**
     * Check id in Priorty queue and return this table if it isn't exist it return null
     *
     * @param Id the id
     * @return the table
     */
    protected static Table cancelTableReservation(String Id) {
        Iterator<Table> itr = tables.iterator();

        while (itr.hasNext()) {
           Table t = itr.next();
            if (t.getID().equals(Id))
                return t;
        }
        return null;
    }

    //-MENU---------------------------------------------------------------------------
    /***
     * It is the main menu that appears first after the program is opened.There are 3 options.
     * These are log in operation, sign up operation and exit. If the user enter the 1 (exit) program is shut down.
     * If the user signs in, the sign up menu opens.
     * If the log is done, the student or employee is asked.
     * There are also 3 options for log in operation. These are student log in, librarian log in and exit.
     * If the user does the student operation, the student menu opens.
     * If the Librarian process, the librarian menu opens. Return to the upper menu of Exit course
     */
    public void mainMenu(){
        Scanner sb = new Scanner(System.in);
        System.out.println(User.ANSI_YELLOW + "---------------------------------" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "|   LIBRARY MANAGEMENT SYSTEM   |" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "---------------------------------" + User.ANSI_RESET);
        System.out.println(User.ANSI_BLUE + "1) Sign Up\n2) Log In\n3) Exit\n" + User.ANSI_RESET);
        String type = sb.next();

        switch (type){
            case "1" :
                signUp();
                mainMenu();
                break;
            case "2":
                System.out.println(User.ANSI_YELLOW + "------------" + User.ANSI_RESET);
                System.out.println(User.ANSI_YELLOW + "|  LOG IN  |" + User.ANSI_RESET);
                System.out.println(User.ANSI_YELLOW + "------------" + User.ANSI_RESET);
                System.out.println(User.ANSI_BLUE + "1) Student\n2) Librarian\n3) Exit\n"+ User.ANSI_RESET);
                type = sb.next();
                switch (type){
                    case "1":
                        Student student = null;
                        studentMenu("student",student);
                        mainMenu();
                        break;
                    case "2":
                        Librarian librarian = null;
                        librarianMenu("librarian",librarian);
                        mainMenu();
                        break;
                    case "3":
                        mainMenu();
                        break;
                    default:
                        System.out.println(User.ANSI_RED + "Wrong Entry" + User.ANSI_RESET);
                        mainMenu();
                        break;
                }
            case "3":
                break;
            default:
                System.out.println(User.ANSI_RED + "Wrong Entry" + User.ANSI_RESET);
                mainMenu();
                break;
        }
    }
    /***
     * If the incoming student object is not null, the transaction is performed.
     * There are 3 basic operations that the student will do.
     * The first is exit. If the user says exit, he returns to a top menu.
     * The second is Book Option. If the user chooses the Book Option option, the Book menu opens.
     * The third basic operation of the student is the Table option,
     * if the table option is selected, the table menu opens and finally, in the profile option,
     * the student can view and change their profile information.
     * @param type student or librarian
     * @param student real object object of the student who program user.
     */
    private void studentMenu(String type,Student student){
        if(student == null){
            student = (Student) logIn(type);
            if (student == null) {
                System.out.println(User.ANSI_RED +"Log in process is failed." + User.ANSI_RESET);
                mainMenu();
                return;
            }
        }
        System.out.println(User.ANSI_YELLOW + "\n-----------------------------" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "\tStudent " + student.getName() + " " + student.getSurname() + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "-----------------------------" + User.ANSI_RESET);
        System.out.println(User.ANSI_BLUE + "1) Open your Profile\n2) Book Option\n3) Table Option\n4) Exit"+ User.ANSI_RESET);
        Scanner sb = new Scanner(System.in);
        String choice = sb.next();

        switch (choice){
            case "1":
                profileOption((User) student);
                studentMenu(type,student);
                break;
            case "2":
                studentBookOption(student);
                studentMenu(type,student);
                break;
            case "3":
                studentTableOption(student);
                studentMenu(type,student);
                break;
            case "4":
                break;
            default:
                System.out.println(User.ANSI_RED + "Wrong Entry\n" + User.ANSI_RESET);
                studentMenu(type,student);

        }
    }
    /***
     * In profile option, all profile information is printed on the screen. There are three options.
     * These are name, surname and password change. With the option 1, the password is changed with the name 2 option and
     * the last name 3 option. 4 option to exit and return to the upper menu
     * @param user user object to process
     */
    public void profileOption(User user){
        System.out.println(User.ANSI_GREEN + "\nProfile Information\n" + User.ANSI_RESET);
        System.out.println(User.ANSI_GREEN +"Name:      "+user.getName() + User.ANSI_RESET);
        System.out.println(User.ANSI_GREEN +"Surname:   "+user.getSurname() + User.ANSI_RESET);
        System.out.println(User.ANSI_GREEN +"ID:        "+user.getID() + User.ANSI_RESET);
        System.out.println(User.ANSI_GREEN +"Password:  "+user.getPassword() + User.ANSI_RESET);
        System.out.println(User.ANSI_BLUE +"\n1) Edit Name\n2) Edit Surname\n3) Edit Password\n4) Exit" + User.ANSI_RESET);

        Scanner sb = new Scanner(System.in);
        String change;
        String choice = sb.next();

        switch (choice){
            case "1":
                System.out.println(User.ANSI_BLUE + "Enter the new name please "+ User.ANSI_RESET);
                change = sb.next();
                user.setName(change);
                profileOption(user);
                break;
            case "2":
                System.out.println(User.ANSI_BLUE + "Enter the new surname please "+ User.ANSI_RESET);
                change = sb.next();
                user.setSurname(change);
                profileOption(user);
                break;
            case "3":
                System.out.println(User.ANSI_BLUE + "Enter the new password please "+ User.ANSI_RESET);
                change = sb.next();
                user.setPassword(change);
                profileOption(user);
                break;
            case "4":
                break;
            default:
                System.out.println(User.ANSI_RED + "Wrong choice. Please enter a new choice\n" + User.ANSI_RESET);
                profileOption(user);
                break;
        }
    }
    /***
     * There are 6 options for the Student Book menu. 1 show book.
     * You can see all the books in the library with the show book option.
     * 2 Search Book. With the Search Book option, you can search by the name of the author,
     * by the name of the book, or by the category of books. 3 Request Book.
     * With Request Book, you may want to buy a book that is also in the library.
     * If you are eligible to request a book, it is given to you. 4 Extend Time Book.
     * Each book is given for 15 days. In this option, you can extend the book's stay. 5 Show Book info.
     * With this option, you can access the information of your books and the duration of the book. 6 exit.
     * With the exit option you will return to the upper menu
     * @param user user object to process
     */
    private void studentBookOption(Student user){
        System.out.println(User.ANSI_YELLOW + "------------------" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "|  BOOK OPTIONS  |"  + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "------------------" + User.ANSI_RESET);
        System.out.println(User.ANSI_BLUE + "1) Show Books\n2) Search Book\n3) Request Book\n4) Extend Time Book\n5) Show Book Information\n6) Exit" + User.ANSI_RESET);
        Scanner sb = new Scanner(System.in);
        String choice = sb.next();
        switch (choice){
            case "1":
                user.showBook();
                studentBookOption(user);
                break;
            case "2":
                user.searchBook();
                studentBookOption(user);
                break;
            case "3":
                user.requestBook();
                studentBookOption(user);
                break;
            case "4":
                user.extendTimeBook();
                studentBookOption(user);
                break;
            case "5":
                user.showBookInfo();
                studentBookOption(user);
                break;
            case "6":
                break;
            default:
                System.out.println(User.ANSI_RED + "Wrong Entry\n"+ User.ANSI_RESET);
                studentBookOption(user);
                break;
        }
    }
    /***
     * There are 7 options for the student table menu.
     * You can see all tables in the library with 1 show table show table option.
     * With the 2 reserve table option, you can request any available table. 3 You can take a break with Take a break.
     * 4 You can leave the table with Leave table. With 5 extend time tables, you can extend your stay on the table.
     * The duration of stay at the table for each user is 1 hour.
     * With Check table time, you can learn the time you stay at the table. With 7 exit you will return to the upper menu.
     * @param user user object to process
     */
    private void studentTableOption(Student user){
        System.out.println(User.ANSI_YELLOW + "-------------------" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "|  TABLE OPTIONS  |"  + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "-------------------" + User.ANSI_RESET);
        System.out.println(User.ANSI_BLUE +"1) Show Table\n"+"2) Reserve Table\n" +
                "3) Take Break\n4) Leave Table\n5) Extend Time Table\n6) Check Table Time\n7) Exit" + User.ANSI_RESET);
        Scanner sb = new Scanner(System.in);
        String choice = sb.next();

        switch (choice){
            case "1":
                user.showTable();
                studentTableOption(user);
                break;
            case "2":
                user.reserveTable();
                studentTableOption(user);
                break;
            case "3":
                user.takeBreak();
                studentTableOption(user);
                break;
            case "4":
                user.leaveTable();
                studentTableOption(user);
                break;
            case "5":
                user.extendTimeTable();
                studentTableOption(user);
                break;
            case "6":
                user.checkTableTime();
                studentTableOption(user);
            case "7":
                break;
            default:
                System.out.println(User.ANSI_RED + "Wrong Entry" + User.ANSI_RESET);
                studentTableOption(user);
                break;
        }
    }
    /***
     * The librarian menu has 3 options. With 1 open your profile, you can view and edit all your profile information.
     * With 2 Book option menu, you go to the librarian's book menu.
     * With 3 table options, you go to the librarian's table menu. With 4 exits you will return to the upper menu
     * @param type student or librarian
     * @param librarian user object to process
     */
    private void librarianMenu(String type,Librarian librarian){
        if(librarian == null){
            librarian = (Librarian) logIn(type);
            if (librarian == null) {
                System.out.println(User.ANSI_RED +"Log in process is failed."+ User.ANSI_RESET);
                mainMenu();
                return;
            }
        }
        System.out.println(User.ANSI_YELLOW + "\n-----------------------------" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "\tLibrarian " + librarian.getName() + " " + librarian.getSurname() + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "-----------------------------" + User.ANSI_RESET);
        System.out.println(User.ANSI_BLUE + "1) Open your profile\n2) Book Option\n3) Table Option\n4) Exit" + User.ANSI_RESET);
        Scanner sb = new Scanner(System.in);
        String choice = sb.next();

        switch (choice){
            case "1":
                profileOption((User) librarian);
                librarianMenu("librarian",librarian);
                break;
            case "2":
                librarianBookOption(librarian);
                librarianMenu("librarian",librarian);
                break;
            case "3":
                librarianTableOption(librarian);
                librarianMenu("librarian",librarian);
                break;
            case "4":
                break;
            default:
                System.out.println(User.ANSI_RED + "Wrong Entry" + User.ANSI_RESET );
                librarianMenu(type,librarian);
                break;
        }
    }
    /***
     * There are 5 operations you can do in the librarian book menu.
     * With 1 show books, you can see all the books in the library.
     * With 2 search book, you can search for a specific book. With 3 add books, you can add new books to the library.
     * With 4 exit you will return to the upper menu.
     * @param user user object to process
     */
    private void librarianBookOption(Librarian user){
        System.out.println(User.ANSI_YELLOW + "------------------" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "|  BOOK OPTIONS  |"  + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "------------------" + User.ANSI_RESET);
        System.out.println(User.ANSI_BLUE + "1) Show Books\n2) Search Book\n3) Add Book\n4) Remove Book\n5) Exit" + User.ANSI_RESET);
        Scanner sb = new Scanner(System.in);
        String choice = sb.next();

        switch (choice){
            case "1":
                user.showBook();
                librarianBookOption(user);
                break;
            case "2":
                user.searchBook();
                librarianBookOption(user);
                break;
            case "3":
                user.addBook();
                librarianBookOption(user);
                break;
            case "4":
                user.removeBook();
                librarianBookOption(user);
                break;
            case "5":
                break;
            default:
                System.out.println(User.ANSI_RED +"Wrong Entry"+ User.ANSI_RESET );
                librarianBookOption(user);
                break;
        }
    }
    /***
     * There are 3 transactions in the Librarian table option menu.
     * With 1 show table you can see all tables and their status. With 2 cancel table reservation,
     * you can cancel the reservation of a student with a table. With 3 exit you will return to the upper menu.
     * @param user user object to process
     */
    private void librarianTableOption(Librarian user){
        System.out.println(User.ANSI_YELLOW + "-------------------" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "|  TABLE OPTIONS  |"  + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "-------------------" + User.ANSI_RESET);
        System.out.println(User.ANSI_BLUE + "1) Show Table\n"+"2) Cancel Table Reservation\n3) Exit"+ User.ANSI_RESET);
        Scanner sb = new Scanner(System.in);
        String choice = sb.next();

        switch (choice){
            case "1":
                user.showTable();
                librarianTableOption(user);
                break;
            case "2":
                user.cancelTableReservation();
                librarianTableOption(user);
            case "3":
                break;
            default:
                System.out.println(User.ANSI_RED +"Wrong Entry\n"+ User.ANSI_RESET);
                break;
        }
    }

    //-----------------------------------------------------------------
    /***
     * 3 options are registered to the system with the sign up menu, one of them is student.
     * With Student, you enroll in the student system.
     * secondly, with librarian you register to the librarian system and thirdly with exit you return to 1 upper menu
     */
    public void signUp(){
        String  type;
        Scanner sb = new Scanner(System.in);
        System.out.println(User.ANSI_YELLOW + "-------------" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "|  SIGN UP  |" + User.ANSI_RESET);
        System.out.println(User.ANSI_YELLOW + "-------------" + User.ANSI_RESET);
        System.out.println(User.ANSI_BLUE +"1) Student\n2) Librarian\n3) Exit\n"+ User.ANSI_RESET);
        type = sb.next();
        switch (type){
            case "1":
                User newStudent = createUser(type);
                if(newStudent == null){
                    signUp();
                }else{
                    students.insert((Student) newStudent);
                }
                break;
            case "2":
                User newLibrarian = createUser(type);
                if(newLibrarian == null){
                    signUp();
                }else{
                    librarians.add((Librarian) newLibrarian);
                }
                break;
            case "3":
                break;
            default:
                System.out.println(User.ANSI_RED+"Wrong entry"+ User.ANSI_RESET);
                signUp();
                break;
        }
    }
    /***
     * It takes input from the sign up menu as a parameter.
     * If parameter 1 is the student, the parameter 2 is the librarian.
     * Name surname password id information is entered. In case any input is entered, exit is returned to the upper menu.
     * @param type User user is abstract class parent for student class and librarian class
     * @return to user in student list or librarian list.
     */
    private User createUser(String type){
        String name;
        String surname;
        String ID;
        String password;
        Scanner sb = new Scanner(System.in);
        System.out.println(User.ANSI_BLUE +"In case you want to leave menu, please write 'exit'.\n"+ User.ANSI_RESET );

        System.out.println(User.ANSI_BLUE +"Enter the name please"+ User.ANSI_RESET );
        name = sb.next();
        char[] chars = name.toCharArray();

        StringBuilder sB = new StringBuilder();
        for(char c : chars){
            if(Character.isDigit(c)){
                System.out.println(User.ANSI_RED + "Please enter name correctly, it should not contain any number." + User.ANSI_RESET);
                return null;
            }
        }
        if (name.equals("exit")){
            return null;
        }

        System.out.println(User.ANSI_BLUE +"Enter the surname please"+ User.ANSI_RESET );
        surname = sb.next();
        char[] chars2 = surname.toCharArray();
        for(char c : chars2){
            if(Character.isDigit(c)){
                System.out.println(User.ANSI_RED +"Please enter surname correctly, it should not contain any number." + User.ANSI_RESET);
                return null;
            }
        }
        if (surname.equals("exit")){
            return null;
        }

        System.out.println(User.ANSI_BLUE +"Enter the ID please"+ User.ANSI_RESET );
        ID = sb.next();
        if (ID.equals("exit")){
            return null;
        }

        System.out.println(User.ANSI_BLUE +"Enter the password please"+ User.ANSI_RESET );
        password = sb.next();
        if (password.equals("exit")){
            return null;
        }

        if(type.equals("1") ){
            return (User) new Student(name, surname, ID, password);
        }else{
            return (User) new Librarian(name, surname, ID, password);
        }
    }
    /***
     * In accordance with the information received, the user object to be processed is returned.
     * First of all, the id entered by the user is checked in the system. and in total they have 4 rights to enter id.
     * If the entered id is in the system, that object temp is thrown to a reference. password control is done.
     * The password received from the user is checked with the password in temp. the user has a total of 4 rights.
     * @param type student or librarian
     * @return  user object to process
     */
    public User logIn(String type){

        System.out.println(User.ANSI_BLUE +"In case you want to leave, please write 'exit'.\n"+ User.ANSI_RESET );

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
    /***
     * It takes 2 parameters. 1. Have the status of the user.
     * If the user is a student, a search is made for the student skip list with the entered id.
     * If the object is found, it is returned. If not found, it is null returned. for the user librarian,
     * a search is made in the arraylist.
     * @param type student or librarian
     * @param i right to try
     * @return user object or null
     */
    private User IDControl(String type,int i){

        if(i == 4){
            return null;
        }

        Scanner sb = new Scanner(System.in);
        System.out.println(User.ANSI_BLUE+"Entry the ID please"+ User.ANSI_RESET );
        String ID = sb.next();
        int index = 0;

        if(ID.equals("exit"))
            return null;

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
        System.out.println(User.ANSI_RED + "Wrong ID. Your remaining ID retry: " +(4-i-1) + User.ANSI_RESET);
        return IDControl(type,i+1);
    }
    /***
     * The password entered with the password that comes as a parameter is checked.
     * If passwords are the same, true is different, false is returned. The total trial right is 4.
     * @param temp user object whose password will be checked
     * @param i right to try
     * @return return true if equals password else false.
     */
    private boolean passwordControl(User temp,int i){
        Scanner sb = new Scanner(System.in);
        System.out.println(User.ANSI_BLUE + "Enter password please" + User.ANSI_RESET );
        String password = sb.next();
        if(password.equals("exit"))
            return false;

        if (i == 4){
            return false;
        }else{
            if (temp.getPassword().equals(password)){
                return true;
            }else{
                System.out.println(User.ANSI_RED + "Wrong password. Your remaining password retry:  " +(4-i-1) + User.ANSI_RESET);
                return passwordControl(temp,i+1);
            }
        }
    }

}
