package de.berlin.fu.inf.pattern.impl.knn;

import java.util.Collection;
import java.util.Comparator;
import java.util.NavigableMap;
import java.util.TreeMap;

import javax.annotation.Nullable;

import de.berlin.fu.inf.pattern.util.mesure.DistanceComperator;
import de.berlin.fu.inf.pattern.util.types.Messurable;

/**
 * collect the k best elements
 * @author wabu
 *
 * @param <K>
 * @param <V>
 */
class KList<K,V> {
	final int k;
	final NavigableMap<K,V> map;
	
	public KList(int k) {
		this(k,(Comparator<K>)null);
	}
	
	/**
	 * use distance to a reference point as definition of best
	 * @param k
	 * @param refPoint
	 */
	public KList(int k, Messurable<K> refPoint) {
		this(k, new DistanceComperator<K>(refPoint));
	}
	public KList(int k, @Nullable final Comparator<K> compare) {
		this.k = k;
		this.map = new TreeMap<K,V>(compare);
	}
	
	/**
	 * add value to collection, if its key is one of the k best keys 
	 * @param key
	 * @param value
	 */
    @edu.umd.cs.findbugs.annotations.SuppressWarnings("BAS")
	public void add(final K key, V value){
		// put data point into map
		map.put(key,value);
		if(map.size() > k){
			// remove worst data point
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
