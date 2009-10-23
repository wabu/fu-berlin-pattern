package de.berlin.fu.inf.algorithm;

import static org.junit.Assert.*;

import java.util.Comparator;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SelectionSortTest {
	private SelectionSort<Integer> selSort;

	private Integer[] numbers;
	
	private Comparator<Integer> comparator = new Comparator<Integer>() {
		public int compare(Integer o1, Integer o2) {
			return o1.compareTo(o2);
		}
	};
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@Before
	public void setUp() throws Exception {
		selSort = new SelectionSort<Integer>();
		// 10 elements correct order: 1,1,2,3,3,5,5,7,7,9
		numbers = new Integer[]{1,5,3,5,2,7,1,3,7,9};

	}

	@Test
	public void testFindIntEArrayComparatorOfE() {
		int index;
		
		index = selSort.find(2, numbers, comparator);
		assertTrue(numbers[index]==1);
		
		index = selSort.find(6, numbers, comparator);
		assertTrue("value is " + numbers[index], numbers[index]==5);
		
		index = selSort.find(10, numbers, comparator);
		assertTrue(numbers[index]==9);
		
	}

}
