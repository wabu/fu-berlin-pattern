package de.berlin.fu.inf.algorithm;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;
import static org.junit.Assert.*;

import java.util.Comparator;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SelectionSortTest {
    @SuppressWarnings("UWF_FIELD_NOT_INITIALIZED_IN_CONSTRUCTOR")
	private SelectionSort<Integer> selSort;

    @SuppressWarnings("UWF_FIELD_NOT_INITIALIZED_IN_CONSTRUCTOR")
	private Integer[] numbers;
	
	private Comparator<Integer> comparator = new IntComparator();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@Before
	public void setUp() throws Exception {
		selSort = new SelectionSort<Integer>();
		// 10 elements correct order: 0,1,2,3,4,5,6,7,8,9
		numbers = new Integer[]{0,2,3,1,5,4,6,8,7,9};

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

    private static class IntComparator implements Comparator<Integer> {
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }
    }

}
