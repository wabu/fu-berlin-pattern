/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.iface;

import java.util.List;
import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.vector.Vector;

/**
 *
 * @author wabu
 */
public interface MatrixFactorization {
    void learn(List<? extends Vector<Float64>> data);

    List<? extends Vector<Float64>> getFeatures();
    Vector<Float64> encode(Vector<Float64> data);
    Vector<Float64> decode(Vector<Float64> data);
}
