package de.berlin.fu.inf.pattern.util.data;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;
import java.util.Arrays;

import de.berlin.fu.inf.pattern.util.types.Dimensionable;
import de.berlin.fu.inf.pattern.util.types.Messurable;
import de.berlin.fu.inf.pattern.util.types.Vectorable;

public class DoubleVector implements Dimensionable<DoubleVector>, Vectorable, Messurable<DoubleVector> {
	private final double[] data;
	private final int dimension;
	
	public DoubleVector(double ... data) {
		this.data = data;
		dimension = data.length;
	}

    @SuppressWarnings("EI_EXPOSE_REP")
	public double[] getVectorData() {
		return data;
	}

	public int compareInDimension(DoubleVector dimObject, int dimension) {
		
		return this.getValueInDimension(dimension) < dimObject.getValueInDimension(dimension) ? -1 :
			   this.getValueInDimension(dimension) > dimObject.getValueInDimension(dimension) ?  1 :0;
	}

	public double getDistanceInDimension(DoubleVector dimObject, int dimension) {
		return Math.abs(getValueInDimension(dimension) - dimObject.getValueInDimension(dimension));
	}
	
	public double getValueInDimension(int dimension) {
		if( !checkDimension(dimension) )
			throw new IllegalArgumentException("dimension " + dimension + " is out of range");
		
		
		return this.data[dimension];
	}

	/**
	 * @return euklid distance between this and object
	 */
	public double getDistance(DoubleVector object) {
		double distance = 0.0d;
		assert object.dimension == dimension;
		
		for(int i = 0; i < dimension; i++) {
			double d = getDistanceInDimension(object, i);
			distance += d*d;
		}
		return Math.sqrt(distance);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DoubleVector){
			DoubleVector other = (DoubleVector) obj;
			return Arrays.equals(data, other.data);
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(data);
	}
	
	/**
	 * 
	 * @param dimension
	 * @return true if dimension is in range otherwise false
	 */
	private boolean checkDimension(int dimension) {
		return dimension >= 0 && dimension < this.dimension;
	}

    public int getDimension() {
        return data.length;
    }
	
}
