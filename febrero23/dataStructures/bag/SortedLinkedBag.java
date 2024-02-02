/**
 * @author Pablo López, Data Structures, Grado en Informática. UMA.
 *
 * Bag ADT implementation using linked nodes
 * (beware of ugly code: search loop is repeated everywhere)
 */

package dataStructures.bag;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedLinkedBag<T extends Comparable<? super T>> implements Bag<T> {

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

	public SortedLinkedBag() {
		first = null;
		size = 0;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() { return size; }

	public void insert(T item) {
		Node<T> previous = null;
		Node<T> current = first;

		while (current != null && current.elem.compareTo(item) < 0) {
			previous = current;
			current = current.next;
		}

		if (current != null && current.elem.equals(item)) {
			current.count++;
		} else if (previous == null) {
			first = new Node<>(item, 1, first);
		} else {
			previous.next = new Node<>(item, 1, current);
		}
		size++;
	}

	public int occurrences(T item) {
		Node<T> current = first;
		int result = 0;

		while (current != null && current.elem.compareTo(item) < 0) {
			current = current.next;
		}

		if (current != null && current.elem.equals(item)) {
			result = current.count;
		}
		return result;
	}

	public void delete(T item) {
		Node<T> previous = null;
		Node<T> current = first;

		while (current != null && current.elem.compareTo(item) < 0) {
			previous = current;
			current = current.next;
		}

		if (current != null && current.elem.equals(item)) {
			if (current.count > 1) {
				current.count--;
			} else if (previous == null) {
				first = current.next;
			} else {
				previous.next = current.next;
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

	public String toString() {
		String className = getClass().getSimpleName();
		String text = className+"(";
		for (Node<T> p = first; p != null; p = p.next) {
			text += "(" + p.elem + ", " + p.count + ")" + (p.next != null ? "," : "");
		}
		return text + ")";
	}
}
