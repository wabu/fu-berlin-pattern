package de.berlin.fu.inf.pattern.iface;

import java.util.Collection;


public interface SelflearningClassifier<D, C> extends Classifier<D, C> {
	double train(Collection<D> data);
}
