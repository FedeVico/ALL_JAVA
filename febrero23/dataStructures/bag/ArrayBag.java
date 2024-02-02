/**
 * @author Pablo López, Data Structures, Grado en Informática. UMA.
 *
 * Bag ADT implementation using arrays
 */

package dataStructures.bag;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayBag<T extends Comparable<? super T>> implements Bag<T> {

	private final static int INITIAL_CAPACITY = 5;

	protected T[] value; // keep this array sorted
	protected int[] count; // keep only positive counters
	protected int nextFree;
	protected int size;

	public ArrayBag() {
		this(INITIAL_CAPACITY);
	}

	@SuppressWarnings("unchecked")
	public ArrayBag(int n) {
		value = (T[]) new Comparable[n];
		count = new int[n];
		nextFree = 0;
		size = 0;
	}

	private void ensureCapacity() {
		if (nextFree == value.length) {
			value = Arrays.copyOf(value, 2 * value.length);
			count = Arrays.copyOf(count, 2 * count.length);
		}
	}

	public boolean isEmpty() { return size == 0; }

	public int size() { return size; }

	// if "item" is stored in the array "value", returns its index
	// otherwise returns the index where "item" would be inserted

	private int locate(T item) {
		int lower = 0;
		int upper = nextFree - 1;
		int mid = 0;
		boolean found = false;
		
		// binary search
		while (lower <= upper && !found) {
			mid = lower + ((upper - lower) / 2); // (lower + upper) / 2;
			found = value[mid].equals(item);
			if (!found) {
				if (value[mid].compareTo(item) > 0) {
					upper = mid - 1;
				} else {
					lower = mid + 1;
				}
			}
		}

		if (found)
			return mid; // the index where "item" is stored
		else
			return lower; // the index where "item" would be inserted
	}

	public void insert(T item) {
		ensureCapacity();
		int i = locate(item);
		if (value[i] != null && value[i].equals(item)) {
			count[i]++;
		} else {
			// shift elements to right
			for (int j = nextFree; j > i; j--) {
				value[j] = value[j - 1];
				count[j] = count[j - 1];
			}
			value[i] = item;
			count[i] = 1;
			nextFree++;
		}
		size++;
	}

	public int occurrences(T item) {
		int i = locate(item);
		if (value[i] != null && value[i].equals(item)) {
			return count[i];
		} else {
			return 0;
		}
	}

	public void delete(T item) {
		int i = locate(item);
		if (value[i] != null && value[i].equals(item)) {
			if (count[i] > 1) {
				count[i]--;
			} else {
				// shift elements to left
				for (int j = i; j < nextFree-1; j++) {
					value[j] = value[j + 1];
					count[j] = count[j + 1];
				}
				nextFree--;
			}
			size--;
		}
	}

	private class BagIterator implements Iterator<T> {
		// Invariant: idx is index of cell storing next to be yielded element.
		// It's >= size if iterator is exhausted. returned is the number of
		// occurrences for that element that have been already yielded.
		int idx;
		int returned;

		BagIterator() {
			idx = 0;
			returned = 0;
		}

		public boolean hasNext() {
			return (idx < nextFree);
		}

		public T next() {
			if(!hasNext())
				throw new NoSuchElementException();
			T x = value[idx];
			returned++;
			if(returned == count[idx]) {
				// Maintain invariant
				idx++;
				returned = 0;
			}
			return x;
		}
	}

	public Iterator<T> iterator() {
		return new BagIterator();
	}

	public String toString() {
		String className = getClass().getSimpleName();
		String text = className+"(";
		for (int i = 0; i < nextFree; i++) {
			text += "(" + value[i] + ", " + count[i] + ")" + (i<nextFree-1 ? "," : "");
		}
		return text + ")";
	}
}
