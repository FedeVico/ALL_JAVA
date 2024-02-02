/**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 *
 * Stack implementation using a linked list of array
 *  
 */
 
package dataStructures.stack;

/**
 * Stack implemented using a linked list of arrays.
 * @param <T> Type of elements in stack.
 */
public class LinkedArrayStack<T> implements Stack<T> {

	static private class Node<E> {
		private static final int CAPACITY = 5; // each node can store up to 5 elements

		E[] elements; 
		Node<E> next;
		
		@SuppressWarnings("unchecked")
		public Node(Node<E> node) {
			elements =  (E[]) new Object[CAPACITY];
			next = node;			
		}
	}
	
	private Node<T> topNode;
	private int nextFree;
	
	/**
	 * Creates an empty stack.
	 * <p>Time complexity: O(1)
	 */
	public LinkedArrayStack() {
		topNode = null;
		nextFree = -1; 
	}
	
	/** 
	 * {@inheritDoc}
	 * <p>Time complexity: O(1)
	 */
	public boolean isEmpty() {
		return topNode == null;
	}
	
	/** 
	 * {@inheritDoc}
	 * <p>Time complexity: O(1)
	 * @throws EmptyStackException {@inheritDoc} 
	 */
	public T top() {
		if(isEmpty())
			throw new EmptyStackException("top on empty stack");
		else
			return topNode.elements[nextFree+1];
	}
	
	/** 
	 * {@inheritDoc}
	 * <p>Time complexity: O(1)
	 * @throws EmptyStackException {@inheritDoc} 
	 */	
	public void pop() {
		if (isEmpty())
			throw new EmptyStackException("pop on empty stack");
		else {	
			nextFree++;
			if (nextFree>=Node.CAPACITY-1) {
				// leftmost node became empty; unlink it
				topNode = topNode.next;
				// new leftmost node is full
				nextFree = -1;
			}
		}
	}

	
	/** 
	 * {@inheritDoc}
	 * <p>Time complexity: O(n)
	 */
	public void push(T x) {
	    if (nextFree < 0) {
			// allocate and link new node
			topNode = new Node<>(topNode);
			// new leftmost node is empty
			nextFree = Node.CAPACITY-1;
		}	
		topNode.elements[nextFree] = x;
		nextFree--;
	}
	
	/** 
	 * Returns representation of stack as a String.
	 */
	@Override public String toString() {
		String className = getClass().getSimpleName();
		String s = className+"(";
		int top = nextFree+1; 
		for(Node<T> node = topNode; node != null; node = node.next) {
			for(int j = top; j<Node.CAPACITY-1; j++) {
				s += node.elements[j] + ",";
			}
			s += node.elements[Node.CAPACITY-1];
			if(node.next != null)
				s += ",";
			top = 0;
		}
		s += ")";
		return s;			
	}
}
