package de.berlin.fu.inf.pattern.iface;

import java.util.Collection;

import de.berlin.fu.inf.pattern.data.Entry;



/**
 * classifies data into classes
 * @author wabu
 *
 * @param <D> type of data
 * @param <C> type of classes
 */
public interface SupervisedClassifier<D, C> extends Classifier<D, C> {
	void train(Collection<Entry<D,C>> trainingData);
}