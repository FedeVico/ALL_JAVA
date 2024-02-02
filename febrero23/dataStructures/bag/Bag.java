/**
 * @author Pablo López, Data Structures, Grado en Informática. UMA.
 *
 * Interface for the Bag ADT.
 */

package dataStructures.bag;

/**
 * Interface for the Bag ADT.
 * 
 * @param <T>
 *            Type of elements in bag.
 */
public interface Bag<T> extends Iterable<T> {
	/**
	 * Test the bag for emptiness.
	 * 
	 * @return {@code true} if bag is empty, else {@code false}.
	 */
	boolean isEmpty();

	/**
	 * Retrieves total number of elements in bag (its cardinal).
	 * @return Number of elements in bag.
	 */
	int size();

	/**
	 * Inserts a new occurrence in the bag.
	 * 
	 * @param item
	 *            the element to insert.
	 */
	void insert(T item);

	/**
	 * Returns the number of occurrences of {@code item} in the bag
	 * 
	 * @param item
	 *            the item to be counted
	 * @return number of occurrences of {@code item}
	 */
	int occurrences(T item);

	/**
	 * Removes an occurrence of {@code item} from the bag
	 * 
	 * @param item
	 *            the item to remove
	 */
	void delete(T item);
}
