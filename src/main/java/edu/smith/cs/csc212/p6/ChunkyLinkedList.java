package edu.smith.cs.csc212.p6;

import edu.smith.cs.csc212.p6.errors.BadIndexError;
import edu.smith.cs.csc212.p6.errors.EmptyListError;
import edu.smith.cs.csc212.p6.errors.P6NotImplemented;

/**
 * This is a data structure that has an array inside each node of a Linked List.
 * Therefore, we only make new nodes when they are full. Some remove operations
 * may be easier if you allow "chunks" to be partially filled.
 * 
 * @author jfoley
 * @param <T> - the type of item stored in the list.
 */
public class ChunkyLinkedList<T> implements P6List<T> {
	private int chunkSize;
	private SinglyLinkedList<FixedSizeList<T>> chunks;

	public ChunkyLinkedList(int chunkSize) {
		this.chunkSize = chunkSize;
		// We use chunks before creating it:
		chunks = new SinglyLinkedList<>();
		chunks.addBack(new FixedSizeList<>(chunkSize));
	}

	/**
	 * O(n) - It is easy to find the first fixed size list in our chunky list, 
	 * but it is not easy to shift all of the values within that fixed size list to remove the front
	 */
	@Override
	public T removeFront() {
		return chunks.getFront().removeFront();
	}

	/**
	 * O(n) - It is not easy, since  we must loop through all of the 
	 * fixed size lists first to find the back list. Then, actually removing from that is easy.
	 */
	@Override
	public T removeBack() {
		return chunks.getBack().removeBack();
	}

	/**
	 * O(n)? - Not easy, since we must loop through all of the fixed size lists to find 
	 * the one containing the index. Then, must loop through all of the nodes within
	 * the list to remove.
	 */
	@Override
	public T removeIndex(int index) {
		T removed = null;
		
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
		int start = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			// calculate bounds of this chunk.
			int end = start + chunk.size();
			
			// Check whether the index should be in this chunk:
			if (start <= index && index < end) {
				removed = chunk.getIndex( index - start );
				chunk.removeIndex( index - start );
				//return chunk.addIndex(item, (index - start) );
			}
			
			// update bounds of next chunk.
			start = end;
		}
		if ( removed != null ) {
			return removed;
		} else {
			throw new BadIndexError();
		}
	}

	/**
	 * Sorry I failed to implement this, but I know how it should work in theory:
	 * 
	 * O(n) - If the first fixed size list is not full, you will still have to shift all 
	 * of the inside items over by one. If the first fixed size list IS full, you will have
	 * to shift the items of that fixed size list and all following ones. If the every 
	 * fixed size list is full, you will have to make a new fixed size list and shift all 
	 * of the values over.
	 */
	@Override
	public void addFront(T item) {
		throw new P6NotImplemented();
	}

	/**
	 * O(n) - We must first find the last fixed size list, which requires looping through
	 * every fixed size list. Once the last one is found, it is easier to add a node.
	 * If the last one is full, make a new one and add the node. If not full, just add to back.
	 */
	@Override
	public void addBack(T item) {
		if ( ( chunks.getBack().size() + 1 ) > chunkSize ) {
			chunks.addBack(new FixedSizeList<>(chunkSize));
			
			chunks.getBack().addFront(item);
		} else {
			chunks.getBack().addBack(item);
		}
	}

	/**
	 * Again, could not implement.
	 * 
	 * O(n) - First, loop through every fixed size list to see which one contains the index.
	 * After that one is found, shift every following node down one across fixed size lists. 
	 * Once there is space, add the new item.
	 */
	@Override
	public void addIndex(T item, int index) {
		throw new P6NotImplemented();
	}
	
	/**
	 * O(1) - We have been keeping track of the start of the chunky list, and it is easy
	 * to index the fixed size lists inside
	 */
	@Override
	public T getFront() {
		return this.chunks.getFront().getFront();
	}

	/**
	 * O(n) - We have to loop through all fixed size lists to find the last one, but then 
	 * it is easy to get the back of the fixed size list within
	 */
	@Override
	public T getBack() {
		return this.chunks.getBack().getBack();
	}

	/**
	 * O(n) - We have to loop through all fixed size lists to find the one with the index, 
	 * but then it is easy to get the item from within the fixed size list
	 */
	@Override
	public T getIndex(int index) {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
		int start = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			// calculate bounds of this chunk.
			int end = start + chunk.size();
			
			// Check whether the index should be in this chunk:
			if (start <= index && index < end) {
				return chunk.getIndex(index - start);
			}
			
			// update bounds of next chunk.
			start = end;
		}
		throw new BadIndexError();
	}

	/**
	 * O(n) - Must loop through every fixed size list and add up the contents of each
	 */
	@Override
	public int size() {
		int total = 0;
		for (FixedSizeList<T> chunk : this.chunks) {
			total += chunk.size();
		}
		return total;
	}

	/**
	 * O(n) - Easy, since if there is no start to the list, the list must be empty.
	 */
	@Override
	public boolean isEmpty() {
		return this.chunks.isEmpty();
	}
}
