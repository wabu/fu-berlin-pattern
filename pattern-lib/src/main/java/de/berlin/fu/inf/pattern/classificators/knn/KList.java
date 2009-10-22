package de.berlin.fu.inf.pattern.classificators.knn;

import java.util.Collection;
import java.util.Comparator;
import java.util.NavigableMap;
import java.util.TreeMap;

import javax.annotation.Nullable;

import de.berlin.fu.inf.pattern.types.DistanceComperator;
import de.berlin.fu.inf.pattern.types.Messurable;

class KList<K,V> {
	final int k;
	final NavigableMap<K,V> map;
	
	public KList(int k) {
		this(k,(Comparator<K>)null);
	}
	public KList(int k, Messurable<K> refPoint) {
		this(k, new DistanceComperator<K>(refPoint));
	}
	
	
	public KList(int k, @Nullable final Comparator<K> compare) {
		this.k = k;
		this.map = new TreeMap<K,V>(compare);
	}
	
	public void add(final K key, V value){
		map.put(key,value);
		if(map.size() > k){
			V removed = map.remove(map.lastKey());
			assert removed != null;
		}
	}
	
	public Collection<V> getValues(){
		return map.values();
	}
	
	@Override
	public String toString() {
		return map.toString();
	}
}
