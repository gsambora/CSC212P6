package edu.smith.cs.csc212.p6;

import edu.smith.cs.csc212.p6.errors.BadIndexError;
import edu.smith.cs.csc212.p6.errors.EmptyListError;
import edu.smith.cs.csc212.p6.errors.RanOutOfSpaceError;

public class FixedSizeList<T> implements P6List<T> {
	private Object[] array;
	private int fill;
	
	public FixedSizeList(int maximumSize) {
		this.array = new Object[maximumSize];
		this.fill = 0;
	}
	
	/**
	 * Remove front is O(n) because after removing the first item, everything 
	 * has to move back 1 place
	 */
	@Override
	public T removeFront() {
		return removeIndex(0);
	}

	/**
	 * Remove back is O(1) because you can just pick off the end without moving anything else
	 */
	@Override
	public T removeBack() {
		if (this.size() == 0) {
			throw new EmptyListError();
		}
		
		T value = this.getIndex(fill-1);
		this.array[fill-1] = null;
		fill--;
		
		return value;
	}

	/**
	 * Remove index is O(n) because after removing the indexed item, everything after
	 * has to move back 1 place
	 */
	@Override
	public T removeIndex(int index) {
		if (this.size() == 0) {
			throw new EmptyListError();
		}
		T removed = this.getIndex(index);
		fill--;
		for (int i=index; i<fill; i++) {
			this.array[i] = this.array[i+1];
		}
		this.array[fill] = null;
		return removed;
	}

	/**
	 * O(n) - In order to add to the front, everything after has to shift down one place.
	 */
	@Override
	public void addFront(T item) {
		addIndex(item, 0);		
	}

	/**
	 * O(1) - Nothing has to move to add to the back
	 */
	@Override
	public void addBack(T item) {
		if (fill < array.length) {
			array[fill++] = item;
		} else {
			throw new RanOutOfSpaceError();
		}
	}

	/**
	 * O(n) - Everything after the desired index has to shift down one place to give 
	 * room for the new value
	 */
	@Override
	public void addIndex(T item, int index) {
		if (fill >= array.length) {
			throw new RanOutOfSpaceError();
		}
		// loop backwards, shifting items to the right.
		for (int j=fill; j>index; j--) {
			array[j] = array[j-1];
		}
		array[index] = item;
		fill++;		
	}

	/**
	 * O(1) - Indexing the array is quick since the array gives each value an index anyway
	 */
	@Override
	public T getIndex(int index) {
		if (index < 0 || index >= fill) {
			throw new BadIndexError();
		}
		return (T) this.array[index];
	}
	
	/**
	 * O(1) - We've been keeping track of the value of fill the whole time 
	 * so it's easy to retrieve
	 */
	@Override
	public int size() {
		return this.fill;
	}

	/**
	 * O(1) - Again, it is super easy to see the value of fill 
	 */
	@Override
	public boolean isEmpty() {
		return this.fill == 0;
	}
	
	
	/**
	 * O(1) - Indexing the array is quick since the array gives each value an index anyway
	 */
	@Override
	public T getFront() {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
		return this.getIndex(0);
	}
	
	/**
	 * O(1) - Indexing the array is quick since the array gives each value an index anyway
	 */
	@Override
	public T getBack() {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
		return this.getIndex(this.size()-1);
	}
}
