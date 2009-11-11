package de.berlin.fu.inf.pattern.util.jama;

import de.berlin.fu.inf.pattern.util.types.Vectorable;
import Jama.Matrix;

public class Vec extends Matrix {
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
	
	@Override
	public Vec minus(Matrix arg0) {
		if(arg0.getColumnDimension() != 1){
			throw new IllegalArgumentException("vecor has to be an 1xn matrix");
		}
		return new Vec(super.minus(arg0));
	}
	
	@Override
	public String toString() {
		return MatrixString.ms(this);
	}
}
