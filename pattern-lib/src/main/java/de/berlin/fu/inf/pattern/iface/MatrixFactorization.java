/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.iface;

import java.util.List;
import org.jscience.mathematics.vector.Float64Vector;

/**
 *
 * @author wabu
 */
public interface MatrixFactorization {
    void learn(List<Float64Vector> data);

    public List<Float64Vector> getFeatures();
    public Float64Vector encode(Float64Vector data);
    public Float64Vector decode(Float64Vector data);
}
