//Student's name:
//Student's group:
//Identity number (DNI if Spanish/passport if Erasmus):

import dataStructures.list.List;
import dataStructures.list.ArrayList;
// import dataStructures.list.LinkedList;

public class TreeBitSet {
  private static final int BITS_PER_LEAF = LongBits.BITS_PER_LONG;

  private interface Tree {
    long size();

    boolean contains(long element, long capacity);

    void add(long element, long capacity);

    void delete(long element, long capacity);

    List<Long> toList(long capacity);
  }

  private final Tree root;
  private final long capacity;

  // returns true if capacity is 64 * 2^n for some n >= 0
  private static boolean isValidCapacity(long capacity) {
    if (capacity <= 0) {
      return false;
    }
    while (capacity > BITS_PER_LEAF) {
      if (capacity % 2 != 0) {
        return false;
      }
      capacity /= 2;
    }
    return capacity == BITS_PER_LEAF;
  }

  // -------------------------------------------------------------------
  // DO NOT MODIFY ANY CODE ABOVE THIS LINE
  // -------------------------------------------------------------------

  private static class Leaf implements Tree {
    private long bitset;

    public Leaf(long bitset) {
      this.bitset = bitset;
    }

    public long size() {
      return LongBits.countOnes(bitset);
    }

    public boolean contains(long element, long capacity) {
      return LongBits.contains(bitset, element);
    }

    public void add(long element, long capacity) {
      bitset = LongBits.set(bitset, element);
    }

    // este es creado por mi
    public void delete(long element, long capacity) {
      bitset = LongBits.delete(bitset, element);
    }

    public List<Long> toList(long capacity) {
      List<Long> list = new ArrayList<>();
      for (long i = 0; i < BITS_PER_LEAF; i++) {
        if (LongBits.contains(bitset, i))
          list.append(i);
      }
      return list;
    }

  }

  private static class Node implements Tree {
    private final Tree left, right;

    public Node(Tree left, Tree right) {
      this.left = left;
      this.right = right;
    }

    public long size() {
      return left.size() + right.size();
    }

    public boolean contains(long element, long capacity) {
      long mitad = capacity / 2;
      if (element < mitad) {
        return left.contains(element, mitad);
      } else {
        return right.contains(element - mitad, mitad);
      }
    }

    public void add(long element, long capacity) {
      long mitad = capacity / 2;
      if (element < mitad) {
        left.add(element, mitad);
      } else {
        right.add(element - mitad, mitad);
      }
    }

    public void delete(long element, long capacity) {
      long mitad = capacity / 2;
      if (element < mitad) {
        left.delete(element, mitad);
      } else {
        right.delete(element - mitad, mitad);
      }
    }

    public List<Long> toList(long capacity) {
      long mitad = capacity / 2;
      List<Long> listLeft = left.toList(mitad);
      List<Long> listRight = right.toList(mitad);
      for (long elem : listRight) {
        listLeft.append(elem + mitad);
      }
      return listLeft;
    }
  }

  // * Exercise 1 * -
  private static Tree makeTree(long capacity) {
    if (capacity <= BITS_PER_LEAF) {
      return new Leaf(0);
    } else {
      return new Node(makeTree(capacity / 2), makeTree(capacity / 2));
    }
  }

  // * Exercise 2 * -
  public TreeBitSet(long capacity) {
    if (capacity <= 0) {
      throw new IllegalArgumentException("capacity must be positive");
    }
    if (!isValidCapacity(capacity)) {
      throw new IllegalArgumentException("capacity must be 64 multiplied by a power of 2");
    }
    this.root = makeTree(capacity);
    this.capacity = capacity;
  }

  private TreeBitSet(long capacity, Tree root) {
    // TODO Auto-generated constructor stub
    this.capacity = capacity;
    this.root = root;
  }

  // * Exercise 3 * -
  public long capacity() {
    return capacity;
  }

  // * Exercise 4 * -
  private boolean outOfRange(long element) {
    if (element >= capacity || element < 0)
      return true;
    else
      return false;
  }

  // * Exercise 5 * -
  public long size() {
    return root.size();
  }

  // * Exercise 6 * -
  public boolean isEmpty() {
    return root.size() == 0;
  }

  // * Exercise 7 * -
  public boolean contains(long element) {
    if (outOfRange(element)) {
      return false;
    } else {
      return root.contains(element, capacity);
    }
  }

  // * Exercise 8 * -
  public void add(long element) {
    if (outOfRange(element)) {
      throw new IllegalArgumentException("element is out of range");
    } else {
      root.add(element, capacity);
    }
  }

  public void delete(long element) {
    if (outOfRange(element)) {
      throw new IllegalArgumentException("element is out of range");
    } else {
      root.delete(element, capacity);
    }
  }

  // * Exercise 9 * -
  public List<Long> toList() {
    return root.toList(capacity);
  }

  // -------------------------------------------------------------------
  // Only for students without continuous assessment
  // -------------------------------------------------------------------

  // * Exercise 10 * -
  public static TreeBitSet union(TreeBitSet set1, TreeBitSet set2) {
    if (set1.capacity == set2.capacity) {
      Tree root = unionRec(set1.root, set2.root);
      return new TreeBitSet(set1.capacity(), root);
    } else {
      throw new IllegalArgumentException("capacity is not the same");
    }
  }

  private static Tree unionRec(Tree tree1, Tree tree2) {
    if (tree1 instanceof Leaf && tree2 instanceof Leaf) {
      Leaf hoja1 = (Leaf) tree1;
      Leaf hoja2 = (Leaf) tree2;
      long newBitSet = LongBits.or(hoja1.bitset, hoja2.bitset);
      return new Leaf(newBitSet);
    }
    Node node1 = (Node) tree1;
    Node node2 = (Node) tree2;
    Tree left = unionRec(node1.left, node2.left);
    Tree right = unionRec(node1.right, node2.right);
    return new Node(left, right);
  }

  // * Exercise 11 * -
  public static TreeBitSet extendedUnion(TreeBitSet set1, TreeBitSet set2) {
    throw new UnsupportedOperationException("to be implemented");
  }

  private static TreeBitSet clone(TreeBitSet set) {
    TreeBitSet setCloned = new TreeBitSet(set.capacity());
    for (long i : set.toList()) {
      setCloned.add(i);
    }
    return setCloned;
  }

  // -------------------------------------------------------------------
  // Basic program for testing your implementation
  // -------------------------------------------------------------------
  public static void main(String[] args) {
    TreeBitSet set = new TreeBitSet(64 * 2 * 2 * 2);
    set.add(0);
    set.add(1);
    set.add(2);
    set.add(3);
    set.add(4);
    set.add(5);
    set.add(6);
    set.add(7);
    set.add(8);
    set.add(9);
    set.add(10);
    set.add(120);
    set.add(128);
    set.add(270);

    System.out.println(set.size());

    System.out.println(set.contains(6));
    System.out.println(set.contains(128));
    System.out.println(set.contains(270));

    set.delete(6);

    System.out.println(set.contains(6));
    System.out.println(set.contains(272));

    System.out.println(set.toList());

    TreeBitSet set2 = clone(set);

    set2.delete(128);
    set2.delete(0);
    set2.delete(6);
    set2.delete(9);
    System.out.println(set2.toList());

    TreeBitSet setUnion = union(set, set2);
    System.out.println("Union de sets: " + setUnion.toList());

  }
}