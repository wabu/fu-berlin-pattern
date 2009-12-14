/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.util.test;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import de.berlin.fu.inf.pattern.data.Entry;
import de.berlin.fu.inf.pattern.iface.Classifier;
import de.berlin.fu.inf.pattern.util.gen.Generator;
import org.apache.log4j.Logger;

/**
 *
 * @author wabu
 */
public class IntClassifierTest<V> extends ClassifierTest<V, Integer> {
    Logger log = Logger.getLogger(IntClassifierTest.class);
    /**
     * @param classifier the trained classifier
     */
    public IntClassifierTest(Classifier<V, Integer> classifier) {
        super(classifier);
    }

    public double runTest(final Iterable<? extends V> ... colls) {
        return runTest(Iterables.concat(Iterables.transform(
            Lists.newArrayList(colls),
            new Function<Iterable<? extends V>, Iterable<Entry<V,Integer>>>(){
                int k = 0;
                public Iterable<Entry<V, Integer>> apply(Iterable<? extends V> from) {
                    return Iterables.transform(from,
                    new Function<V,Entry<V,Integer>>() {
                        final int klass = k++;
                        public Entry<V, Integer> apply(V from) {
                            return new Entry<V, Integer>(from, klass);
                        }});
            }})));

    }

    public double runTest(int num, Generator<? extends V> ... gens) {
        log.debug("running "+num+" test with generated data");

        @SuppressWarnings("unchecked")
        Iterable<? extends V>[] its = (Iterable<? extends V>[]) new Iterable<?>[gens.length];
        for (int i = 0; i < gens.length; i++) {
            its[i] = gens[i].getGenerator(num);
        }
        return runTest(its);
    }
}
