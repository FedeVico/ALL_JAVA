/**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 *
 * Interface for rings.
 */
package dataStructures.ring;

public interface Ring<T> extends Iterable<T> {

    /**
     * Test for set emptiness.
     * 
     * @return {@code true} if set is empty, else {@code false}.
     */
    boolean isEmpty();

    /**
     * Retrieves (without removing) element at cursor of ring.
     * 
     * @throws EmptyRingException
     *             if ring is empty.
     * @return Element at cursor of ring.
     */
    T cursor();

    /**
     * Rotates ring to the left (moves cursor to the right).
     */
    void rotateL();

    /**
     * Rotates ring to the right (moves cursor to the left).
     */
    void rotateR();

    /**
     * Inserts new element to the left of cursor. Doesn't move cursor.
     * 
     * @param x
     *            the element to insert.
     */
    void insertL(T x);

    /**
     * Inserts new element to the right of cursor. Doesn't move cursor.
     * 
     * @param x
     *            the element to insert.
     */
    void insertR(T x);

    /**
     * Deletes element at cursor and moves cursor to the left.
     * 
     * @throws EmptyRingException
     *             if ring is empty.
     */
    void deleteL();

    /**
     * Deletes element at cursor and moves cursor to the right.
     * 
     * @throws EmptyRingException
     *             if ring is empty.
     */
    void deleteR();

    /**
     * Retrieves number of elements in ring.
     * 
     * @return Number of elements in ring.
     */
    int size();
}
