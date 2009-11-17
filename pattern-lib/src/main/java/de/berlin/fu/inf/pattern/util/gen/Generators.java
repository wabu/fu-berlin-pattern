/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.util.gen;

import java.util.Iterator;

/**
 *
 * @author wabu
 */
public class Generators {
    public static <V> Iterable<V> take(final int num, final Iterable<V> iterable) {
        return new Iterable<V>() {
            final Iterator<V> it = iterable.iterator();
            public Iterator<V> iterator() {
                return new Iterator<V>() {
                    private int i = 0;

                    public boolean hasNext() {
                        return i<num && it.hasNext();
                    }

                    public V next() {
                        i++;
                        return it.next();
                    }

                    public void remove() {
                        it.remove();
                        i++;
                    }
                };
            }
        };
    }
}
