/**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 *
 * Maxiphobic Heaps
 */
 
package dataStructures.heap;


/**
 * Heap implemented using maxiphobic heap-ordered binary trees.
 * @param <T> Type of elements in heap.
 */
public class MaxiphobicHeap<T extends Comparable<? super T>> implements	Heap<T> {

	private static class Tree<E> {
		private E elem;
		private int size;
		private Tree<E> left;
		private Tree<E> right;
	}

	private static int size(Tree<?> heap) {
		return heap == null ? 0 : heap.size;
	}

	private static <T extends Comparable<? super T>> Tree<T> merge(Tree<T> h1,	Tree<T> h2) {
		if (h1 == null)
			return h2;
		if (h2 == null)
			return h1;

		// force h1 to have smaller root
		if (h2.elem.compareTo(h1.elem) < 0) {
			// swap heap1 and heap2
			Tree<T> tmp = h1;
			h1 = h2;
			h2 = tmp;
		}

		// update size of merged tree
		h1.size += h2.size;

		// get the three subtrees
		Tree<T> child1 = h1.left;
		Tree<T> child2 = h1.right;
		Tree<T> child3 = h2;

		// force child1 to be the one with more elements of the three subtrees
		if (size(child1) < size(child2)) {
			Tree<T> tmp = child1;
			child1 = child2;
			child2 = tmp;
		}
		if (size(child1) < size(child3)) {
			Tree<T> tmp = child1;
			child1 = child3;
			child3 = tmp;
		}

		// rebuild tree
		h1.left = child1;
		h1.right = merge(child2, child3);
		return h1;
	}

	private Tree<T> root;

	/**
	 * Creates an empty Maxiphobic Heap. 
	 * <p>Time complexity: O(1)
	 */
	public MaxiphobicHeap() {
		root = null;
	}

	/** 
	 * {@inheritDoc}
	 * <p>Time complexity: O(1)
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/** 
	 * {@inheritDoc}
	 * <p>Time complexity: O(1)
	 */
	public int size() {
		return root == null ? 0 : root.size;
	}

	/** 
	 * {@inheritDoc}
	 * <p>Time complexity: O(1)
	 * @throws <code>EmptyHeapException</code> if heap stores no element.
	 */
	public T minElem() {
		if (isEmpty())
			throw new EmptyHeapException("minElem on empty heap");
		else
			return root.elem;
	}

	/** 
	 * {@inheritDoc}
	 * <p>Time complexity: O(log n)
	 * @throws <code>EmptyHeapException</code> if heap stores no element.
	 */
	public void delMin() {
		if (isEmpty())
			throw new EmptyHeapException("delMin on empty heap");
		else
			root = merge(root.left, root.right);
	}

	/** 
	 * {@inheritDoc}
	 * <p>Time complexity: O(log n)
	 */
	public void insert(T value) {
		Tree<T> newHeap = new Tree<>();
		newHeap.elem = value;
		newHeap.size = 1;
		newHeap.left = null;
		newHeap.right = null;

		root = merge(root, newHeap);
	}

	public void clear() {
		root = null;
	}

	private static String toStringRec(Tree<?> tree) {
		return tree == null ? "null" : "Node<" + toStringRec(tree.left) + ","
				+ tree.elem + "," + toStringRec(tree.right) + ">";
	}
	
	/** 
	 * Returns representation of heap as a String.
	 */
  @Override public String toString() {
    String className = getClass().getSimpleName();

  	return className+"("+toStringRec(this.root)+")";
  }	
	
}