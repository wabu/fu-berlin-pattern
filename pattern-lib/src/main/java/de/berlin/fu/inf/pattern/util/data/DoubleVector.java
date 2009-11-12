package de.berlin.fu.inf.pattern.util.data;

import de.berlin.fu.inf.pattern.util.types.Vectorable;

public class DoubleVector implements Vectorable {
	private final double[] data;
	
	public DoubleVector(double ... data) {
		this.data = data;
	}

	public double[] getVectorData() {
		return data;
	}
}
