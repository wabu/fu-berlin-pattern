package de.berlin.fu.inf.pattern.types;

public class NaturalMessure<M extends Messurable<M>> implements Messure<M> {

	public double getDistance(M a, M b) {
		return a.getDistance(b);
	}

	public Messurable<M> getMessurable(M refPoint) {
		return refPoint;
	}

}
