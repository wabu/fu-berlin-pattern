package de.berlin.fu.inf.pattern.data.kdtree;

/**
 * 
 * @author alex
 */
public interface Dimensionable<D> {

	
	/**
	 * @param dimObject 
	 * @param dimension
	 * @return a value smaller zero, zero or greater then zero if dimObject is in
	 * specified dimension smaller, equal or greater then this object 
	 */
	public int compareInDimension(D dimObject, int dimension);
	
	/**
	 * 
	 * @param dimObject
	 * @param dimension
	 * @return 
	 */
	public double getDistanceInDimension(D dimObject, int dimension);
	
	/**
	 * 
	 * @param o another dimensionable object o
	 * @return the euklid distance to another dimensionable object o
	 */
	public double getDistance(D o);
	
}
