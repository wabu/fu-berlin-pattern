package de.berlin.fu.inf.pattern.util.mesure;

import de.berlin.fu.inf.pattern.util.types.Messurable;

public class NaturalMessure<M extends Messurable<M>> implements Messure<M> {

	public double getDistance(M a, M b) {
		return a.getDistance(b);
	}

	public Messurable<M> getMessurable(M refPoint) {
		return refPoint;
	}

}
