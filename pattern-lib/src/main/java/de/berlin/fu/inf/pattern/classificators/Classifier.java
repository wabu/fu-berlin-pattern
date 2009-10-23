package de.berlin.fu.inf.pattern.classificators;


/**
 * classifies data into classes
 * @author wabu
 *
 * @param <D> type of data
 * @param <C> type of classes
 */
public interface Classifier<D, C> {
	public abstract C classify(D data);

}