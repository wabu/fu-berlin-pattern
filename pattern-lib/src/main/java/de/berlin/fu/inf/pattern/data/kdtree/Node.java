package de.berlin.fu.inf.pattern.data.kdtree;

/**
 * 
 * @author alex
 *
 * @param <E> datatype of entry
 */
public class Node<E extends Dimensionable<E>> {
	private E content;
	private Node<E> leftNode, rightNode, parentNode;
	
	public Node() {
		this.content = null;
		leftNode = null;
		rightNode = null;
		parentNode = null;
	}
	
	public E getContent() {
		return content;
	}
	public void setContent(E content) {
		this.content = content;
	}
	public Node<E> getLeftNode() {
		return leftNode;
	}
	
	/**
	 * sets leftNode as left node of current node. Implicit leftNode's parent is set to this one
	 * @param leftNode
	 */
	public void setLeftNode(Node<E> leftNode) {
		this.leftNode = leftNode;
	}
	public Node<E> getRightNode() {
		return rightNode;
	}
	
	/**
	 * sets rigthNode as right node of current node. Implicit rightNode's parent is set to this one 
	 * 
	 * @param rightNode
	 */
	public void setRightNode(Node<E> rightNode) {
		this.rightNode = rightNode;
		
		rightNode.setParentNode(this);
	}
	public Node<E> getParentNode() {
		return parentNode;
	}
	public void setParentNode(Node<E> parentNode) {
		this.parentNode = parentNode;
	}
	
	
	
	
	
}
