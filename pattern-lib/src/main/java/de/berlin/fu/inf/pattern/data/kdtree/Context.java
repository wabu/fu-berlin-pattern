package de.berlin.fu.inf.pattern.data.kdtree;

import javax.annotation.CheckForNull;

class Context<V extends Dimensionable<V>> extends DimensionComparator<V> {
	final V value;
	
	Node<V> node;
	Node<V> best;
	double dist;
	
	public Context(Node<V> root, V value, int dimensions) {
		super(dimensions);
		dist = Double.POSITIVE_INFINITY;
		node = root;
		this.value = value;
		
		updateBest();
	}
	
	public Node<V> getNode() {
		return node;
	}
	
	public void setNode(Node<V> node) {
		this.node = node;
	}
	
	V getContent() {
		return node.getContent();
	}
	
	boolean hasParent(){
		return node.getParentNode() != null;
	}
	
	Context<V> traverseUp() {
		super.previousDimension();
		Node<V> parent = node.getParentNode();
		assert parent != null;
		node = parent;
		return this;
	}
	
	int selectDir(){
		return super.compare(value, node.getContent());
	}
	int selectReverse(){
		return -super.compare(value, node.getContent());
	}
	
	
	@CheckForNull
	Node<V> selectChild(int dir) {
		if(dir == 0) {
			return null;
		} else if (dir < 0){
			return node.getLeftNode();
		} else {
			return node.getRightNode();
		}
	}
	
	Context<V> traverseTo(int dir) {
		Node<V> child;
		if(dir == 0) {
			return this;
		} else {
			child = selectChild(dir);
		} 
		assert child != null;
		return traverseTo(child);
	}
	
	Context<V> traverseTo(Node<V> child){
		super.nextDimension();
		node = child;
		updateBest();
		return this;
	}
	
	public double getBestDistance() {
		return dist;
	}
	public Node<V> getBestNode() {
		return best;
	}
	public V getBestValue() {
		return best.getContent();
	}
	
	void updateBest() {
		double dist = value.getDistance(node.getContent());
		if(dist < this.dist){
			setBest(node);
		}
	}
	public void setBest(Node<V> best) {
		this.dist = value.getDistance(best.getContent());
		this.best = best;
	}
	
	public V getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return getValue()+": "+getNode()+"|"+getCurrentDimension() + " ("+dist+")";
	}
}
