/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.util.test;

import de.berlin.fu.inf.pattern.data.Entry;
import de.berlin.fu.inf.pattern.iface.Classifier;
import de.berlin.fu.inf.pattern.util.Threads;
import de.berlin.fu.inf.pattern.util.fun.Proc;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author wabu
 */
public class ClassifierTest<V, C> {
    private final Classifier<V, C> classifier;

    /**
     * @param classifier the trained classifier
     */
    public ClassifierTest(Classifier<V, C> classifier) {
        this.classifier = classifier;
    }

    public double runTest(Iterable<? extends Entry<V,C>> testData) {
        final AtomicInteger total = new AtomicInteger();
        final AtomicInteger correct = new AtomicInteger();

        Threads.doParralell(testData, new Proc<Entry<? extends V,C>>() {
            public void apply(Entry<? extends V, C> t) {
                if(t.getClassification().equals(classifier.classify(t.getData()))) {
                    correct.incrementAndGet();
                }
                total.incrementAndGet();
            }
        });
        return correct.get()/(double)total.get();
    }
}
