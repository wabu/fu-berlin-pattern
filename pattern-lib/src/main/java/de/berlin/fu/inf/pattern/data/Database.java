package de.berlin.fu.inf.pattern.data;

import java.util.List;


/**
 * hold some enties
 * @see Entry
 * @author wabu
 *
 * @param <D>
 * @param <C>
 */
public interface Database<D, C> extends Iterable<Entry<D, C>>{

	public abstract void add(Entry<D, C> e);

	public abstract void addAll(Iterable<Entry<D, C>> trainingData);

	public abstract boolean contains(Entry<D, C> o);

	public abstract int size();

	public abstract List<D> getDataView();

}