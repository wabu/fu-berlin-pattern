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
	

	public V findKnearestValues(V value) {
		DimensionComparator<V> dim = new DimensionComparator<V>(dimensions);
		return findnearest(root,dim, value, Double.POSITIVE_INFINITY, null).getContent();
	}
	
	private Node<V> findnearest(Node<V> node, DimensionComparator<V> dim, V value, double best, Node<V> b) {
		logger.debug("findnearest("+value+", "+node.getContent()+")");
		Node<V> leaf = findLeafNode(value, node, dim);
		Node<V> nearest = unwind(leaf, dim, value, best, b);
		logger.debug("< "+nearest.getContent());
		return nearest;
	}
	
	private Node<V> unwind(Node<V> node, DimensionComparator<V> dim, V value, double bestDist, Node<V> b) {
		Node<V> parent = node.getParentNode();
		V content = node.getContent();
		
		logger.debug("unwind("+content+")");
		
		double dist = content.getDistance(value);
		if(dist < bestDist){
			b = node;
			bestDist = dist;
		}
		
		double dimDist = content.getDistanceInDimension(value, dim.getCurrentDimension());
		if(dimDist <= bestDist) {
			if(parent == null) {
				return b;
			} else {
				return unwind(parent, dim.previousDimension(), value, bestDist, b);
			}
		}
		
		Node<V> other = selectOtherChild(value, node, dim);
		if(other == null ||
				content.compareInDimension(other.getContent(), dim.getCurrentDimension()) ==
				content.compareInDimension(value, dim.getCurrentDimension()) ) {
			if(parent == null) {
				return b;
			} else {
				return unwind(parent, dim.previousDimension(), value, bestDist, b);
			}
		}
		
		return findnearest(other, dim.nextDimension(), value, bestDist, b);
	}
	
	public V findLeaf(V value){
		return findLeafNode(value, root, new DimensionComparator<V>(dimensions)).getContent();
	}
	
	private Node<V> findLeafNode(V value, Node<V> root, DimensionComparator<V> dim) {
		Node<V> next=root, node;
		do {
			node = next;
			next = selectChild(value, node, dim);
			if(next != null){
				dim.nextDimension();
			}
		} while(next != null);
		
		return node;
	}
	
	@CheckForNull
	private Node<V> selectChild(V value, Node<V> node, DimensionComparator<V> dim){
		int c = dim.compare(value, node.getContent());
		if(c==0){
			return null;
		} else if (c<0){
			return node.getLeftNode();
		} else {
			return node.getRightNode();
		}
	}
	
	@CheckForNull
	private Node<V> selectOtherChild(V value, Node<V> node, DimensionComparator<V> dim){
		int c = dim.compare(value, node.getContent());
		assert c != 0;
		if (c>0){
			return node.getLeftNode();
		} else {
			return node.getRightNode();
		}
	}
	
	Node<V> getRootNode() {
		return this.root;
	}
}
