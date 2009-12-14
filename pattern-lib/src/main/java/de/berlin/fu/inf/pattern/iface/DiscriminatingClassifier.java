package de.berlin.fu.inf.pattern.iface;

import java.util.Collection;




/**
 * classifies data into classes
 * @author wabu
 *
 * @param <D> type of data
 * @param <C> type of classes
 * @return value describing estimated error of the classifier
 */
public interface DiscriminatingClassifier<D> extends Classifier<D, Integer> {
	double train(Collection<? extends D> c1, Collection<? extends D> c2);
}