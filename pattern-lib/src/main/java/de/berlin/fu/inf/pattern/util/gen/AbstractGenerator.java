package de.berlin.fu.inf.pattern.util.gen;

import java.util.Iterator;

import com.google.common.base.Function;

import com.google.common.collect.Iterables;
import de.berlin.fu.inf.pattern.data.Entry;

public abstract class AbstractGenerator<V> implements Generator<V> {
    private class GeneratingIterator implements Iterator<V> {
        public boolean hasNext() {
            return true;
        }
        public V next() {
            return generate();
        }
        public void remove() {
            throw new UnsupportedOperationException("remove on generator");
        }
    }
    private Iterator<V> it = new GeneratingIterator();

    public Iterable<V> getGenerator() {
        return new Iterable<V>() {
            public Iterator<V> iterator() {
                return it;
            }
        };
    }

    public <C> Iterable<Entry<V, C>> getEntryGenerator(final C klass) {
        return Iterables.transform(getGenerator(), new Function<V, Entry<V, C>>() {
            public Entry<V, C> apply(V data) {
                return new Entry<V, C>(data, klass);
            }
        });
    }

    public Iterable<V> getGenerator(int size) {
        return Generators.take(size, getGenerator());
    }

    public <C> Iterable<Entry<V, C>> getEntryGenerator(final C klass, int size) {
        return Iterables.transform(getGenerator(size), new Function<V, Entry<V, C>>() {
            public Entry<V, C> apply(V data) {
                return new Entry<V, C>(data, klass);
            }
        });
    }
}
