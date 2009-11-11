package de.berlin.fu.inf.pattern.util.data;

import de.berlin.fu.inf.pattern.util.types.Vectorable;

public class DoubleVecor implements Vectorable {
	private final double[] data;
	
	public DoubleVecor(double ... data) {
		this.data = data;
	}

	public double[] getVectorData() {
		return data;
	}
}
