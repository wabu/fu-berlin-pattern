package de.berlin.fu.inf.pattern.util.types;

/**
 * 
 * @author alex
 */
public interface Dimensionable<D> {

	/**
	 * @param dimObject 
	 * @param dimension from zero up to number of dimensions-1
	 * @return a value smaller then zero, zero or greater then zero if dimObject is in
	 * specified dimension smaller, equal or greater then this object 
	 */
	public int compareInDimension(D dimObject, int dimension);
	
	/**
	 * 
	 * @param dimObject
	 * @param dimension from zero up to number of dimensions-1
	 * @return 
	 */
	public double getDistanceInDimension(D dimObject, int dimension);
	
	/**
	 * 
	 * @param object another dimensionable object
	 * @return the euklid distance to another dimensionable object o
	 */
	public double getDistance(D object);
	
}
