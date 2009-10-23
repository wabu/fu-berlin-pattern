package de.berlin.fu.inf.pattern.data;

import java.util.Collection;
import java.util.List;

import de.berlin.fu.inf.pattern.classificators.Entry;

/**
 * hold some enties
 * @see Entry
 * @author wabu
 *
 * @param <D>
 * @param <C>
 */
public interface Database<D, C> extends Iterable<Entry<D, C>>{

	public abstract boolean add(Entry<D, C> e);

	public abstract boolean addAll(Collection<? extends Entry<D, C>> c);

	public abstract boolean contains(Entry<D, C> o);

	public abstract int size();

	public abstract List<D> getDataView();

}