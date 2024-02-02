/**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 *
 * Braun Heaps
 *
 * A Braun tree is a binary tree such that, for every node,
 * weight(node.left) - weight(node.right) <= 1
 *
 * A Braun Heap is a Braun tree verifying the Heap Order Property (HOP)
 *
 * The structure of Braun trees depends only on its weight.
 * Shapes for Braun trees with different weights:
 *   1    2     3        4        5          6           7
 *
 *   o    o     o        o        o          o           o
 *       /     / \      / \      / \       /   \       /   \
 *      o     o   o    o   o    o   o     o     o     o     o
 *                    /        /   /     / \   /     / \   / \
 *                   o        o   o     o   o o     o   o o   o
 *
 * Note that, for each node, if it has a right child it also has a left one
 *
 */

package dataStructures.heap;

public class BraunHeap<T extends Comparable<? super T>> implements Heap<T> {
    private static class Node<E> {
        E elem;
        Node<E> left, right;
    }

    // O(n) version
    private static int slowWeight(Node<?> node) {
        return node == null ? 0 : 1 + weight(node.left) + weight(node.right);
    }

    // Okasaki's O(log^2 n) version:
    private static int weight(Node<?> node) {
        if(node == null)
            return 0;
        else {
            int wr = weight(node.right);
            return 1 + 2*wr + diff(node.left, wr);
        }
    }

    private static int diff(Node<?> node, int w) {
        if(node == null)
            return 0;
        else if(w == 0)
            return 1;
        else if(w % 2 == 0)
            return diff(node.right, (w-1) / 2);
        else
            return diff(node.left, w / 2);
    }

    private static <T extends Comparable<? super T>> boolean greaterEq(Node<T> node, T elem) {
        return node == null || node.elem.compareTo(elem) >= 0;
    }

    private static <T extends Comparable<? super T>> boolean isBraunHeap(Node<T> node) {
        return node == null
                || greaterEq(node.left, node.elem)
                    && greaterEq(node.right, node.elem)
                    && weight(node.left) - weight(node.right) <= 1
                    && isBraunHeap(node.left)
                    && isBraunHeap(node.right);
    }

    private Node<T> root;

    public BraunHeap() { root = null; }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return weight(root);
    }

    public void clear() { root = null; }

    public T minElem() {
        if (isEmpty())
            throw new EmptyHeapException("minElem on empty heap");
        else
            return root.elem;
    }

    static private <T> void swapChildren(Node<T> node) {
        Node<T> tmp = node.right;
        node.right = node.left;
        node.left = tmp;
    }

    static private <T> void swapElems(Node<T> node1, Node<T> node2) {
        T tmp = node1.elem;
        node1.elem = node2.elem;
        node2.elem = tmp;
    }

    public void insert(T elem) {
        Node<T> newNode = new Node<>();
        newNode.elem = elem;
        newNode.left = null;
        newNode.right = null;

        root = insertRec(root, newNode);
    }

    // We will insert on the right (as this is child with less number of elements) and
    // swap children in order to restore Braun Tree Invariant.
    // returns modified tree.
    static private <T extends Comparable<? super T>> Node<T> insertRec(Node<T> node, Node<T> newNode) {
        if (node == null) {
            node = newNode;
        } else {
            // Make node.value store minimum of itself and to be inserted element
            if(newNode.elem.compareTo(node.elem) < 0)
                swapElems(newNode, node);

            // Insert the other value on right child
            node.right = insertRec(node.right, newNode);
            swapChildren(node); // to guarantee Braun Tree Invariant
        }
        return node;
    }

    public void delMin() {
        if (isEmpty())
            throw new EmptyHeapException("delMin on empty heap");
        else {
            root = delMinRec(root);
        }
    }

    // We would like to to move element at root of left child to the root
    // and swap children in order to restore Braun Tree Invariant.
    // returns modified tree.
    static private <T extends Comparable<? super T>> Node<T> delMinRec(Node<T> node) {
        if(node.right == null) // node only has a left child
            node = node.left;
        else if(node.left.elem.compareTo(node.right.elem) <= 0) { // two children and minimum value on left
            node.elem = node.left.elem;

            node.left = delMinRec(node.left);
            swapChildren(node); // to guarantee Braun Tree Invariant
        } else { // two children and minimum value on right
            node.elem = node.right.elem;
            node.right.elem = node.left.elem; // may break Heap Order Property
            heapify(node.right); // to restore Heap Order Property

            node.left = delMinRec(node.left);
            swapChildren(node); // to guarantee Braun Tree Invariant
        }
        return node;
    }

    // Precondition: node is a non-empty tree
    // restores Heap Order Property if root was modified, without altering shape of tree
    static private <T extends Comparable<? super T>> void heapify(Node<T> node) {
        if(node.left == null && node.right == null) // node is a leaf
            ; // it's already a heap
        else if(node.right == null) { // node has a single child
            if(node.elem.compareTo(node.left.elem) <= 0)
                ; // it's already a heap
            else {
                swapElems(node, node.left); // swap elements and heapify single child
                heapify(node.left);
            }
        } else { // node has two children
            if(node.elem.compareTo(node.left.elem) <= 0 && node.elem.compareTo(node.right.elem) <= 0 )
                ; // it's already a heap
            else if(node.left.elem.compareTo(node.right.elem) <= 0) {
                swapElems(node, node.left); // minChild on left. swap elements and heapify left child
                heapify(node.left);
            } else {
                swapElems(node, node.right); // minChild on right. swap elements and heapify right child
                heapify(node.right);
            }
        }
    }

    private static void BuildStringRec(StringBuilder sb, Node<?> tree) {
        if(tree==null)
            sb.append("null");
        else {
            sb.append("Node<");
            BuildStringRec(sb,tree.left);
            sb.append(",");
            sb.append(tree.elem);
            sb.append(",");
            BuildStringRec(sb,tree.right);
            sb.append(">");
        }
    }

    @Override public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append("(");
        BuildStringRec(sb, root);
        sb.append(")");
        return sb.toString();
    }
}
