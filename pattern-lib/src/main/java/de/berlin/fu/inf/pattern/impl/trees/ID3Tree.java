/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.trees;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import de.berlin.fu.inf.pattern.data.Entry;
import de.berlin.fu.inf.pattern.data.feature.Feature;
import de.berlin.fu.inf.pattern.data.feature.FeatureDescription;
import de.berlin.fu.inf.pattern.data.feature.FeatureVector;
import de.berlin.fu.inf.pattern.iface.SupervisedClassifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author wabu
 */
public class ID3Tree<D extends FeatureVector,C> implements
        SupervisedClassifier<D, C>{
    private Node root;

    @Override
    public double train(Collection<Entry<D, C>> trainingData) {
        root = createTree(trainingData);
        return Double.NaN;
    }

    @Override
    public C classify(D data) {
        return root.getKlass(data);
    }

    private abstract class Node {
        abstract C getKlass(D data);
    }
    private class Leaf extends Node {
        private final C klass;
        public Leaf(C klass) {
            this.klass = klass;
        }

        @Override
        C getKlass(D data) {
            return klass;
        }
    }
    private class Internal extends Node {
        private final Decision<D> decision;
        private final Map<Enum<?>,Node> siblings;

        public Internal(Decision<D> decision) {
            this.decision = decision;
            this.siblings = new HashMap<Enum<?>, Node>();
        }

        void add(Enum<?> value, Node subTree) {
            siblings.put(value, subTree);
        }

        @Override
        C getKlass(D data) {
            return siblings.get(decision.apply(data)).getKlass(data);
        }
    }

    private Node createTree(Collection<Entry<D,C>> examples) {
        if(examples.isEmpty()) {
            throw new IllegalArgumentException("can't create subtree based on no data");
        }
        if( allSame(examples) ) {
            return new Leaf(examples.iterator().next().getClassification());
        }


        final int i = getBestFeature(examples);
        FeatureDescription d =
                examples.iterator().next().getData().getFeature(i).getDescription();

        Internal n = new Internal(new Decision<D> () {
            @Override
            public Enum<?> apply(D from) {
                return from.getFeature(i).getValue();
            }
        });

        for(final Enum<?> v : d.values()) {
            Collection<Entry<D,C>> filtered = 
                Collections2.filter(examples, new FeatureFilter<D,C>(i, v));
            if(filtered.isEmpty()) {
                n.add(v, new Leaf(mostCommon(examples)));
            } else {
                n.add(v, createTree(filtered));
            }
        }
        return n;
    }

    private C mostCommon(Collection<Entry<D,C>> examples) {
        assert !examples.isEmpty();
        C best = null;
        int max = 0;
        Map<C, Integer> map = new HashMap<C, Integer>();
        for(Entry<D,C> e : examples) {
            int count = 1;
            if(map.containsKey(e.getClassification())) {
                count = map.get(e.getClassification())+1;
            }
            map.put(e.getClassification(), count);
            if(count > max) {
                max = count;
                best = e.getClassification();
            }
        }
        assert best != null;
        return best;
    }
    private boolean allSame(Collection<Entry<D,C>> examples) {
        final C klass = examples.iterator().next().getClassification();
        return Iterables.all(examples, new Predicate<Entry<D,C>>(){
            @Override
            public boolean apply(Entry<D, C> input) {
                return klass.equals(input.getClassification());
            }
        });
    }

    private int getBestFeature(final Collection<Entry<D,C>> examples) {
        FeatureVector fs = examples.iterator().next().getData();
        int best = 0;
        double val = 0;

        for(int i=0; i<fs.getSize(); i++) {
            Feature f = fs.getFeature(i);
            final int pos = i;

            double sumed = 0;
            for(double d :
                Lists.transform(Lists.newArrayList(f.getDescription().values()),
                new Function<Enum<?>, Double>(){
                    @Override
                    public Double apply(Enum<?> v) {
                        Collection<Entry<D,C>> filtred = 
                                Collections2.filter(examples,
                                new FeatureFilter<D, C>(pos, v));
                        return filtred.size() * calculateEntropie(filtred);
                    }
                }
            )) {
                 sumed += d;
            }
            if(sumed > val) {
                best = i;
                val = sumed;
            }
        }
        return best;
    }

    private double calculateEntropie(final Collection<Entry<D,C>> examples) {
        Map<C, Integer> map = new HashMap<C, Integer>();
        for(Entry<D,C> e : examples) {
            int count = 1;
            if(map.containsKey(e.getClassification())) {
                count = map.get(e.getClassification())+1;
            }
            map.put(e.getClassification(), count);
        }

        double ent = 0;
        double n = examples.size();
        for(double c : map.values()) {
            ent -= (c/n) * Math.log(c/n);
        }
        return ent;
    }
}
