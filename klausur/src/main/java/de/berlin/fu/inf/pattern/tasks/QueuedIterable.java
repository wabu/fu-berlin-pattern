/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author wabu
 */
public class QueuedIterable<E> implements Iterable<List<E>> {
    private final Iterable<E> base;
    private final int n;

    public QueuedIterable(int n, Iterable<E> base) {
        this.base = base;
        this.n = n;
    }

    @Override
    public Iterator<List<E>> iterator() {
        return new QueuedIterator<E>(n, base);
    }
}
