package de.berlin.fu.inf.pattern.util.gen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import de.berlin.fu.inf.pattern.data.Entry;

public abstract class AbstractGenerator<V> implements Generator<V> {
	
	public Collection<V> generate(int size) {
		List<V> list = new ArrayList<V>(size);
		for(int i=0; i<size; i++) {
			list.add(generate());
		}
		return list;
	}
	
	public <C> Collection<Entry<V, C>> generateEntries(final C klass, final int size) {
		return Collections2.transform(generate(size), new Function<V, Entry<V, C>>() {
			public Entry<V, C> apply(V data) {
				return new Entry<V, C>(data, klass);
			}
		});
	}
}
