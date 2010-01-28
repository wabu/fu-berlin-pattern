package de.berlin.fu.inf.pattern.impl.trees;

import com.google.common.base.Predicate;
import de.berlin.fu.inf.pattern.data.Entry;
import de.berlin.fu.inf.pattern.data.feature.FeatureVector;

class FeatureFilter<D extends FeatureVector, C> implements Predicate<Entry<D, C>> {

    private final int i;
    private final Enum<?> v;

    public FeatureFilter(int i, Enum<?> v) {
        this.i = i;
        this.v = v;
    }

    @Override
    public boolean apply(Entry<D, C> input) {
        return input.getData().getFeature(i).getValue().equals(v);
    }
}
