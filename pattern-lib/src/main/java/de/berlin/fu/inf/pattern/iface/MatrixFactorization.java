/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.iface;

import de.berlin.fu.inf.pattern.util.types.Vectorable;
import java.util.List;

/**
 *
 * @author wabu
 */
public interface MatrixFactorization {
    void learn(List<Vectorable> data);

    List<Vectorable> getFeatures();
    Vectorable getCode(Vectorable data);
}
