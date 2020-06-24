import java.time.LocalDateTime;
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
        return this.status.equals("reserved");
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
     * Setters and getters
     */
    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public String  getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Integer getTime() {
        return time;
    }
    public void setTime(Integer time) {
        this.time = time;
    }

}
