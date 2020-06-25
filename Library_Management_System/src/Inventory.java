
/**
 * abstract Inventory class which implements InventoryInterface interface
 */
public abstract class Inventory implements InventoryInterface{

    /**
     * Holds ID of a book, table
     */
    private String ID;
    /**
     * Holds status of a book, table
     */
    private String  status;
    /**
     * Holds time of a book, table
     */
    private Integer time;

    /**
     * Default constructor
     */
    public Inventory() {}

    /**
     * Constructor
     * @param id to be assigned
     * @param status to be assigned
     * @param time to be assigned
     */
    protected Inventory(String  id, String status, Integer time) {
        this.ID = id;
        this.status = status;
        this.time = time;
    }

    /**
     * Checks whether table or book are available or not
     */
    @Override
    public boolean isAvailable(){
        return this.status.equals("available");
    }

    /**
     * Changes status of a book,table
     */
    @Override
    public abstract void changeStatus(String newStatus);

    /**
     * Updates time of a book, table
     */
    @Override
    public abstract void updateTime() ;


    /**
     * Getter gets ID of the Book, table
     * @return string value of ID
     */
    public String getID() {
        return ID;
    }
    /**
     * Setter sets ID of the Book, table
     * @param ID value to be assigned
     */
    public void setID(String ID) { this.ID = ID; }
    /**
     * Getter gets status of the Book, table
     * @return string value of status
     */
    public String  getStatus() {
        return status;
    }
    /**
     * Setter sets status of the Book, table
     * @param status value to be assigned
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * Getter gets time of the Book, table
     * @return string value of time
     */
    public Integer getTime() {
        return time;
    }
    /**
     * Setter sets time of the Book, table
     * @param time value to be assigned
     */
    public void setTime(Integer time) {
        this.time = time;
    }

}
