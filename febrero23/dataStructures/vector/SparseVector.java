/**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 *
 * Persistent Vectors as binary trees, where some cells corresponding to
 * equal+adjacent values are compressed into a single node.
 */

package dataStructures.vector;

public class SparseVector<T> {

    private static abstract class Tree<T>{}

    private static class Unif<T> extends Tree<T> {
        T elem;
        public Unif(T e) { elem = e; }
    }

    private static class Node<T> extends Tree<T> {
        Tree<T> left, right;
        public Node(Tree<T> l, Tree<T> r) {
            left = l;
            right = r;
        }
    }


    private int size;
    private Tree<T> root;


    private static <T> Tree<T> simplify(Node<T> node) {
        if(node.left instanceof Unif<?> && node.right instanceof Unif<?>) {
            Unif<T> unif1 = (Unif<T>) node.left;
            Unif<T> unif2 = (Unif<T>) node.right;
            if(unif1.elem.equals(unif2.elem))
                return unif1;
        }
        return node;
    }


    public SparseVector(int exp, T elem) {
        if(exp<0)
            throw new VectorException("SparseVector: negative size");
        size = (int) Math.pow(2,exp);
        root = new Unif<>(elem);
    }


    public int size() { return size; }


    public void set(int i, T x) {
        if(i<0 || i>=size)
            throw new VectorException("set: out of bounds exception "+i);
        else
            root = set(size, i, x, root);
    }

    private static <T> Tree<T> set(int sz, int i, T x, Tree<T> tree) {
        if(sz < 2)
            return new Unif<>(x);
        else {
            int newSz = sz / 2;
            if(tree instanceof Unif<?>) {
                Unif<T> unif = (Unif<T>) tree;
                if(unif.elem.equals(x))
                    return unif;
                else if(i < newSz)
                    return new Node<>(set(newSz, i, x, unif), unif);
                else
                    return new Node<>(unif, set(newSz, i-newSz, x, unif));
            } else {
                Node<T> node = (Node<T>) tree;
                if (i < newSz)
                    node.left = set(newSz, i, x, node.left);
                else
                    node.right = set(newSz, i-newSz, x, node.right);
                return simplify(node);
            }
        }
    }

    public T get(int i) {
        if(i<0 || i>=size)
            throw new VectorException("get: out of bounds exception "+i);
        else
            return get(size, i, root);
    }

    private static <T> T get(int sz, int i, Tree<T> tree) {
        if(tree instanceof Unif<?>) {
            Unif<T> unif = (Unif<T>) tree;
            return unif.elem;
        } else {
            Node<T> node = (Node<T>) tree;
            int newSz = sz / 2;
            if(i < newSz)
                return get(newSz, i, node.left);
            else
                return get(newSz, i-newSz, node.right);
        }
    }

    private static <T> void toStringRec(StringBuilder sb, Tree<T> tree) {
        if(tree instanceof Unif<?>) {
            Unif<T> unif = (Unif<T>) tree;
            sb.append("Unif<");
            sb.append(unif.elem);
            sb.append(">");
        } else {
            Node<T> node = (Node<T>) tree;
            sb.append("Node<");
            toStringRec(sb, node.left);
            sb.append(",");
            toStringRec(sb, node.right);
            sb.append(">");
        }
    }

    @Override public String toString() {
        String className = getClass().getSimpleName();
        StringBuilder sb = new StringBuilder(className);
        sb.append("(");
        toStringRec(sb, root);
        sb.append(")");
        return sb.toString();
    }

}
