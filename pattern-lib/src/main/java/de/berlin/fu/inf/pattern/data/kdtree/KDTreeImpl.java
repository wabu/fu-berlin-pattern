package de.berlin.fu.inf.pattern.data.kdtree;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;

import org.apache.log4j.Logger;

import de.berlin.fu.inf.algorithm.SelectionSort;

/**
 * 
 * @author alex
 *
 */
public class KDTreeImpl<V extends Dimensionable<V>> implements KDTree<V>{
	public static final int DEFAULT_DIMENSIONS = 2;
	private final Logger logger = Logger.getLogger(KDTreeImpl.class);

	private final int dimensions;
	
	private Node<V> root;
	private V[] tmpValues;
	
	// FIXME: shoud be used locally ... 
	private final SelectionSort<V> selectionSort = new SelectionSort<V>();
	private final DimensionComparator<V> dimComp;

	/**
	 * creates an new KDTree with <code>DEFAULT_DIMENSIONS</code>
	 */
	public KDTreeImpl() {
		this(DEFAULT_DIMENSIONS);
	}
	
	/**
	 * @param dimensions represents the number of dimensions
	 */
	public KDTreeImpl(int dimensions) {
		this.dimensions = dimensions;
		this.dimComp = new DimensionComparator<V>(dimensions);
	}
	
	
	public void buildTree(V[] values) {
		logger.info("build new KD-Tree of " + values.length + " objects");
		
		tmpValues = values;
		
		this.root = determineMedianNode(0, values.length-1, 0);
		
	}
	
	/**
	 * This method provides the median in an given range according 
	 * to a dimension represented as node object
	 * 
	 * @param indexFrom
	 * @param indexTo
	 * @param currentDimension
	 * @return Node or null if there is an error in specified index range 
	 */
	@Nullable
	protected Node<V> determineMedianNode(int indexFrom, int indexTo, int currentDimension){
		if( logger.isDebugEnabled() )
			logger.debug("determineMedianNode("+indexFrom+","+indexTo+","+currentDimension +")");
		
		// if range size is one, there is no median element to be searched for
		if( indexFrom == indexTo ) {
			return new Node<V>(tmpValues[indexFrom]);
		}
		
		dimComp.setCurrentDimension(currentDimension);
		
		// retrieve median between two indices indexFrom and indexTo
		int medianIndex = selectionSort.find(
				getMedianBetween(indexFrom, indexTo)+1, 	// the median in this rang as k-th smallest element
				tmpValues, 								// the complete array
				indexFrom, 								// search in array from
				indexTo, 								// search in array to
				this.dimComp);							// comparator
		
		
		
		if(medianIndex < indexFrom || medianIndex > indexTo) {
			logger.warn("retrieved index is out of range, return NULL");
			return null;
		}
		logger.debug("medianIndex="+medianIndex + " -> " + tmpValues[medianIndex]);
		
		Node<V> newNode = new Node<V>(tmpValues[medianIndex]);
		newNode.setContent(tmpValues[medianIndex]);
		
		if( indexFrom < medianIndex ) {
			newNode.setLeftNode(
				determineMedianNode(indexFrom, medianIndex-1, (currentDimension+1)%this.dimensions));
		}
		if( indexTo > medianIndex ) {
			newNode.setRightNode(
				determineMedianNode(medianIndex+1, indexTo, (currentDimension+1)%this.dimensions));
		}
		
		return newNode;
	}
	
	/**
	 * 
	 * @param from
	 * @param to
	 * @return an value representing the median in the range 
	 * from <code>from</code> to <code>to</code> or -1 if there is not at least one element
	 */
	protected int getMedianBetween(int from, int to) {
		if(to < from ) return -1;

		return Math.round((from+to)/2.0f);
	}
	

	public V findNearestValues(V value) {
		DistValueContext<V> context = new DistValueContext<V>(root, value, dimensions);
		return findnearest(context).getContent();
	}
	
	private Node<V> findnearest(DistValueContext<V> context) {
		logger.trace(context.indent()+"find nearest"+context);
		
		findLeafNode(context);
		unwind(context);
		logger.trace(context.indent()+"< "+context.getBestNode());
		return context.getBestNode();
	}
	
	private Node<V> unwind(DistValueContext<V> context) {
		logger.trace(context.indent() + "unwind("+context+")");
		
		if(context.getDistenceInCurrentDimension() <= context.getBestDistance()) {
			if(context.hasParent()) {
				return unwind(context.traverseUp());
			} else {
				return context.getBestNode();
			}
		}
	
		Node<V> other = context.selectChild(context.selectReverse());
		if(other == null || context.isSamePlaneAsValue(other)) {
			if(context.hasParent()) {
				return unwind(context.traverseUp());
			} else {
				return context.getBestNode();
			}
		}
		
		return findnearest(context.traverseTo(other));
	}
	
	public V findLeaf(V value){
		return findLeafNode(new DistValueContext<V>(root, value, dimensions)).getContent();
	}
	
	private Node<V> findLeafNode(DistValueContext<V> context) {
		Node<V> next;
		next = context.selectChild(context.selectDir());
		while(next != null) {
			context.traverseTo(next);
			next = context.selectChild(context.selectDir());
		}
		return context.getNode();
>>>>>>> 52197752c5f8891dc68b098486637762b9d6caba:pattern-lib/src/main/java/de/berlin/fu/inf/pattern/data/kdtree/KDTreeImpl.java
	}
	
	Node<V> getRootNode() {
		return this.root;
	}
}
