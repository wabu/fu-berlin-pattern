package de.berlin.fu.inf.pattern.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;


public class SimpleDatabase<D, C> implements Iterable<Entry<D,C>>, Database<D, C> {
	private final List<Entry<D, C>> enties;
	
	public SimpleDatabase() {
		enties = new ArrayList<Entry<D,C>>();
	}
	
	public void add(Entry<D, C> e) {
		enties.add(e);
	}

	public void addAll(Iterable<Entry<D, C>> c) {
		for(Entry<D, C> e : c){
			enties.add(e);
		}
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
