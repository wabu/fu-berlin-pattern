package de.berlin.fu.inf.algorithm;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;
import java.util.Comparator;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * This class provides a method to the k-th smalles element in an unordered array
 * 
 * @author alex
 *
 * @param <E> Objecttype to sort
 */
public class SelectionSort<E> {
	private final Logger logger = Logger.getLogger(SelectionSort.class);
	

	// util field to count the number of comparation for finding k-th element
	private int comparisons;
	private Random rand = new Random();

	private Comparator<? super E> comparator;
	private E[] objects = null;
	private E[] sortBuffer = null;
	
	public SelectionSort() {}	
	
	/**
	 * @param k
	 * @param objects
	 * @param comparator
	 * @return index of k-th element
	 */
	public int find(int k, E[] objects, Comparator<E> comparator) {
		
		return find(k, objects, 0, objects.length-1, comparator);
	}
	
    @SuppressWarnings("EI_EXPOSE_REP2")
	public int find(int k, E[] objects, int indexFrom, int indexTo, Comparator<? super E> comparator) {
		if( logger.isTraceEnabled()) {
            logger.trace("find(): k=" + k + " in array from " + indexFrom + "->" +
                    indexTo);
        }
		
		this.objects = objects;		
		this.sortBuffer = this.objects.clone();
		this.comparator = comparator;
		
		return divideAndSelect(indexFrom, indexTo, k);
	}
	
	/**
	 * adapted quicksort algorithm to detect k-th smallest element
	 * 
	 * @param indexFrom
	 * @param indexTo
	 * @param k
	 * @return
	 */
	private int divideAndSelect(final int indexFrom, final int indexTo, int k ) {
		if( logger.isTraceEnabled() )
			logger.trace("divideAndSelct " + indexFrom + "," + indexTo + "," + k);
		// avoid IndexOutOfBoundException
		if( indexFrom > indexTo ) {
			throw new IllegalArgumentException("bounding indices passed each other: from="+indexFrom + " -> to="+indexTo );
		}
		
		
		if( indexFrom == indexTo ) {
			return indexFrom;
		}
		
		// choose pivot element
		int pivotIndex = indexFrom + rand.nextInt(indexTo-indexFrom+1);
		logger.trace("choose pivotIndex " + pivotIndex);
		E pivotElement = this.objects[pivotIndex];
		

		
		/** switch all elements greater then pivotElement from left to
         *  right block
         */
        int smallerValues=0;
        int higherValues=0;
        for( int i = indexFrom; i <= indexTo; i++) {
            this.comparisons++;
            // leave pivot out
            if(i==pivotIndex) continue;

            if(comparator.compare(objects[i], pivotElement) <= 0) {
                this.sortBuffer[indexFrom+smallerValues] = objects[i];
                smallerValues++;
            } else {
                this.sortBuffer[indexTo-higherValues] = objects[i];
                higherValues++;
            }
        }
        //finally place the pivotElement
        pivotIndex = indexFrom+smallerValues;
        this.sortBuffer[pivotIndex] = pivotElement;
        
        //copy temporary back:
        for(int i =indexFrom; i <= indexTo; i++) {
            objects[i] = this.sortBuffer[i];
        }
        
        // get current element position
        

        if(k == pivotIndex+1) {
        	return pivotIndex;
        } else if( k < pivotIndex + 1) {
            return this.divideAndSelect(indexFrom, pivotIndex-1, k);
        } else {
            return this.divideAndSelect(pivotIndex+1, indexTo,k);
        }
	}
}
