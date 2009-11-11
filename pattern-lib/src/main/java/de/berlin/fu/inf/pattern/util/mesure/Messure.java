package de.berlin.fu.inf.pattern.util.mesure;

import de.berlin.fu.inf.pattern.util.types.Messurable;

public interface Messure<M> {
	double getDistance(M a, M b);
	Messurable<M> getMessurable(M refPoint);
}
