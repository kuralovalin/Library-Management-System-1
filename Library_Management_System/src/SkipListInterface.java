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

