/**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 *
 * Ring implemented with double linked circular list.
 */
package dataStructures.ring;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Ring implemented with double linked circular list.
 *
 * @param <T>
 *            Type of elements in ring.
 */
public class DoubleLinkedRing<T> implements Ring<T> {

    private static class Node<E> {
        private E elem;
        private Node<E> left, right;

        public Node(Node<E> l, E x, Node<E> r) {
            left = l;
            elem = x;
            right = r;
        }
    }

    protected Node<T> cursor;
    protected int size;

    public DoubleLinkedRing() {
        cursor = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T cursor() {
        if (isEmpty())
            throw new EmptyRingException("cursor on empty ring");
        else
            return cursor.elem;
    }

    @Override
    public void rotateL() {
        if (size > 1)
            cursor = cursor.right;
    }

    @Override
    public void rotateR() {
        if (size > 1)
            cursor = cursor.left;
    }

    private Node<T> singleton(T x) {
        Node<T> node = new Node<>(null, x, null);
        node.left = node;
        node.right = node;
        return node;
    }

    @Override
    public void insertL(T x) {
        if (isEmpty()) {
            cursor = singleton(x);
        } else {
            Node<T> node = new Node<>(cursor.left, x, cursor);
            cursor.left = node;
            node.left.right = node;
        }
        size++;
    }

    @Override
    public void insertR(T x) {
        if (isEmpty()) {
            cursor = singleton(x);
        } else {
            Node<T> node = new Node<>(cursor, x, cursor.right);
            cursor.right = node;
            node.right.left = node;
        }
        size++;
    }

    @Override
    public void deleteL() {
        if (isEmpty())
            throw new EmptyRingException("deleteL on empty ring");
        else {
            if (size > 1) {
                cursor.right.left = cursor.left;
                cursor.left.right = cursor.right;
                cursor = cursor.left;
            } else {
                cursor = null;
            }
            size--;
        }
    }

    @Override
    public void deleteR() {
        if (isEmpty())
            throw new EmptyRingException("deleteR on empty ring");
        else {
            if (size > 1) {
                cursor.left.right = cursor.right;
                cursor.right.left = cursor.left;
                cursor = cursor.right;
            } else {
                cursor = null;
            }
            size--;
        }
    }

    @Override
    public String toString() {
        String className = getClass().getSimpleName();
        StringBuilder sb = new StringBuilder(className);
        sb.append("(");
        Node<T> c = cursor;
        if (!isEmpty()) {
            sb.append("<");
            sb.append(c.elem);
            sb.append(">");
        }
        for (int i = 0; i < size - 1; i++) {
            c = c.right;
            sb.append(",");
            sb.append(c.elem);
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new DoubleLinkedBagIterator();
    }

    private class DoubleLinkedBagIterator implements Iterator<T> {

        private Node<T> current;
        private int visited;

        public DoubleLinkedBagIterator() {
            current = cursor;
            visited = 0;
        }

        @Override
        public boolean hasNext() {
            return visited < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T result = current.elem;
            visited++;
            current = current.right;
            return result;
        }
    }
}
