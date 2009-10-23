package de.berlin.fu.inf.pattern.classificators;

import java.util.Collection;



/**
 * classifies data into classes
 * @author wabu
 *
 * @param <D> type of data
 * @param <C> type of classes
 */
public interface Classifier<D, C> {
	C classify(D data);
	void train(Collection<Entry<D,C>> trainingData);
}