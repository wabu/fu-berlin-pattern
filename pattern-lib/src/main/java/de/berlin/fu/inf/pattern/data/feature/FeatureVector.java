/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.data.feature;

import java.util.List;

/**
 *
 * @author wabu
 */
public interface FeatureVector {
    int getSize();
    Feature getFeature(int i);

    List<Feature> getFeatures();
}
