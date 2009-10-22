package de.berlin.fu.inf.pattern.classificators;

import java.util.Collection;

import de.berlin.fu.inf.pattern.data.Entry;

/**
 * classifies data into classes
 * @author wabu
 *
 * @param <D> type of data
 * @param <C> type of classes
 */
public interface Classifer<D, C> {

	public abstract boolean add(Entry<D, C> e);
	public abstract boolean addAll(Collection<? extends Entry<D, C>> c);

	public abstract C classify(D data);

}