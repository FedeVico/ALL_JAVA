package dataStructures.searchTree;

/**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 * @author Pablo López (solution to posed exercises)
 *
 * Search tree implemented using an unbalanced binary search tree augmented with
 * weight on nodes. Note that elements should define an order relation (
 * {@link Comparable}).
 *
 * @param <T>
 *            Type of keys.
 */
public class AugmentedBST<T extends Comparable<? super T>> {

    // class for implementing one one in search tree
    private static class Tree<E> {
        E key; // value stored in node
        int weight; // weight of node: total number of elements stored in tree
        // rooted at this node
        Tree<E> left;
        Tree<E> right;

        public Tree(E k) {
            key = k;
            weight = 1;
            left = null;
            right = null;
        }
    }

    private Tree<T> root; // reference to root node of binary search tree

    /**
     * Creates an empty unbalanced binary search tree.
     * <p>
     * Time complexity: O(1)
     */
    public AugmentedBST() {
        root = null;
    }

    /**
     * <p>
     * Time complexity: O(1)
     */
    public boolean isEmpty() {
        return root == null;
    }

    private static <T> int weight(Tree<T> node) {
        return node == null ? 0 : node.weight;
    }

    /**
     * <p>
     * Time complexity: O(1)
     */
    public int size() {
        return weight(root);
    }

    /**
     * <p>
     * Time complexity: from O(log n) to O(n)
     */
    public void insert(T k) {
        root = insertRec(root, k);
    }

    // returns modified tree
    private Tree<T> insertRec(Tree<T> node, T key) {
        if (node == null) {
            node = new Tree<>(key);
        } else if (key.compareTo(node.key) < 0)
            node.left = insertRec(node.left, key);
        else if (key.compareTo(node.key) > 0)
            node.right = insertRec(node.right, key);
        else
            node.key = key;

        // recompute weight for this node after insertion
        node.weight = 1 + weight(node.left) + weight(node.right);

        return node;
    }

    /**
     * <p>
     * Time complexity: from O(log n) to O(n)
     */
    public T search(T key) {
        return searchRec(root, key);
    }

    private static <T extends Comparable<? super T>> T searchRec(Tree<T> node,
                                                                 T key) {
        if (node == null)
            return null;
        else if (key.compareTo(node.key) < 0)
            return searchRec(node.left, key);
        else if (key.compareTo(node.key) > 0)
            return searchRec(node.right, key);
        else
            return node.key;
    }

    /**
     * <p>
     * Time complexity: from O(log n) to O(n)
     */
    public boolean isElem(T key) {
        return search(key) != null;
    }

    /**
     * precondition: node and temp are non-empty trees Removes node with minimum
     * key from tree rooted at node. Before deletion, key is saved into temp
     * node. returns modified tree (without min key)
     */
    private static <T extends Comparable<? super T>> Tree<T> split(
            Tree<T> node, Tree<T> temp) {
        if (node.left == null) {
            // min node found, so copy min key in temp node
            temp.key = node.key;
            return node.right; // remove node
        } else {
            // remove min from left subtree
            node.left = split(node.left, temp);
            return node;
        }
    }

    /**
     * <p>
     * Time complexity: from O(log n) to O(n)
     */
    public void delete(T key) {
        root = deleteRec(root, key);
    }

    // returns modified tree
    private Tree<T> deleteRec(Tree<T> node, T key) {
        if (node == null)
            ; // key not found; do nothing
        else {
            if (key.compareTo(node.key) < 0)
                node.left = deleteRec(node.left, key);
            else if (key.compareTo(node.key) > 0)
                node.right = deleteRec(node.right, key);
            else {
                if (node.left == null)
                    node = node.right;
                else if (node.right == null)
                    node = node.left;
                else
                    node.right = split(node.right, node);
            }
            // recompute weight for this node after deletion
            node.weight = 1 + weight(node.left) + weight(node.right);
        }
        return node;
    }

    /**
     * Returns representation of tree as a String.
     */
    @Override
    public String toString() {
        String className = getClass().getSimpleName();
        return className + "(" + toStringRec(this.root) + ")";
    }

    private static String toStringRec(Tree<?> tree) {
        return tree == null ? "null" : "Node<" + toStringRec(tree.left) + ","
                + tree.key + "," + tree.weight + "," + toStringRec(tree.right)
                + ">";
    }

    // You should provide EFFICIENT implementations for the following methods

    // returns i-th smallest key in BST (i=0 means returning the smallest value
    // in tree, i=1 the next one and so on).
    public T select(int i) {
        if (i < 0 || i >= root.weight) {
            return null;
        } else {
            return selectRec(root, i);
        }
    }

    private T selectRec(Tree<T> t, int i) {
        if (i == weight(t.left)) {
            return t.key;
        } else if (i < weight(t.left)) {
            return selectRec(t.left, i);
        } else {
            // i > weight(t.left))
            return selectRec(t.right, i - (weight(t.left) + 1));
        }
    }

    // returns largest key in BST whose value is less than or equal to k.
    public T floor(T k) {
        return floorRec(root, k);
    }

    private T floorRec(Tree<T> t, T k) {
        if (t == null)
            return null;
        else if (t.key.compareTo(k) == 0) {
            return k;
        } else if (t.key.compareTo(k) > 0) {
            return floorRec(t.left, k);
        } else {
            T toRight = floorRec(t.right, k);
            if (toRight != null) {
                return toRight;
            } else {
                return t.key;
            }
        }
    }

    // returns smallest key in BST whose value is greater than or equal to k.
    public T ceiling(T k) {
        return ceilingRec(root, k);
    }

    private T ceilingRec(Tree<T> t, T k) {
        if (t == null)
            return null;
        else if (t.key.compareTo(k) == 0) {
            return k;
        } else if (t.key.compareTo(k) < 0) {
            return ceilingRec(t.right, k);
        } else {
            T toLeft = ceilingRec(t.left, k);
            if (toLeft != null) {
                return toLeft;
            } else {
                return t.key;
            }
        }
    }

    // returns number of keys in BST whose values are less than k.
    public int rank(T k) {
        return rankRec(root, k);
    }

    private int rankRec(Tree<T> t, T k) {
        if (t == null) {
            return 0;
        } else if (t.key.compareTo(k) == 0) {
            return weight(t.left);
        } else if (t.key.compareTo(k) > 0) {
            return rankRec(t.left, k);
        } else {
            return weight(t.left) + 1 + rankRec(t.right, k);
        }
    }

    // returs number of keys in BST whose values are in range low to high.
    public int size(T low, T high) {
        return sizeRec(root, low, high);
    }

    private int sizeRec(Tree<T> t, T low, T high) {
        if (t == null) {
            return 0;
        } else if (t.key.compareTo(high) > 0) {
            return sizeRec(t.left, low, high);
        } else if (t.key.compareTo(low) < 0) {
            return sizeRec(t.right, low, high);
        } else { // t.key in [low, high]
            if (t.key.compareTo(low) == 0) {
                return 1 + lessEqRec(t.right, high);
            } else if (t.key.compareTo(high) == 0) {
                return 1 + greaterEqRec(t.left, low);
            } else {
                return 1 + greaterEqRec(t.left, low) + lessEqRec(t.right, high);
            }
        }
    }

    private int greaterEqRec(Tree<T> t, T k) {
        if (t == null) {
            return 0;
        } else if (t.key.compareTo(k) == 0) {
            return 1 + weight(t.right);
        } else if (t.key.compareTo(k) < 0) {
            return greaterEqRec(t.right, k);
        } else {
            return 1 + weight(t.right) + greaterEqRec(t.left, k);
        }
    }

    private int lessEqRec(Tree<T> t, T k) {
        if (t == null) {
            return 0;
        } else if (t.key.compareTo(k) == 0) {
            return 1 + weight(t.left);
        } else if (t.key.compareTo(k) < 0) {
            return 1 + weight(t.left) + lessEqRec(t.right, k);
        } else {
            return lessEqRec(t.left, k);
        }
    }

    // returning minimum key stored in BST
    public T min() {
        if (root == null) {
            return null;
        } else {
            return minIter(root);
        }
    }

    private T minIter(Tree<T> t) {
        while (t.left != null) {
            t = t.left;
        }
        return t.key;
    }

    // returning maximum key stored in BST,
    public T max() {
        if (root == null) {
            return null;
        } else {
            return maxIter(root);
        }
    }

    private T maxIter(Tree<T> t) {
        while (t.right != null) {
            t = t.right;
        }
        return t.key;
    }
}