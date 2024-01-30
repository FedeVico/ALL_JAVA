
/**
 * Student's name:
 *
 * Student's group:
 */

import dataStructures.list.List;
import dataStructures.list.ArrayList;
import dataStructures.list.LinkedList;

import java.util.Iterator;

class Bin {
    private int remainingCapacity; // capacity left for this bin
    private List<Integer> weights; // weights of objects included in this bin

    public Bin(int initialCapacity) {
        // todone
        remainingCapacity = initialCapacity;
        weights = new LinkedList<>();
    }

    // returns capacity left for this bin
    public int remainingCapacity() {
        // todone
        return remainingCapacity;
    }

    // adds a new object to this bin
    public void addObject(int weight) throws Exception {
        // todone
        if (weight > remainingCapacity)
            throw new Exception("weight is bigger than capacity");
        else {
            weights.append(weight);
            remainingCapacity -= weight;
        }
    }

    // returns an iterable through weights of objects included in this bin
    public Iterable<Integer> objects() {
        // todo
        // SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
        // ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
        return new Iterable<Integer>() {
            public Iterator<Integer> iterator() {
                return weights.iterator();
            }
        };
    }

    public String toString() {
        String className = getClass().getSimpleName();
        StringBuilder sb = new StringBuilder(className);
        sb.append("(");
        sb.append(remainingCapacity);
        sb.append(", ");
        sb.append(weights.toString());
        sb.append(")");
        return sb.toString();
    }
}

// Class for representing an AVL tree of bins
public class AVL {
    static private class Node {
        Bin bin; // Bin stored in this node
        int height; // height of this node in AVL tree
        int maxRemainingCapacity; // max capacity left among all bins in tree rooted at this node
        Node left, right; // left and right children of this node in AVL tree

        // recomputes height of this node
        void setHeight() {
            // todone
            int lh = 0, rh = 0;
            if (left != null) {
                left.setHeight();
                lh = left.height;
            }
            if (right != null) {
                right.setHeight();
                rh = right.height;
            }
            height = 1 + Math.max(lh, rh); // altura de la raiz + el lado que mas tenga
        }

        // recomputes max capacity among bins in tree rooted at this node
        void setMaxRemainingCapacity() {
            // todone
            int lc = 0, rc = 0;
            if (left != null) {
                left.setMaxRemainingCapacity();
                lc = left.maxRemainingCapacity;
            }
            if (right != null) {
                right.setMaxRemainingCapacity();
                rc = right.maxRemainingCapacity;
            }
            // Comparamos las capacidades de raiz, left y right
            maxRemainingCapacity = Math.max(bin.remainingCapacity(), Math.max(lc, rc));
        }

        // left-rotates this node. Returns root of resulting rotated tree
        Node rotateLeft() {
            // todone
            Node rt = this.right;
            Node rlt = rt.left;
            this.right = rlt;
            this.setMaxRemainingCapacity();
            this.setHeight();
            rt.left = this;
            this.setMaxRemainingCapacity();
            rt.setHeight();
            return rt;
        }
    }

    private static int height(Node node) {
        // todone
        return node.height;
    }

    private static int maxRemainingCapacity(Node node) {
        // todone
        return node.maxRemainingCapacity;
    }

    private Node root; // root of AVL tree

    public AVL() {
        this.root = null;
    }

    // adds a new bin at the end of right spine.
    private void addNewBin(Bin bin) {
        // todo
        root = addNewBinRec(root, bin);
    }

    private Node addNewBinRec(Node node, Bin bin) {
        if (node == null) {
            node = new Node();
            node.bin = bin;
            node.height = 1;
            node.maxRemainingCapacity = bin.remainingCapacity();
        } else if (node.right == null) {
            node.right = new Node();
            node.right.bin = bin;
            node.right.height = 1;
            node.right.maxRemainingCapacity = bin.remainingCapacity();
            node.right.setHeight();
            node.right.setMaxRemainingCapacity();
        } else if (node.left == null) {
            node.right = addNewBinRec(node.right, bin);
            node.right = node.right.rotateLeft();
        } else {
            node.right = addNewBinRec(node.right, bin);
            node.right.setHeight();
            node.right.setMaxRemainingCapacity();
        }
        return node;
    }

    // adds an object to first suitable bin. Adds
    // a new bin if object cannot be inserted in any existing bin
    public void addFirst(int initialCapacity, int weight) throws Exception {
        // todone
        root = addFirstRec(root, initialCapacity, weight);
    }

    private Node addFirstRec(Node node, int initialCapacity, int weight) throws Exception {
        if (node == null || weight > node.maxRemainingCapacity) {
            Bin bin = new Bin(initialCapacity);
            node = addNewBinRec(node, bin);
        } else if (node.left != null && node.left.maxRemainingCapacity >= weight) {
            node.left = addFirstRec(node.left, initialCapacity, weight);
        } else if (node.bin.remainingCapacity() >= weight) {
            node.bin.addObject(weight);
        } else {
            node.right.bin.addObject(weight);
        }
        node.setHeight();
        node.setMaxRemainingCapacity();

        return node;
    }

    public void addAll(int initialCapacity, int[] weights) throws Exception {
        // todone
        for (int w : weights) {
            addFirst(initialCapacity, w);
        }
    }

    public List<Bin> toList() {
        // todone
        if (root == null)
            return new ArrayList<>();
        else
            return toListRec(root);
    }

    private List<Bin> toListRec(Node node) {
        List<Bin> list1 = new LinkedList<>();
        List<Bin> list2 = new LinkedList<>();

        if (node.left != null) {
            list1 = toListRec(node.left);
        }
        list1.append(node.bin);

        if (node.right != null) {
            list2 = toListRec(node.right);
        }

        for (Bin bin : list2) {
            list1.append(bin);
        }
        return list1;
    }

    public String toString() {
        String className = getClass().getSimpleName();
        StringBuilder sb = new StringBuilder(className);
        sb.append("(");
        stringBuild(sb, root);
        sb.append(")");
        return sb.toString();
    }

    private static void stringBuild(StringBuilder sb, Node node) {
        if (node == null)
            sb.append("null");
        else {
            sb.append(node.getClass().getSimpleName());
            sb.append("(");
            sb.append(node.bin);
            sb.append(", ");
            sb.append(node.height);
            sb.append(", ");
            sb.append(node.maxRemainingCapacity);
            sb.append(", ");
            stringBuild(sb, node.left);
            sb.append(", ");
            stringBuild(sb, node.right);
            sb.append(")");
        }
    }
}

class LinearBinPacking {
    public static List<Bin> linearBinPacking(int initialCapacity, List<Integer> weights) throws Exception {
        // todo
        // SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
        // ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
        AVL avl = new AVL();
        avl.addFirst(initialCapacity, weights.get(0));
        for (int i = 1; i < weights.size(); i++) {
            avl.addFirst(initialCapacity, weights.get(i));
        }
        return avl.toList();
    }

    public static Iterable<Integer> allWeights(Iterable<Bin> bins) {
        // todo
        // SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
        // ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
        List<Integer> allWeights = new LinkedList<>();
        for (Bin bin : bins) {
            for (int weight : bin.objects()) {
                allWeights.append(weight);
            }
        }
        return allWeights;
    }
}