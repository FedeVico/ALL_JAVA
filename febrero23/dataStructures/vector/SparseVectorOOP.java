/**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 *
 * Persistent Vectors as binary trees, where some cells corresponding to
 * equal+adjacent values are compressed into a single node.
 */

package dataStructures.vector;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SparseVectorOOP<T> implements Iterable<T> {

    private interface Tree<T>{
        T get(int sz, int i);
        Tree<T> set(int sz, int i, T x);
        void toStringRec(StringBuilder sb);
    }

    private static class Unif<T> implements Tree<T> {
        T elem;

        public Unif(T e) {
            elem = e;
        }

        public T get(int sz, int i) {
            return elem;
        }

        public Tree<T> set(int sz, int i, T x) {
            if (elem.equals(x))
                return this;
            else if (sz < 2) {
                return new Unif<>(x);
            } else {
                int newSz = sz / 2;
                if (i < newSz)
                    return new Node<>(this.set(newSz, i, x),this);
                else
                    return new Node<>(this, this.set(newSz, i - newSz, x));
            }
        }

        public void toStringRec(StringBuilder sb) {
            sb.append("Unif<");
            sb.append(elem);
            sb.append(">");
        }
    }

    private static class Node<T> implements Tree<T> {
        Tree<T> left, right;

        public Node(Tree<T> l, Tree<T> r) {
            left = l;
            right = r;
        }

        public T get(int sz, int i) {
            int newSz = sz / 2;
            if(i < newSz)
                return left.get(newSz, i);
            else
                return right.get(newSz, i-newSz);
        }

        private Tree<T> simplify() {
            if(this.left instanceof Unif<?> && this.right instanceof Unif<?>) {
                Unif<T> unif1 = (Unif<T>) left;
                Unif<T> unif2 = (Unif<T>) right;
                if(unif1.elem.equals(unif2.elem))
                    return unif1;
            }
            return this;
        }

        public Tree<T> set(int sz, int i, T x) {
            int newSz = sz / 2;

            if (i < newSz)
               left = left.set(newSz, i, x);
            else
               right = right.set(newSz, i-newSz, x);
            return this.simplify();
        }

        public void toStringRec(StringBuilder sb) {
            sb.append("Node<");
            left.toStringRec(sb);
            sb.append(",");
            right.toStringRec(sb);
            sb.append(">");
        }
    }

    private int size;
    private Tree<T> root;

    public SparseVectorOOP(int exp, T elem) {
        if(exp<0)
            throw new VectorException("SparseVectorOOP: negative size");
        size = (int) Math.pow(2,exp);
        root = new Unif<>(elem);
    }

    public int size() { return size; }

    public void set(int i, T x) {
        if(i<0 || i>=size)
            throw new VectorException("set: index out of bounds exception "+i);
        else
            root = root.set(size, i, x);
    }

    public T get(int i) {
        if(i<0 || i>=size)
            throw new VectorException("get: index out of bounds exception "+i);
        else
            return root.get(size, i);
    }

    private class Iter implements Iterator<T> {
        // A more efficient implementation would work directly with the tree
        int index = 0;

        public boolean hasNext() {
            return index < size;
        }

        public T next() {
            if(!hasNext())
                throw new NoSuchElementException();
            T elem = get(index);
            index++;
            return elem;
        }
    }

    public Iterator<T> iterator() {
        return new Iter();
    }

    @Override public String toString() {
        String className = getClass().getSimpleName();
        StringBuilder sb = new StringBuilder(className);
        sb.append("(");
        sb.append(size);
        sb.append(",");
        root.toStringRec(sb);
        sb.append(")");
        return sb.toString();
    }
}
