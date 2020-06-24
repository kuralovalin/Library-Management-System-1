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

    public int getTimeExtendCounter() {
        return timeExtendCounter;
    }

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
     * Getters and setters
     */
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public int compareTo(Book o) {
        String num1 = o.getID().substring(1);
        String num2 = this.getID().substring(1);

        return (Integer.valueOf(num1).compareTo(Integer.valueOf(num2)));

    }

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


    @Override
    public String toString() {
        return "Category: " + category +  ", Name: " + name +   ", Author: " + author +   ", ID: " + getID() + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(category, book.category) &&
                Objects.equals(name, book.name) &&
                Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, name, author);
    }

}
