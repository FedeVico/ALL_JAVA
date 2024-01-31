//Student's name:
//Student's group:
//Identity number (DNI if Spanish/passport if Erasmus):

package dataStructures.set;
import dataStructures.list.List;
import dataStructures.list.LinkedList;

public class TreeBitSet {
  private static final int BITS_PER_LEAF = LongBits.BITS_PER_LONG;
  private interface Tree {
    long size();
    boolean contains(long element, long capacity);
    void add(long element, long capacity);
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

//-------------------------------------------------------------------
// DO NOT MODIFY ANY CODE ABOVE THIS LINE
//-------------------------------------------------------------------

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
    public List<Long> toList(long capacity) {
      List<Long> list = new LinkedList<>();
      for (long i = 0; i < BITS_PER_LEAF; i++)
        if (LongBits.contains(bitset, i))
          list.append(i);
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
      long half = capacity / 2;
      return element < half ? left.contains(element, half) : right.contains(element - half, half);
    }
    public void add(long element, long capacity) {
      long half = capacity / 2;
      if (element < half)
        left.add(element, half);
      else
        right.add(element - half, half);
    }
    public List<Long> toList(long capacity) {
      long half = capacity / 2;
      List<Long> leftList = left.toList(half);
      List<Long> rightList = right.toList(half);
      for (long element : rightList)
        leftList.append(element + half);
      return leftList;
    }
  }


  // * Exercise 1 * -
  private static Tree makeTree(long capacity) {
    if (capacity <= BITS_PER_LEAF)
      return new Leaf(0);
    else {
      long half = capacity / 2;
      return new Node(makeTree(half), makeTree(half));
    }
  }

  // * Exercise 2 * -
  public TreeBitSet(long capacity) {
    if (capacity <= 0)
      throw new IllegalArgumentException("capacity must be positive");
    if (!isValidCapacity(capacity))
      throw new IllegalArgumentException("capacity must be 64 multiplied by a power of 2");
    this.root = makeTree(capacity);
    this.capacity = capacity;
  }

  // * Exercise 3 * -
  public long capacity() {
    return capacity;
  }

  // * Exercise 4 * -
  private boolean outOfRange(long element) {
    return element < 0 || element >= capacity;
  }

  // * Exercise 5 * -
  public long size() {
    return root.size();
  }

  // * Exercise 6 * -
  public boolean isEmpty() {
    return size() == 0;
  }

  // * Exercise 7 * -
  public boolean contains(long element) {
    if (outOfRange(element))
      return false;
    return root.contains(element, capacity);
  }

  // * Exercise 8 * -
  public void add(long element) {
    if (outOfRange(element))
      throw new IllegalArgumentException("element is out of range");
    root.add(element, capacity);
  }

  // * Exercise 9 * -
  public List<Long> toList() {
    return root.toList(capacity);
  }


  //-------------------------------------------------------------------
  // Only for students without continuous assessment
  //-------------------------------------------------------------------

  // * Exercise 10 * -
  public static TreeBitSet union(TreeBitSet set1, TreeBitSet set2) {
    throw new UnsupportedOperationException("to be implemented");
  }

  // * Exercise 11 * -
  public static TreeBitSet extendedUnion(TreeBitSet set1, TreeBitSet set2) {
    throw new UnsupportedOperationException("to be implemented");
  }


  //-------------------------------------------------------------------
  // Basic program for testing your implementation
  //-------------------------------------------------------------------
  public static void main(String[] args) {
    TreeBitSet set = new TreeBitSet(64*2*2*2);
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

    System.out.println(set.contains(11));
    System.out.println(set.contains(272));

    System.out.println(set.toList());
  }
}