package dataStructures.doubleEndedQueue;

public class LinkedDoubleEndedQueue<T> implements DoubleEndedQueue<T> {

    private static class Node<E> {
        private E elem;
        private Node<E> next;
        private Node<E> prev;

        public Node(E x, Node<E> nxt, Node<E> prv) {
            elem = x;
            next = nxt;
            prev = prv;
        }
    }

    private Node<T> first, last;

    /**
     * Invariants:
     * if queue is empty then both first and last are null
     * if queue is non-empty:
     * * first is a reference to first node and last is ref to last node
     * * first.prev is null
     * * last.next is null
     * * rest of nodes are doubly linked
     */

    public LinkedDoubleEndedQueue() {
        first = null;
        last = null;
    }

    @Override
    public boolean isEmpty() {
        return first == null && last == null;
    }

    @Override
    public void addFirst(T x) {
        if (isEmpty()) {
            Node<T> node = new Node<T>(x, null, null);
            first = node;
            last = node;
        } else {
            Node<T> node = new Node<T>(x, first, null);
            first = node;
            first.prev = node;
        }
    }

    @Override
    public void addLast(T x) {
        if (isEmpty()) {
            Node<T> node = new Node<T>(x, null, null);
            first = node;
            last = node;
        } else {
            Node<T> node = new Node<T>(x, null, last);
            last = node;
            last.next = node;
        }
    }

    @Override
    public T first() {
        return first.elem;
    }

    @Override
    public T last() {
        return last.elem;
    }

    @Override
    public void deleteFirst() {
        if (first == last) {
            first = null;
            last = null;
        } else {
            first = first.next;
            first.prev = null;
        }
    }

    @Override
    public void deleteLast() {
        if (first == last) {
            first = null;
            last = null;
        } else {
            last = last.prev;
            last.next = null;
        }
    }

    @Override
    public String toString() {
        String className = getClass().getName().substring(getClass().getPackage().getName().length() + 1);
        String s = className + "(";
        for (Node<T> node = first; node != null; node = node.next)
            s += node.elem + (node.next != null ? "," : "");
        s += ")";
        return s;
    }
}
