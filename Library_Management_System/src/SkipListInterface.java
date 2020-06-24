<<<<<<< HEAD
public interface SkipListInterface<E extends Comparable > {
    /**
     * Removes given target from a Skip List.
     */
    public boolean remove(E target);
    /**
     * Inserts given data to a Skip List.
     */
    public boolean insert(E data);
    /**
     * Finds given data in a SkipList.
     */
    public E find(E data);
}

=======
public interface SkipListInterface<E extends Comparable > {
    public boolean remove(E target);
    public boolean insert(E data);
    public E find(E data);
}

>>>>>>> ef7685c87f7f3c53edd36145dce5760c05b9e9d1
