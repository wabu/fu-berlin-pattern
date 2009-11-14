package de.berlin.fu.inf.pattern.util.gen;

import java.util.Collection;

import de.berlin.fu.inf.pattern.data.Entry;

public interface Generator<V> {
	V generate();
	Collection<V> generate(int size);
	<C> Collection<Entry<V, C>> generateEntries(C klass, int size);
}
