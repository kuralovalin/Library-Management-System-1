public interface SkipListInterface<E extends Comparable > {
    public boolean remove(E target);
    public boolean insert(E data);
    public E find(E data);
}

