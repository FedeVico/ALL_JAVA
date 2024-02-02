/**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 *
 * Persistent Vectors as binary trees.
 */

package dataStructures.vector;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class TreeVector<T> implements Iterable<T> {

    private interface Tree<T>{
        T get(int i);
        Tree<T> set(int i, T x);
        void toStringRec(StringBuilder sb);
    }

    private static class Leaf<T> implements Tree<T> {
        T elem;

        public Leaf(T e) {
            elem = e;
        }

        public T get(int i) {
            return elem;
        }

        public Tree<T> set(int i, T x) {
            if (elem.equals(x))
                return this;
            else
                return new Leaf<>(x);
        }

        public void toStringRec(StringBuilder sb) {
            String className = getClass().getSimpleName();
            sb.append(className);
            sb.append("(");
            sb.append(elem);
            sb.append(")");
        }
    }

    private static class Node<T> implements Tree<T> {
        Tree<T> left, right;

        public Node(Tree<T> l, Tree<T> r) {
            left = l;
            right = r;
        }

        public T get(int i) {
            int j = i / 2;
            if(i % 2 == 0)
                return left.get(j);
            else
                return right.get(j);
        }

        public Tree<T> set(int i, T x) {
            int j = i / 2;
            if(i % 2 == 0)
               left = left.set(j, x);
            else
               right = right.set(j, x);
            return this;
        }

        public void toStringRec(StringBuilder sb) {
            String className = getClass().getSimpleName();
            sb.append(className);
            sb.append("(");
            left.toStringRec(sb);
            sb.append(",");
            right.toStringRec(sb);
            sb.append(")");
        }
    }

    private int exponent; // this vector stores Math.pow(2,exponent) components
    private Tree<T> root;

    public TreeVector(int exp, T elem) {
        if(exp<0)
            throw new VectorException("TreeVector: negative exponent exponent");
        exponent = exp;
        root = tree(exp, elem);
    }

    private Tree<T> tree(int exp, T elem) {
        if(exp == 0)
            return new Leaf<>(elem);
        else {
            Tree<T> left = tree(exp-1, elem);
            Tree<T> right = tree(exp-1, elem);
            return new Node<>(left, right);
        }
    }

    public int size() { return (int) Math.pow(2,exponent); }

    public void set(int i, T x) {
        if(i<0 || i>= size())
            throw new VectorException("set: index out of bounds exception "+i);
        else
            root = root.set(i, x);
    }

    public T get(int i) {
        if(i<0 || i>= size())
            throw new VectorException("get: index out of bounds exception "+i);
        else
            return root.get(i);
    }

    private class Iter implements Iterator<T> {
        // A more efficient implementation would work directly with the tree
        int index = 0;

        public boolean hasNext() {
            return index < exponent;
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
        int sz = size();
        String className = getClass().getSimpleName();
        StringBuilder sb = new StringBuilder(className);
        sb.append("(");
        for(int i=0; i<sz-1; i++) {
            sb.append(get(i));
            sb.append(",");
        }
        if(sz > 0)
            sb.append(get(sz-1));
        sb.append(")");
        return sb.toString();
    }
}
