/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.data.feature;

/**
 *
 * @author wabu
 */
public interface Feature {
    FeatureDescription getDescription();
    Enum<?> getValue();
}
