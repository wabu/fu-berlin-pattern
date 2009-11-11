package de.berlin.fu.inf.pattern.iface;

import java.util.Collection;



/**
 * classifies data into classes
 * @author wabu
 *
 * @param <D> type of data
 * @param <C> type of classes
 */
public interface DiscriminatingClassifier<D> extends Classifier<D, Integer> {
	void train(Collection<D> c1, Collection<D> c2);
}