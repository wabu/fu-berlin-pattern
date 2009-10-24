package de.berlin.fu.inf.pattern.data.kdtree;

import edu.umd.cs.findbugs.annotations.CheckForNull;

/**
 * 
 * @author alex
 *
 * @param <E> datatype of entry
 */
public class Node<E extends Dimensionable<E>> {
	private E content;
	@CheckForNull
	private Node<E> leftNode, rightNode, parentNode;
	
	public Node(E content) {
		this.content = content;
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
	
	@CheckForNull
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
	
	@CheckForNull
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
	@CheckForNull
	public Node<E> getParentNode() {
		return parentNode;
	}
	
	public void setParentNode(Node<E> parentNode) {
		this.parentNode = parentNode;
	}
}
