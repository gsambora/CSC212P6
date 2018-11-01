package edu.smith.cs.csc212.p6;

import edu.smith.cs.csc212.p6.errors.EmptyListError;
import edu.smith.cs.csc212.p6.errors.P6NotImplemented;



public class DoublyLinkedList<T> implements P6List<T> {
	private Node<T> start;
	private Node<T> end;
	
	/**
	 * A doubly-linked list starts empty.
	 */
	public DoublyLinkedList() {
		this.start = null;
		this.end = null;
	}
	
	/**
	 * O(1) - Easy, since start just has to be updated 
	 */
	@Override
	public T removeFront() {
		checkNotEmpty();
		T front = this.start.value;
		
		if ( this.start.after != null ) {
			this.start.after.before = null;
			this.start = this.start.after;
			
		}
		else { 
			this.start = null;
			this.end = null;
		}
		
		return front;
		
	}

	/**
	 * O(1) - Easy since end just has to be updated
	 */
	@Override
	public T removeBack() {
		checkNotEmpty();
		T back = this.end.value;
		
		if ( this.end.before != null ) {
			this.end.before.after = null;
			this.end = this.end.before;
			
		}
		else { 
			this.start = null;
			this.end = null;
		}
		
		return back;

	}

	/**
	 * O(n) - Not easy, since we have to loop to find where the index is to update all pointers
	 */
	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		T removed = null;
		int at = 0;
		if ( index == 0 ) {
			removed = this.start.value;
			this.removeFront();
		}
		else {
			for (Node<T> current = start; current != null; current = current.after) {
				if ( at + 1 == index ) {
					removed = current.after.value;

					if ( current.after.after != null ) {
						current.after = current.after.after;
						current.after.before = current;
					} else {
						current.after = null;
						this.end = current;
					}
					break;
				}	
				else { 
					at++;
				}
			}
		}
		return removed;
	}

	/**
	 * O(1) - Update the start and pointers of start and second item
	 */
	@Override
	public void addFront(T item) {
		if ( this.start == null ) {
			this.start = new Node<T>( item );
		}
		else {
			this.start.before = new Node<T>( item );
			this.start.before.after = this.start;
			this.start = this.start.before;
		}
	}

	/**
	 * O(1) - Update the end and pointers of end and second to last item
	 */
	@Override
	public void addBack(T item) {
		if ( this.start == null ) {
			this.start = new Node<T>( item );
		}
		else {
			for (Node<T> current = start; current != null; current = current.after) {
				if ( current.after == null ) {
					current.after = new Node<T>( item );
					current.after.before = current;
					this.end = current.after;
					break;
				}
			}
		}
	}

	/**
	 * O(n) - Go through items of the list until the index is found 
	 * and fix pointers of surrounding nodes
	 */
	@Override
	public void addIndex(T item, int index) {
		int at = 0;
		checkNotEmpty();
		Node<T> newNode = new Node<T>( item );
		for (Node<T> current = start; current != null; current = current.after) {
			if ( at + 1 == index ) {
				newNode.before = current;
				newNode.after = current.after;
				if ( current.after != null ) {
					current.after.before = newNode;
				}
				current.after = newNode;
				break;
			}
			else {
				at ++;
			}
		}
	}

	/**
	 * O(1) - We've been keeping track of start, so this is a quick grab
	 */
	@Override
	public T getFront() {
		return start.value;
	}

	/**
	 * O(1) - We've been keeping track of end, so this is a quick grab
	 */
	@Override
	public T getBack() {
		return end.value;
	}
		
	/**
	 * O(n) - Must go through everything in the list until the index is found
	 */
	@Override
	public T getIndex(int index) {
		int at = 0;
		T value = null;
		checkNotEmpty();
		for (Node<T> current = start; current != null; current = current.after) {
			if ( at == index ) {
				value = current.value;
				break;
			} else {
				at++;
			}
		}
		return value;
	}

	/**
	 * O(n) - Must go through the whole list to count up items 
	 */
	@Override
	public int size() {
		int at = 0;
		for (Node<T> current = start; current != null; current = current.after) {
			at ++;
		}
		return at;
	}

	/**
	 * O(1) - Easy, since if there is no start there are no values in the list
	 */
	@Override
	public boolean isEmpty() {
		return ( this.start == null );
	}
	
	private void checkNotEmpty() {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
	}
	
	/**
	 * The node on any linked list should not be exposed.
	 * Static means we don't need a "this" of DoublyLinkedList to make a node.
	 * @param <T> the type of the values stored.
	 */
	private static class Node<T> {
		/**
		 * What node comes before me?
		 */
		public Node<T> before;
		/**
		 * What node comes after me?
		 */
		public Node<T> after;
		/**
		 * What value is stored in this node?
		 */
		public T value;
		/**
		 * Create a node with no friends.
		 * @param value - the value to put in it.
		 */
		public Node(T value) {
			this.value = value;
			this.before = null;
			this.after = null;
		}
	}
}
