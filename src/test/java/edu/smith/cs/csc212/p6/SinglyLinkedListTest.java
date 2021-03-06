package edu.smith.cs.csc212.p6;

import org.junit.Assert;
import org.junit.Test;

import edu.smith.cs.csc212.p6.errors.EmptyListError;

public class SinglyLinkedListTest {
	
	/**
	 * Helper method to make a full list.
	 * @return
	 */
	public P6List<String> makeFullList() {
		P6List<String> data = new SinglyLinkedList<String>();
		data.addFront("d");
		data.addFront("c");
		data.addFront("b");
		data.addFront("a");
		return data;
	}
	@Test
	public void testIsEmpty() {
		P6List<String>data = new SinglyLinkedList<String>();
		Assert.assertEquals(true, data.isEmpty());
		
		P6List<String>data1 = makeFullList();
		Assert.assertEquals(false, data1.isEmpty());
	}
	
	@Test
	public void testRemoveFront() {
		P6List<String> data = makeFullList();
		Assert.assertEquals(4, data.size());
		Assert.assertEquals("a", data.removeFront());
		Assert.assertEquals(3, data.size());
		Assert.assertEquals("b", data.removeFront());
		Assert.assertEquals(2, data.size());
		Assert.assertEquals("c", data.removeFront());
		Assert.assertEquals(1, data.size());
		Assert.assertEquals("d", data.removeFront());
		Assert.assertEquals(0, data.size());
	}
	
	@Test( expected = EmptyListError.class )
	public void testRemoveBack() {
		P6List<String> data = makeFullList();
		Assert.assertEquals(4, data.size());
		Assert.assertEquals("d", data.removeBack());
		Assert.assertEquals(3, data.size());
		Assert.assertEquals("c", data.removeBack());
		Assert.assertEquals(2, data.size());
		Assert.assertEquals("b", data.removeBack());
		Assert.assertEquals(1, data.size());
		Assert.assertEquals("a", data.removeBack());
		Assert.assertEquals(0, data.size());
		
		P6List<String> data1 = new SinglyLinkedList<String>();
		data1.removeBack();
	}

	@Test( expected = EmptyListError.class )
	public void testRemoveIndex() {
		P6List<String> data = makeFullList();
		Assert.assertEquals(4, data.size());
		Assert.assertEquals("c", data.removeIndex(2));
		Assert.assertEquals(3, data.size());
		Assert.assertEquals("d", data.removeIndex(2));
		Assert.assertEquals(2, data.size());
		Assert.assertEquals("b", data.removeIndex(1));
		Assert.assertEquals(1, data.size());
		Assert.assertEquals("a", data.removeIndex(0));
		Assert.assertEquals(0, data.size());
		
		P6List<String> data1 = new SinglyLinkedList<String>();
		data1.removeIndex(2);
	}
	
	@Test
	public void testGetIndex() {
		P6List<String> data = new SinglyLinkedList<String>();
		data.addFront("list");
		data.addFront("the");
		data.addFront("test");

		Assert.assertEquals("test", data.getIndex(0));
		Assert.assertEquals("the", data.getIndex(1));
		Assert.assertEquals("list", data.getIndex(2));
	}
	
	@Test
	public void testGetBack() {
		P6List<String>data = makeFullList();
		Assert.assertEquals("d", data.getBack());
	}
	
	@Test
	public void testGetFront() {
		P6List<String>data = makeFullList();
		Assert.assertEquals("a", data.getFront());
	}
	
	@Test
	public void testAddToFront() {
		P6List<String> data = new SinglyLinkedList<String>();
		data.addFront("1");
		Assert.assertEquals(1, data.size());
		Assert.assertEquals("1", data.getIndex(0));
		data.addFront("0");
		Assert.assertEquals(2, data.size());
		Assert.assertEquals("0", data.getIndex(0));
		Assert.assertEquals("1", data.getIndex(1));
		data.addFront("-1");
		Assert.assertEquals(3, data.size());
		Assert.assertEquals("-1", data.getIndex(0));
		Assert.assertEquals("0", data.getIndex(1));
		Assert.assertEquals("1", data.getIndex(2));
		data.addFront("-2");
		Assert.assertEquals("-2", data.getIndex(0));
		Assert.assertEquals("-1", data.getIndex(1));
		Assert.assertEquals("0", data.getIndex(2));
		Assert.assertEquals("1", data.getIndex(3));
	}

	@Test
	public void testAddToBack() {
		P6List<String> data = makeFullList();
		data.addBack("e");
		Assert.assertEquals("e", data.getBack());
		
		
	}
	
	@Test
	public void testAddToIndex() {
		P6List<String> data = makeFullList();
		data.addIndex("15",3);
		Assert.assertEquals(5, data.size());
		Assert.assertEquals("15", data.getIndex(3));
		Assert.assertEquals("d", data.getIndex(4));
	}
	
	@Test
	public void testSize() {
		P6List<String>data = makeFullList();
		Assert.assertEquals(4, data.size());
	}
}
