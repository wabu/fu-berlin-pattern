package de.berlin.fu.inf.pattern.data.kdtree;

public class DimensionableImpl implements Dimensionable<DimensionableImpl> {
	public static int DIMENSIONS = 3;
	
	private int[] vector;
	
	public DimensionableImpl() {
		this(new int[]{0,0,0});
	}
	
	public DimensionableImpl(int[] vector) {
		this.setVector(vector);
	}
	
	public void setVector(int[] vector) {
		if( vector != null && vector.length == DIMENSIONS) {
			this.vector = vector;
		}
	}
	
	
	public int compareInDimension(DimensionableImpl dimObject, int dimension) {
		if( !checkDimension(dimension) ) {
			throw new IllegalArgumentException("dimension " + dimension + " is out of range");
		}
			
		
		return this.getValueInDimension(dimension) - dimObject.getValueInDimension(dimension); 
	}

	/**
	 * @return euklid distance between this and object
	 */
	public double getDistance(DimensionableImpl object) {
		
		double distance = 0.0d;
		
		for(int i = 0; i < this.DIMENSIONS; i++) {
			distance += Math.pow(getDistanceInDimension(object, i), 2);
		}
		
		return Math.sqrt(distance);
	}

	public double getDistanceInDimension(DimensionableImpl dimObject, int dimension) {
		
		return Math.abs(getValueInDimension(dimension) - dimObject.getValueInDimension(dimension));
	}
	
	/**
	 * 
	 * @param dimension
	 * @return true if dimension is in range otherwise false
	 */
	private boolean checkDimension(int dimension) {
		return dimension >= 0 && dimension < DIMENSIONS;
	}
	
	public int getValueInDimension(int dimension) {
		if( !checkDimension(dimension) )
			throw new IllegalArgumentException("dimension " + dimension + " is out of range");
		
		
		return this.vector[dimension];
	}
	
	/**
	 * @return the vector in format of (x1,x2,...,xn)
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("(");
		for(int i = 0; i<DIMENSIONS; i++) {
			builder.append(vector[i]);
			if(i<DIMENSIONS-1)
				builder.append(",");
		}
		builder.append(")");
		return builder.toString();
		
	}

}
