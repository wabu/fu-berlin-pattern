package de.berlin.fu.inf.pattern.impl.kdtree;

import de.berlin.fu.inf.pattern.util.types.Dimensionable;

public interface KDTree<V extends Dimensionable<V>> {

	
	public void buildTree(V[] values);
	
	/** 
	 * @param value
	 * @return an array of size k with most nearest values in this tree
	 */
	public V findNearestValues(V value);
}
