/**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 *
 * ReverserIterable can be used to iterate an iterable
 * in reverse order by using an stack
 */

package dataStructures.util;

import dataStructures.stack.Stack;
import dataStructures.stack.LinkedStack;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ReverseIterable<T> implements Iterable<T> {
    private Iterable<T> iterable; // object to iterate in reverse order

    public ReverseIterable(Iterable<T> iterable) {
        this.iterable = iterable;
    }

    private class ReverseIterator implements Iterator<T> {
        private Stack<T> stack;

        public ReverseIterator() {
            // Store all elements to be returned by iterator in stack
            stack = new LinkedStack<>();
            for(T x : iterable)
                stack.push(x);
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public T next() {
            if(!hasNext())
                throw new NoSuchElementException("next on exhausted iterator");

            T x = stack.top();
            stack.pop();
            return x;
        }
    }

    public Iterator<T> iterator() {
        return new ReverseIterator();
    }
}
