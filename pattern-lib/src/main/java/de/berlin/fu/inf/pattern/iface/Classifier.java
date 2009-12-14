package de.berlin.fu.inf.pattern.iface;

public interface Classifier<D, C> {
	C classify(D data);
}
