/**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 *
 * Skew Heaps
 */

package dataStructures.heap;

/**
 * Heap implemented using skew heap-ordered binary trees.
 * @param <T> Type of elements in heap.
 */
public class SkewHeap<T extends Comparable<? super T>> implements Heap<T> {

	private static class Tree<E> {
		private E elem;
		private Tree<E> left;
		private Tree<E> right;
	}

	private static int size(Tree<?> heap) {
		return heap == null ? 0
				            : 1 + size(heap.left) + size(heap.right);
	}

	private static <T extends Comparable<? super T>> Tree<T> merge(Tree<T> h1,	Tree<T> h2) {
		if (h1 == null)
			return h2;
		if (h2 == null)
			return h1;

		Tree<T> tmp;

		// force h1 to have smaller root
		if (h2.elem.compareTo(h1.elem) < 0) {
			// swap heap1 and heap2
			tmp = h1;
			h1 = h2;
			h2 = tmp;
		}

		// merge right spines and swap
		tmp = h1.left;
		h1.left = merge(h2, h1.right);
		h1.right = tmp;
		return h1;
	}

	private Tree<T> root;

	/**
	 * Creates an empty Skew Heap.
	 * <p>Time complexity: O(1)
	 */
	public SkewHeap() {
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
		return size(root);
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
		newHeap.left = null;
		newHeap.right = null;

		root = merge(root, newHeap);
	}

	public void clear() {
		root = null;
	}

	private static void BuildStringRec(StringBuilder sb, Tree<?> tree) {
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

	/**
	 * Returns representation of heap as a String.
	 */
  	@Override public String toString() {
		StringBuilder sb = new StringBuilder(getClass().getSimpleName());
    	sb.append("(");
		BuildStringRec(sb, this.root);
		sb.append(")");
  		return sb.toString();
  	}
}
