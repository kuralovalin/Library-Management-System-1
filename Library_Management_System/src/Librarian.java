import java.util.Scanner;

public class Librarian extends User{
    public Librarian(String name, String surname, String ID, String password) {
        super(name, surname, ID, password);
    }

    public void addBook(){
        boolean flag = true;
        do{
            Scanner scanner = new Scanner(System.in);
            System.out.println(ANSI_RED + "PLEASE ENTER AUTHOR NAME" + ANSI_RESET);
            String author = scanner.next();
            System.out.println(ANSI_RED + "PLEASE ENTER BOOK NAME" + ANSI_RESET);
            String book_name = scanner.next();
            if(!flag){
                System.out.println(ANSI_GREEN + "ID HAS ALREADY TAKEN" + ANSI_RESET);
            }
            System.out.println(ANSI_RED + "PLEASE ENTER CATEGORY NAME" + ANSI_RESET);
            String category = scanner.next();
            System.out.println(ANSI_RED + "PLEASE ENTER SHELF VALUE" + ANSI_RESET);
            String shelf = scanner.next();
            System.out.println(ANSI_RED + "PLEASE ENTER ORDER VALUE" + ANSI_RESET);
            String order = scanner.next();
            String ID = category.charAt(0) + "." + shelf + "." + order;
            //Book new_book = new Book(author,book_name,category,shelf,order,ID);
            //flag = LibrarySystem.addBook(new_book);
        }
        while(!flag);
    }

    public void removeBook(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(ANSI_RED + "PLEASE ENTER BOOK ID" + ANSI_RESET);
        String bookID = scanner.next();
        //LibrarySystem.removeBook(bookID);
    }

    public void cancelTableReservation(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(ANSI_RED + "PLEASE ENTER TABLE ID" + ANSI_RESET);
        String tableID = scanner.next();
        //Table table =  LibrarySystem.cancelTableReservation(tableID);
        //if(table.getStatus().equals("BREAK")){
        //    System.out.println(ANSI_GREEN + "TABLE'S OWNER IS ON BREAK NOW" + ANSI_RESET);
        //}
        //else if(table.getStatus().equals("EMPTY") {
        //    System.out.println(ANSI_GREEN + "TABLE HAS ALREADY EMPTY + ANSI_RESET);
        //}
        //else{
        //    table.setStatus("Empty");
        //}
        //masadaki studentin da masa bilgisi değişmeli...
    }
}
