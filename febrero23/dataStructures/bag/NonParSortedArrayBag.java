/**
 * @author Pablo López, Data Structures, Grado en Informática. UMA.
 * Modified by Pepe Gallardo to use a single array of nodes instead
 * of two arrays (one with elements and another one with occurrences).
 *
 * Bag ADT implementation using arrays
 */

package dataStructures.bag;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class NonParSortedArrayBag<T extends Comparable<? super T>> implements Bag<T> {

	private final static int INITIAL_CAPACITY = 10;
	
	static private class Node<E> {
		E elem;
		int count;

		Node(E x, int n) {
			elem = x;
			count = n;
		}
	}	
	
	protected Node<T>[] nodes;
	protected int nextFree;
	protected int size;

	public NonParSortedArrayBag() {
		this(INITIAL_CAPACITY);
	}

	@SuppressWarnings("unchecked")
	public NonParSortedArrayBag(int n) {
		nodes = (Node<T>[])new Node[n];
		nextFree = 0;
		size = 0;
	}

	private void ensureCapacity() {
		if (nextFree == nodes.length) {
			nodes = Arrays.copyOf(nodes, 2 * nodes.length);
		}
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() { return size; }


	// if "item" is stored in the array "nodes", returns its index
	// otherwise returns the index where "item" would be inserted

	private int locate(T item) {
		int lower = 0;
		int upper = nextFree - 1;
		int mid = 0;
		boolean found = false;
		
		// binary search
		while (lower <= upper && !found) {
			mid = lower + ((upper - lower) / 2); // (lower + upper) / 2;
			found = nodes[mid].elem.equals(item);
			if (!found) {
				if (nodes[mid].elem.compareTo(item) > 0) {
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
		if (nodes[i] != null && nodes[i].elem.equals(item)) {
			nodes[i].count++;
		} else {
			// shift elements to right
			for (int j = nextFree; j > i; j--) {
				nodes[j] = nodes[j - 1];
			}
			nodes[i] = new Node<>(item,1);
			nextFree++;
		}
		size++;
	}

	public int occurrences(T item) {
		int i = locate(item);
		if (nodes[i] != null && nodes[i].elem.equals(item)) {
			return nodes[i].count;
		} else {
			return 0;
		}
	}

	public void delete(T item) {
		int i = locate(item);
		if (nodes[i] != null && nodes[i].elem.equals(item)) {
			if (nodes[i].count > 1) {
				nodes[i].count--;
			} else {
				// shift elements to left
				for (int j = i; j < nextFree - 1; j++) {
					nodes[j] = nodes[j + 1];
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
			T x = nodes[idx].elem;
			returned++;
			if(returned == nodes[idx].count) {
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
			text += "(" + nodes[i].elem + ", " + nodes[i].count + ")" + (i<nextFree-1 ? "," : "");
		}
		return text + ")";
	}
}
