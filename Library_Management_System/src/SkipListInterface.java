/**
 * The interface Skip list ınterface.
 *
 * @param <E> the type parameter
 */
public interface SkipListInterface<E extends Comparable > {

    /**
     * Remove boolean.
     *
     * @param target the target
     * @return the boolean
     */
    public boolean remove(E target);

    /**
     * Insert boolean.
     *
     * @param data the data
     * @return the boolean
     */
    public boolean insert(E data);

    /**
     * Find e.
     *
     * @param data the data
     * @return the e
     */
    public E find(E data);
}

