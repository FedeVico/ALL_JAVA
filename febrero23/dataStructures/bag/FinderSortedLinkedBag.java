/**
 * @author Pablo López, Data Structures, Grado en Informática. UMA.
 *
 * Bag ADT implementation using linked nodes
 * Search loop is encapsulated in the constructor of nested class Finder
 */

package dataStructures.bag;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FinderSortedLinkedBag<T extends Comparable<? super T>> implements Bag<T> {

	static private class Node<E> {
		E elem;
		int count;
		Node<E> next;

		Node(E x, int n, Node<E> node) {
			elem = x;
			count = n;
			next = node;
		}
	}

	private Node<T> first; // keep linked list sorted by "elem"
	private int size;

	private class Finder {
		Node<T> previous;
		Node<T> current;
		boolean found;

		// if "item" is stored in the bag, "found" is true, "current"
		// refers to "item" and "previous" refers to the preceding node;
		// otherwise "found" is false and "item" would be inserted
		// between "previous" and "current"
		
		Finder(T item) {
			previous = null;
			current = first;

			while (current != null && current.elem.compareTo(item) < 0) {
				previous = current;
				current = current.next;
			}
			found = current != null && current.elem.equals(item);
		}
	}

	public FinderSortedLinkedBag() {
		first = null;
		size = 0;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() { return size; }

	public void insert(T item) {

		Finder f = new Finder(item);

		if (f.found) {
			f.current.count++;
		} else if (f.previous == null) {
			first = new Node<>(item, 1, first);
		} else {
			f.previous.next = new Node<>(item, 1, f.current);
		}
		size++;
	}

	public int occurrences(T item) {
		Finder f = new Finder(item);

		if (f.found) {
			return f.current.count;
		} else {
			return 0;
		}
	}

	public void delete(T item) {
		Finder f = new Finder(item);

		if (f.found) {
			if (f.current.count > 1) {
				f.current.count--;
			} else if (f.previous == null) {
				first = f.current.next;
			} else {
				f.previous.next = f.current.next;
			}
			size--;
		}
	}

	private class BagIterator implements Iterator<T> {
		// Invariant: current is a reference to node containing next to be yielded element
		// or null if iterator is exhausted. returned is the number of occurrences in that
		// node that have been already yielded.
		Node<T> current;
		int returned;

		BagIterator() {
			current = first;
			returned = 0;
		}

		public boolean hasNext() {
			return (current != null);
		}

		public T next() {
			if(!hasNext())
				throw new NoSuchElementException();
			T x = current.elem;
			returned++;
			if(returned == current.count) {
				// Maintain invariant
				current = current.next;
				returned = 0;
			}
			return x;
		}
	}

	public Iterator<T> iterator() {
		return new BagIterator();
	}


	// Extra operations

	public FinderSortedLinkedBag(Bag<T> bag) {
		this();
		for(T x : bag)
			this.insert(x);
	}


	// Copy constructor. We use that argument iterates in order to have an efficient implementation
	public FinderSortedLinkedBag(FinderSortedLinkedBag<T> bag) {
		size = bag.size();

		Iterator<T> it = bag.iterator();
		if(!it.hasNext()) {
			first = null;
		} else {
			first = new Node<>(it.next(), 1, null);
			Node<T> current = first;
			while (it.hasNext()) {
				T x = it.next();
				if(current.elem==x)
					current.count++;
				else {
					current.next = new Node<>(x,1,null);
					current = current.next;
				}
			}
		}
	}

	// Does union of this and that. Result is stored on this, That is not modified
	public void union(Bag<T> bag) {
		for(T x : bag)
			this.insert(x);
	}

	// We use that argument iterates in order
	public void union(FinderSortedLinkedBag<T> that) {
		Node<T> prev = null;
		Node<T> current = first;
		Node<T> thatNode = that.first;

		while(current != null && thatNode != null) {
			T x = current.elem;
			T y = thatNode.elem;

			if(x.compareTo(y) == 0) {
				// Add y to current node
				current.count += thatNode.count;
				// Advance both lists to next nodes
				prev = current;
				current = current.next;
				thatNode = thatNode.next;
			} else if(x.compareTo(y)<0) {
				// Advance first list to next node
				prev = current;
				current = current.next;
			} else {
				// Create new node for y and insert it before current
				if(prev==null)
					first = new Node<>(y,thatNode.count,first);
				else
					prev.next = new Node<>(y,thatNode.count,current);
				// Advance second list
				thatNode = thatNode.next;
			}
		}

		// If second list is not exhausted, then append its elements
		// at the end of first list
		while(thatNode != null) {
			// Create new node for y and insert it after prev
			if(prev==null)
				first = new Node<>(thatNode.elem,thatNode.count,null);
			else
				prev.next = new Node<>(thatNode.elem,thatNode.count,null);
			// Advance modified first list
			prev = prev.next;

			// Advance second list
			thatNode = thatNode.next;
		}
	}

	public void intersection(Bag<T> bag) {
		Node<T> prev = null;
		Node<T> current = first;
		while(current != null) {
			current.count = Math.min(current.count, bag.occurrences(current.elem));
			// Advance to next node
			if(current.count <= 0) { // delete this node
				if(prev==null)
					first = first.next;
				else
					prev.next = current.next;
			} else
				prev = current;
			current = current.next;
		}
	}

	public void intersection(FinderSortedLinkedBag<T> that) {
		Node<T> prev = null;
		Node<T> current = first;
		Node<T> thatNode = that.first;

		while(current != null && thatNode != null) {
			T x = current.elem;
			T y = thatNode.elem;

			if(x.compareTo(y) == 0) {
				// Add y to current node
				current.count = Math.min(current.count,thatNode.count);
				// Advance both lists to next nodes
				prev = current;
				current = current.next;
				thatNode = thatNode.next;
			} else if(x.compareTo(y)<0) {
				// Remove x node from result (first list)
				if(prev==null)
					first = first.next;
				else {
					prev.next = current.next;
					current = current.next;
				}
			} else {
				// Advance second list
				thatNode = thatNode.next;
			}
		}

		// If first list is not exhausted, then remove remaining
		// elements at the end of first list
		if(current != null) {
			if (prev == null)
				first = null;
			else
				prev.next = null;
		}
	}

	public void difference(Bag<T> bag) {
		Node<T> prev = null;
		Node<T> current = first;
		while(current != null) {
			current.count -= bag.occurrences(current.elem);
			// Advance to next node
			if(current.count <= 0) { // delete this node
				if(prev==null)
					first = first.next;
				else
					prev.next = current.next;
			} else
				prev = current;
			current = current.next;
		}
	}

	// We use that argument iterates in order
	public void difference(FinderSortedLinkedBag<T> that) {
		Node<T> prev = null;
		Node<T> current = first;
		Node<T> thatNode = that.first;

		while(current != null && thatNode != null) {
			T x = current.elem;
			T y = thatNode.elem;

			if(x.compareTo(y) == 0) {
				// Add y to current node
				current.count -= thatNode.count;
				// Advance both lists to next nodes
				if(current.count <= 0) { // delete this node
					if(prev==null)
						first = first.next;
					else
						prev.next = current.next;
				} else
					prev = current;

				current = current.next;
				thatNode = thatNode.next;
			} else if(x.compareTo(y)<0) {
				// Advance first list to next node
				prev = current;
				current = current.next;
			} else {
				// Advance second list
				thatNode = thatNode.next;
			}
		}
	}

	public String toString() {
		String className = getClass().getSimpleName();
		String text = className+"(";
		for (Node<T> p = first; p != null; p = p.next) {
			text += "(" + p.elem + ", " + p.count + ")" + (p.next != null ? "," : "");
		}
		return text + ")";
	}
}
