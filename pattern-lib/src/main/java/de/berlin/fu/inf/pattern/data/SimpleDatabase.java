package de.berlin.fu.inf.pattern.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class SimpleDatabase<D, C> implements Iterable<Entry<D,C>>, Database<D, C> {
	private final List<Entry<D, C>> enties;
	
	public SimpleDatabase() {
		enties = new ArrayList<Entry<D,C>>();
	}
	
	public boolean add(Entry<D, C> e) {
		return enties.add(e);
	}

	public boolean addAll(Collection<? extends Entry<D, C>> c) {
		return enties.addAll(c);
	}

	public boolean contains(Entry<D,C> o) {
		return enties.contains(o);
	}
	
	public int size() {
		return enties.size();
	}

	public Iterator<Entry<D, C>> iterator() {
		return enties.iterator();
	}
	
	public List<D> getDataView() {
		return Lists.transform(this.enties, Entry.<D,C>getDataFunction());
	}
	
	@Override
	public String toString() {
		return enties.toString();
	}
}
