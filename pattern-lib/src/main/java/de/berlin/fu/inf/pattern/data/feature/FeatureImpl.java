/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.data.feature;

/**
 *
 * @author wabu
 */
public class FeatureImpl implements Feature {
    private final Enum<?> val;
    private final Enum<?>[] values;

    public FeatureImpl(Enum<?>[] values, Enum<?> val) {
        this.val = val;
        this.values = values;
    }


    @Override
    public FeatureDescription getDescription() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Enum<?> getValue() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
