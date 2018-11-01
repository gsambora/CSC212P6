package edu.smith.cs.csc212.p6;

import java.util.Iterator;

import edu.smith.cs.csc212.p6.errors.EmptyListError;
import edu.smith.cs.csc212.p6.errors.P6NotImplemented;

public class SinglyLinkedList<T> implements P6List<T>, Iterable<T> {
	/**
	 * The start of this list. Node is defined at the bottom of this file.
	 */
	Node<T> start;

	/**
	 * O(1) - It is easy to take off the front, since we just update our start
	 */
	@Override
	public T removeFront() {
		checkNotEmpty();
		T before = start.value;
		start = start.next;
		return before;
	}

	/**
	 * O(n) - It is less easy to remove from the back, since the pointers must be fixed
	 * through a for loop
	 */
	@Override
	public T removeBack() {
		checkNotEmpty();
		T last = null;
		Node<T> nextToLast = null;
		
		if ( start.next == null ) {
			last = start.value;
			start = null;
			return last;
		}
		
		for (Node<T> current = start; current.next != null; current = current.next) {
			
			if ( current.next.next == null ) {
				last = current.next.value;
				current.next = null;
				return last;
			}
		}
		
		return last;
	}

	/**
	 * O(n) - This is difficult and annoying, since multiple pointers must be fixed with a loop. 
	 */
	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		int at = 0;
		T value = null;
		
		if ( this.size() == 1 ) {
			value = start.value;
			start = null;
			return value;
		}
		
		for (Node<T> current = start; current.next != null; current = current.next) {
			if ( at + 1 == index ) {
				value = current.next.value;
				
				if ( current.next.next != null ) {
					current.next = current.next.next;
				} else {
					current.next = null;
				}
				break;
			}
			else {
				at++;
			}
		}
		return value;
	}
	
	/**
	 * O(1) - Extremely easy, just create a new start with its "next" as the original start
	 */
	@Override
	public void addFront(T item) {
		this.start = new Node<T>(item, start);
	}

	/**
	 * O(n) - More difficult, since the last value's next pointer will have to be changed
	 * and we cannot find this without looping through everything
	 */
	@Override
	public void addBack(T item) {
		Node<T> last = null;
		for (Node<T> current = start; current != null; current = current.next) {
			last = current;
		}
		if (last != null) {
			last.next = new Node<T>(item,null);
		} else {
			start = new Node<T>(item,null);
		}
	}

	/**
	 * O(n) - We have to loop through the entire list to find our indexed value,
	 * then fix pointers as well
	 */
	@Override
	public void addIndex(T item, int index) {
		int at = 0;
		
		for (Node<T> current = start; current.next != null; current = current.next) {
			if ( at + 1 == index ) {
				Node<T> newNext = current.next;
				current.next = new Node<T>(item, newNext);
				break;
			}
			else {
				at++;
			}
		}
	}

	/**
	 * O(1) - Easy since we've been keeping track of what the front is 
	 */
	@Override
	public T getFront() {
		return start.value;
	}

	/**
	 * O(n) - We have to loop through everything to find the last item in the list
	 */
	@Override
	public T getBack() {
		T back = null;
		
		back = this.getIndex(this.size()-1);
		
		return back;
		//throw new P6NotImplemented();
	}

	/**
	 * O(n) - We have to loop through everything until we find the indexed value 
	 */
	@Override
	public T getIndex(int index) {
		int at = 0;
		T value = null;
		for (Node<T> current = start; current != null; current = current.next) {
			if (at == index) {
				value = current.value;
				return value;
			}
			else {
				at++;
			}
		}
		return value;
		//throw new IndexOutOfBoundsException();
	}

	/**
	 * O(n) - We have to loop through the whole list in order to count up the items 
	 */
	@Override
	public int size() {
		int count = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			count++;
		}
		return count;
	}

	/**
	 * O(1) - If the start of the list is null, that means nothing is in the list
	 */
	@Override
	public boolean isEmpty() {
		return start == null;
	}

	/**
	 * Helper method to throw the right error for an empty state.
	 */
	private void checkNotEmpty() {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
	}

	/**
	 * The node on any linked list should not be exposed. Static means we don't need
	 * a "this" of SinglyLinkedList to make a node.
	 * 
	 * @param <T> the type of the values stored.
	 */
	private static class Node<T> {
		/**
		 * What node comes after me?
		 */
		public Node<T> next;
		/**
		 * What value is stored in this node?
		 */
		public T value;

		/**
		 * Create a node with no friends.
		 * 
		 * @param value - the value to put in it.
		 */
		public Node(T value, Node<T> next) {
			this.value = value;
			this.next = next;
		}
	}

	/**
	 * I'm providing this class so that SinglyLinkedList can be used in a for loop
	 * for {@linkplain ChunkyLinkedList}. This Iterator type is what java uses for
	 * {@code for (T x : list) { }} lops.
	 * 
	 * @author jfoley
	 *
	 * @param <T>
	 */
	private static class Iter<T> implements Iterator<T> {
		/**
		 * This is the value that walks through the list.
		 */
		Node<T> current;

		/**
		 * This constructor details where to start, given a list.
		 * @param list - the SinglyLinkedList to iterate or loop over.
		 */
		public Iter(SinglyLinkedList<T> list) {
			this.current = list.start;
		}

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public T next() {
			T found = current.value;
			current = current.next;
			return found;
		}
	}
	
	/**
	 * Implement iterator() so that {@code SinglyLinkedList} can be used in a for loop.
	 * @return an object that understands "next()" and "hasNext()".
	 */
	public Iterator<T> iterator() {
		return new Iter<>(this);
	}
}
