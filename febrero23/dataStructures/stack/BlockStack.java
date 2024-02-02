/**
 * @author Pablo López, Data Structures, Grado en Informática. UMA.
 *
 * Stack implementation using a linked list of blocks
 */

package dataStructures.stack;

/**
 * Stack implemented using a linked list of blocks.
 * 
 * @param <T>
 *            Type of elements in stack.
 */
public class BlockStack<T> implements Stack<T> {

	protected static class Block<E> {
		final static int BLOCK_CAPACITY = 4;

		E[] elements;
		Block<E> previous;

		@SuppressWarnings("unchecked")
		public Block(Block<E> block) {
			elements = (E[]) new Object[BLOCK_CAPACITY];
			previous = block;
		}
	}

	Block<T> topBlock;
	int top;

	/**
	 * Creates an empty stack.
	 * <p>
	 * Time complexity: O(1)
	 */
	public BlockStack() {
		topBlock = null;
		top = -1;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Time complexity: O(1)
	 */
	@Override
	public boolean isEmpty() {
		return topBlock == null;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Time complexity: O(1) best case, O(n) worst case
	 */
	@Override
	public void push(T x) {
		if (topBlock == null || top == Block.BLOCK_CAPACITY - 1) {
			topBlock = new Block<>(topBlock);
			top = -1;
		}
		top++;
		topBlock.elements[top] = x;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Time complexity: O(1)
	 * 
	 * @throws EmptyStackException
	 *             {@inheritDoc}
	 */
	@Override
	public T top() {
		if (isEmpty()) {
			throw new EmptyStackException("top on empty stack");
		} else {
			return topBlock.elements[top];
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Time complexity: O(1)
	 * 
	 * @throws EmptyStackException
	 *             {@inheritDoc}
	 */
	@Override
	public void pop() {
		if (isEmpty()) {
			throw new EmptyStackException("pop on empty stack");
		} else {
			top--;
			if (top == -1) {
				topBlock = topBlock.previous;
				top = Block.BLOCK_CAPACITY - 1;
			}
		}
	}

	/**
	 * Returns representation of stack as a String.
	 */
	@Override
	public String toString() {
		String text = getClass().getSimpleName()+"(";
		int n = top;
		for (Block<T> block = topBlock; block != null; block = block.previous) {
			for (int i = n; i > 0; i--) {
				text = text + block.elements[i] + ", ";
			}
			text = text + block.elements[0];
			if (block.previous != null)
				text = text + ", ";
			n = Block.BLOCK_CAPACITY - 1;
		}
		return text + ")";
	}
}
