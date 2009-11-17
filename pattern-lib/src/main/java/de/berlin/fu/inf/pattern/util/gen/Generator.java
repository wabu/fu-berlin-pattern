package de.berlin.fu.inf.pattern.util.gen;


import de.berlin.fu.inf.pattern.data.Entry;

public interface Generator<V> {
    /**
     * generate a vaule
     * @return
     */
	V generate();
    /**
     * lazy iterator generating infinte list of data
     * @return
     */
    Iterable<V> getGenerator();
    /**
     * lazy interator generting infinte list of enties
     * @param <C>
     * @return
     */
    <C> Iterable<Entry<V,C>> getEntryGenerator(C klass);

    Iterable<V> getGenerator(int size);
    <C> Iterable<Entry<V,C>> getEntryGenerator(C klass, int size);
}
