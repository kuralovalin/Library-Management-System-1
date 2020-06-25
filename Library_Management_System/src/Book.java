import java.util.Objects;

public class Book extends Inventory implements Comparable<Book> {

    /**
     * Holds category of the book
     */
    private String category;

    /**
     * Holds name of the book
     */
    private String name;
    /**
     * Holds author of the book
     */
    private String author;
    /**
     * Time extend counter
     */
    private int timeExtendCounter;
    /**
     * Getter returns value for timeExtendCounter
     * @return int value of timeExtendCounter
     */
    public int getTimeExtendCounter() {
        return timeExtendCounter;
    }
    /**
     * Setter sets value for timeExtendCounter
     * @param timeExtendCounter value of timeExtendCounter
     */
    public void setTimeExtendCounter(int timeExtendCounter) {
        this.timeExtendCounter = timeExtendCounter;
    }

    /**
     * Constructor initializes data with taken parameter values.
     */
    public Book(String id, String category, String name, String author, String status, Integer time) {
        super(id,status,time);
        this.category = category;
        this.name = name;
        this.author = author;
        this.timeExtendCounter = 0;
    }
    /**
     * Constructor initializes data with taken parameter object.
     */
    public Book(Book obj) {
        super(obj.getID(),obj.getStatus(),obj.getTime());
        this.category = obj.category;
        this.name = obj.name;
        this.author = obj.author;
        this.timeExtendCounter = 0;
    }
    /**
     * ChangeStatus method changes status
     * @param newStatus newStatus to be assigned
     */
    @Override
    public void changeStatus(String newStatus) {
        if(isAvailable())
            setStatus(newStatus);
        else
            System.out.println(User.ANSI_RED + "Book has already taken" + User.ANSI_RESET);
    }

    /**
     * Updates time of the book, for 15 days
     */
    @Override
    public void updateTime() {
         setTime(getTime()-1);
    }

    /**
     * Getter gets category of the Book
     * @return string value of category
     */
    public String getCategory() {
        return category;
    }
    /**
     * Setter sets category of the Book
     * @param category value to be assigned
     */
    public void setCategory(String category) {
        this.category = category;
    }
    /**
     * Getter gets name of the Book
     * @return string value of name
     */
    public String getName() {
        return name;
    }
    /**
     * Setter sets name of the Book
     * @param name value to be assigned
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Getter gets author of the Book
     * @return string value of author
     */
    public String getAuthor() {
        return author;
    }
    /**
     * Setter sets author of the Book
     * @param author value to be assigned
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Compare Book's Id for AVL tree
     * it compares IDs
     *
     */
    @Override
    public int compareTo(Book o) {
        String num1 = o.getID().substring(1);
        String num2 = this.getID().substring(1);

        return (Integer.valueOf(num1).compareTo(Integer.valueOf(num2)));

    }
    /**
     * Helper Method for compare ID's with string
     */
    public int compareID(String o) {

        String num1 = o.substring(1);
        String num2 = this.getID().substring(1);
        return (Integer.valueOf(num2).compareTo(Integer.valueOf(num1)));

    }

    /**
     * Compares Book object time values
     * Helper method of QuickSort class methods
     * @param o Book object
     * @return Integer value for result of comparing time values
     */
    public int compareTime(Book o) {
        Integer num1 = o.getTime();
        Integer num2 = this.getTime();

        return (Integer.valueOf(num1).compareTo(Integer.valueOf(num2)));
    }

    /**
     * Override toString method for printing book
     */
    @Override
    public String toString() {
        return "Category: " + category +  ", Name: " + name +   ", Author: " + author +   ", ID: " + getID() + "\n";
    }

    /**
     * equals method checks for equality of data in a book
     * @param o Book object
     * @return boolean value for result of comparing data values of book
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(category, book.category) &&
                Objects.equals(name, book.name) &&
                Objects.equals(author, book.author);
    }

    /**
     * hashCode method returns hash int value for a book
     * @return in value of hashing book
     */
    @Override
    public int hashCode() {
        return Objects.hash(category, name, author);
    }

}
