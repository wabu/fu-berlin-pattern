package de.berlin.fu.inf.pattern.u03;

import de.berlin.fu.inf.pattern.util.types.Messurable;

public class DoublePoint implements Messurable<DoublePoint> {
	private final double value;
	public DoublePoint(double value) {
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}

	public double getDistance(DoublePoint other) {
		return Math.abs(value - other.value);
	}
	
	@Override
	public String toString() {
		return Double.toString(value);
	}
}
