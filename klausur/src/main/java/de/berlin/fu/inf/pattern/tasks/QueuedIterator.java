/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author wabu
 */
public class QueuedIterator<E> extends AbstractIterator<List<E>> {
    private final LinkedList<E> queue; //NOPMD
    private final Iterator<E> base;

    public QueuedIterator(int n, Iterable<E> base) {
        super();
        this.queue = new LinkedList<E>();
        this.base = base.iterator();

        for(int i=0; i<n-1; i++) {
            Preconditions.checkArgument(this.base.hasNext(), 
                    "base must have at least %s items", n);
            queue.add(this.base.next());
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    protected List<E> computeNext() {
        if(!base.hasNext()) {
            return endOfData();
        }
        queue.removeFirst();
        queue.add(base.next());
        return (List<E>) queue.clone();
    }
}
