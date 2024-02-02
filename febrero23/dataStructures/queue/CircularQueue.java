/**
 * @author Pablo López, Data Structures, Grado en Informática. UMA.
 *
 * Queue implementation using a circular linked list
 */

package dataStructures.queue;

/**
 * Queue implemented using a circular singly linked list (with reference to the
 * last element).
 *
 * @param <T>
 *            Type of elements in queue.
 */
public class CircularQueue<T> implements Queue<T> {

    private static class Node<E> {
        E elem;
        Node<E> next;

        Node(E x, Node<E> nextNode) {
            elem = x;
            next = nextNode;
        }
    }

    /**
     * reference to last element in queue.
     */
    private Node<T> last;

    /**
     * Creates an empty queue.
     * <p>
     * Time complexity: O(1)
     */
    public CircularQueue() {
        last = null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Time complexity: O(1)
     */
    @Override
    public boolean isEmpty() {
        return last == null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Time complexity: O(1)
     *
     * @throws EmptyQueueException
     *             {@inheritDoc}
     */
    @Override
    public T first() {
        if (isEmpty()) {
            throw new EmptyQueueException("first on empty queue");
        }
        return last.next.elem;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Time complexity: O(1)
     *
     * @throws EmptyQueueException
     *             {@inheritDoc}
     */
    @Override
    public void dequeue() {
        if (isEmpty()) {
            throw new EmptyQueueException("dequeue on empty queue");
        }
        if (last == last.next) {
            last = null;
        } else {
            last.next = last.next.next;
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Time complexity: O(1)
     */
    @Override
    public void enqueue(T x) {
        if (isEmpty()) {
            last = new Node<>(x, null);
            last.next = last;
        } else {
            last.next = new Node<>(x, last.next);
            last = last.next;
        }
    }

    /**
     * {@inheritDoc} Returns representation of queue as a String.
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder(getClass().getSimpleName());
        output.append("(");
        if (!isEmpty()) {
            Node<T> current = last.next;
            output.append(current.elem);
            current = current.next;
            while (current != last.next) {
                output.append(", ").append(current.elem);
                current = current.next;
            }
        }
        return output.append(")").toString();
    }
}
