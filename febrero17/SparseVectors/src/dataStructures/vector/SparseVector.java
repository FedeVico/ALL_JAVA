/******************************************************************************
 * Student's name:
 * Student's group:
 * Data Structures. Grado en Informática. UMA.
******************************************************************************/

package dataStructures.vector;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SparseVector<T> implements Iterable<T> {

    protected interface Tree<T> {

        T get(int sz, int i);

        Tree<T> set(int sz, int i, T x);
    }

    // Unif Implementation

    protected static class Unif<T> implements Tree<T> {

        private T elem;

        public Unif(T e) {
            elem = e;
        }

        @Override
        public T get(int sz, int i) {
            // TODONE
            return elem;
        }

        @Override
        public Tree<T> set(int sz, int i, T x) {
            // TODONE
            // Si x es igual q elem
            if (elem.equals(x))
                return this;
            // si sz es 1, entonces es un Unif
            if (sz == 1)
                return new Unif<>(x);
            // resto, si i<mitad -> insertar x izq y sino derecha
            else {
                int mitad = sz / 2;
                if (i < mitad)
                    return new Node<T>(set(mitad, i, x), this);
                else
                    return new Node<T>(this, set(mitad, i - mitad, x));
            }
        }

        @Override
        public String toString() {
            return "Unif(" + elem + ")";
        }
    }

    // Node Implementation
    protected static class Node<T> implements Tree<T> {
        private Tree<T> left, right;

        public Node(Tree<T> l, Tree<T> r) {
            left = l;
            right = r;
        }

        @Override
        public T get(int sz, int i) {
            // TODONE
            int mitad = sz / 2;
            if (i < mitad) {
                return left.get(mitad, i);
            } else {
                return right.get(mitad, i - mitad);
            }
        }

        @Override
        public Tree<T> set(int sz, int i, T x) {
            // TODO
            int mitad = sz / 2;
            if (i < mitad) {
                left = left.set(mitad, i, x);
            } else {
                right = right.set(mitad, i - mitad, x);
            }
            return this;
        }

        protected Tree<T> simplify() {
            // TODONE
            if (left instanceof Unif<?> && right instanceof Unif<?> && left.get(1, 0) == right.get(1, 0))
                return (Unif<T>) left;
            return this;
        }

        @Override
        public String toString() {
            return "Node(" + left + ", " + right + ")";
        }
    }

    // SparseVector Implementation
    private int size;
    private Tree<T> root;

    public SparseVector(int n, T elem) {
        // TODONE
        if (n < 0)
            throw new VectorException("n is negative");
        this.size = (int) Math.pow(2, n);
        root = new Unif<T>(elem);
    }

    public int size() {
        // TODONE
        return size;
    }

    public T get(int i) {
        // TODONE
        if (i < 0 || i > size)
            throw new VectorException("n is negative");
        return root.get(size, i);
    }

    public void set(int i, T x) {
        // TODONE
        if (i < 0 || i > size - 1)
            throw new VectorException("n is negative");
        root = root.set(size, i, x);
    }

    @Override
    public Iterator<T> iterator() {
        // TODONE
        return new SparseVectorIterator();
    }

    public class SparseVectorIterator implements Iterator<T> {
        private int current = 0;

        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public T next() {
            // TODO Auto-generated method stub
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T valor = get(current);
            current++;
            return valor;
        }
    }

    @Override
    public String toString() {
        return "SparseVector(" + size + "," + root + ")";
    }
}
