
/**
 * Book class which extends Inventory
 */
public class Book extends Inventory {

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
     * Changes status of the book
     */
    @Override
    public void changeStatus() {
        if(getStatus())
            setStatus(false);
        else
            setStatus(true);
    }
    /**
     * Updates time of the book, for 15 days
     */
    @Override
    public void updateTime() {
        setTime(getTime().plusDays(15));
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


}
