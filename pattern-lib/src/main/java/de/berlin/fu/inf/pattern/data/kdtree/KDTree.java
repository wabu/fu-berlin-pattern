package de.berlin.fu.inf.pattern.data.kdtree;

public interface KDTree<V extends Dimensionable<V>> {

	
	public void buildTree(V[] values);
	
	/** 
	 * @param k 
	 * @param value
	 * @return an array of size k with most nearest values in this tree
	 */
	public V[] findKnearestValues(int k, V value);
	
}