package de.berlin.fu.inf.pattern.util.gen;


import de.berlin.fu.inf.pattern.data.Entry;
import java.util.Collection;
import java.util.List;

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

    List<V> getGeneratedData(int size);
    <C> List<Entry<V,C>> getGeneratedEntries(C klass, int size);
}
