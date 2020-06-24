
/**
 *   Project - 222
 *   File:   InventoryInterface.java
 *   Group - 13
 */

public interface InventoryInterface {

    /**
     * Updates time for table and book
     */
    public void updateTime();
    /**
     * Checks whether table or book are available or not
     */
    public boolean isAvailable();
    /**
     * Changes status of a table or book
     */
    public void changeStatus(String newStatus);

}
