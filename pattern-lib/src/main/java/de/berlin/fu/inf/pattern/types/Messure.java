package de.berlin.fu.inf.pattern.types;

public interface Messure<M> {
	double getDistance(M a, M b);
	Messurable<M> getMessurable(M refPoint);
}
