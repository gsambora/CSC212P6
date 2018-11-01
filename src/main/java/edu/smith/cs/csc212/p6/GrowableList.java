package edu.smith.cs.csc212.p6;

import edu.smith.cs.csc212.p6.errors.EmptyListError;
import edu.smith.cs.csc212.p6.errors.P6NotImplemented;

public class GrowableList<T> implements P6List<T> {
	public static final int START_SIZE = 32;
	private Object[] array;
	private int fill;
	
	public GrowableList() {
		this.array = new Object[START_SIZE];
		this.fill = 0;
	}

	/**
	 * O(n) - Everything in the list after the front has to be shifted back
	 */
	@Override
	public T removeFront() {
		return removeIndex(0);
	}

	/**
	 * O(1) - Nothing has to be moved to remove from the back
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
	 * O(n) - Everything in the list after the indexed item has to be shifted back
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
	 * O(n) - Everything has to be shifted down one in order to give room for the new front
	 */
	@Override
	public void addFront(T item) {
		addIndex(item, 0);
	}

	/**
	 * O(1) - When the list isn't full, it is easy to just add something to the back
	 * O(n) - If the list is full and we need a bigger list, everything will need to be copied
	 */
	@Override
	public void addBack(T item) {
		// I've implemented part of this for you.
		if (fill >= this.array.length) { 
			int newSize = fill * 2;
			Object[] newArray = new Object[newSize];
			for ( int i = 0; i < array.length; i++ ) {
				newArray[i] = array[i];
			}
			this.array = newArray;
		}
		
		this.array[fill] = item;
		fill++;
	}

	/**
	 * O(n) - Everything after the index will have to be shifted down to make room
	 */
	@Override
	public void addIndex(T item, int index) {
		// loop backwards, shifting items to the right.
		for (int j=fill; j>index; j--) {
			array[j] = array[j-1];
		}
		array[index] = item;
		fill++;		
	}
	
	/** 
	 * O(1) - Indexing is easy, so finding the front is also easy
	 */
	@Override
	public T getFront() {
		return this.getIndex(0);
	}

	/** 
	 * O(1) - Indexing is easy, so finding the back is also easy
	 */
	@Override
	public T getBack() {
		return this.getIndex(this.fill-1);
	}

	/** 
	 * O(1) - Indexing is easy here 
	 */
	@Override
	public T getIndex(int index) {
		return (T) this.array[index];
	}

	/**
	 * O(1) - We've been keeping track of fill the whole time so it's easy to grab
	 */
	@Override
	public int size() {
		return fill;
	}

	/**
	 * O(1) - Again, we've been keeping track of fill the whole time so 
	 * it's easy to see when it's zero
	 */
	@Override
	public boolean isEmpty() {
		return fill == 0;
	}
	
	


}
