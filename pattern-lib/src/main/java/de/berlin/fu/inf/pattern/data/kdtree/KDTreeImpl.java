package de.berlin.fu.inf.pattern.data.kdtree;

import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * 
 * @author alex
 *
 */
public class KDTreeImpl<V extends Dimensionable> implements KDTree<V>{
	private final Logger logger = Logger.getLogger(KDTreeImpl.class);

	private Node<V> root = null;
	private V[] tmpValues = null;
	private boolean training = false;
	
	public void buildTree(V[] values) {
		logger.trace("build new KD-Tree with " + values.length + " objects");
		
		this.
		tmpValues = values;
		
		root = determineNode(0, values.length, 0);
		
	}
	
	
	private Node<V> determineNode(int indexFrom, int indexTo, int currentDimension){
		Node<V> newNode = null;
		
		
		
		// retrieve median between two indices indexFrom and indexTo
		// newNode = median(V[]values, indexFrom, indexTo 
		
		return null;
	}

	public V[] findKnearestValues(int k, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
