package de.berlin.fu.inf.pattern.util.data;

import java.util.Arrays;

import de.berlin.fu.inf.pattern.util.types.Dimensionable;
import de.berlin.fu.inf.pattern.util.types.Messurable;

public class IntVector implements Dimensionable<IntVector>, Messurable<IntVector> {
	private final int[] vector;
	private final int dimension;
	
	public IntVector(int dim) {
		this(new int[dim]);
	}
	
	public IntVector(int[] vector) {
		this.vector = vector.clone();
		dimension = vector.length;
	}
	
	public void setVector(int[] vector) {
		assert vector.length == dimension;
		System.arraycopy(vector, 0, this.vector, 0, dimension);
	}
	
	
	public int compareInDimension(IntVector dimObject, int dimension) {
		if( !checkDimension(dimension) ) {
			throw new IllegalArgumentException("dimension " + dimension + " is out of range");
		}
			
		
		return this.getValueInDimension(dimension) - dimObject.getValueInDimension(dimension); 
	}

	/**
	 * @return euklid distance between this and object
	 */
	public double getDistance(IntVector object) {
		double distance = 0.0d;
		assert object.dimension == dimension;
		
		for(int i = 0; i < dimension; i++) {
			double d = getDistanceInDimension(object, i);
			distance += d*d;
		}
		
		return Math.sqrt(distance);
	}

	public double getDistanceInDimension(IntVector dimObject, int dimension) {
		return Math.abs(getValueInDimension(dimension) - dimObject.getValueInDimension(dimension));
	}
	
	/**
	 * 
	 * @param dimension
	 * @return true if dimension is in range otherwise false
	 */
	private boolean checkDimension(int dimension) {
		return dimension >= 0 && dimension < this.dimension;
	}
	
	public int getValueInDimension(int dimension) {
		if( !checkDimension(dimension) )
			throw new IllegalArgumentException("dimension " + dimension + " is out of range");
		
		
		return this.vector[dimension];
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof IntVector){
			IntVector other = (IntVector) obj;
			return Arrays.equals(vector, other.vector);
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(vector);
	}
	
	/**
	 * @return the vector in format of (x1,x2,...,xn)
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("(");
		for(int i = 0; i<dimension; i++) {
			builder.append(vector[i]);
			if(i<dimension-1)
				builder.append(",");
		}
		builder.append(")");
		return builder.toString();
		
	}
}
