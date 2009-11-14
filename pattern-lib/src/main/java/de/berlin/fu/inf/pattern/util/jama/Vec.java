package de.berlin.fu.inf.pattern.util.jama;

import net.jcip.annotations.Immutable;
import de.berlin.fu.inf.pattern.util.types.Vectorable;
import Jama.Matrix;

public class Vec extends Matrix implements Vectorable {
	private static final long serialVersionUID = -2349032575947575606L;

	public Vec(double[] data) {
		super(data, data.length);
	}

	public Vec(Vectorable entry) {
		this(entry.getVectorData());
	}
	
	public Vec(Matrix m) {
		super(m.getArray());
	}
	
	/**
	 * creates zero vector of dimension n 
	 * @param n - rows
	 */
	public Vec(int n) {
		// n rows and one column filled with zero
		super(n,1);
	}
	
	@Override
	public Vec minus(Matrix arg0) {
		if(arg0.getColumnDimension() != 1){
			throw new IllegalArgumentException("vecor has to be an 1xn matrix");
		}
		return new Vec(super.minus(arg0));
	}
	
	@Override
	public Vec plus(Matrix arg0) {
		if(arg0.getColumnDimension() != 1){
			throw new IllegalArgumentException("vecor has to be an 1xn matrix");
		}
		return new Vec(super.minus(arg0));
	}
	
	
	@Override
	public String toString() {
		return MatrixString.ms(this);
	}

	// interface vectorable 
	public double[] getVectorData() {
		return super.getColumnPackedCopy();
	}
	
	
}
