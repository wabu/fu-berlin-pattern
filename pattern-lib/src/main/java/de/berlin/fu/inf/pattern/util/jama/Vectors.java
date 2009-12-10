/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.util.jama;

import de.berlin.fu.inf.pattern.util.types.Vectorable;
import org.jscience.mathematics.vector.Float64Vector;

/**
 *
 * @author wabu
 */
public class Vectors {
    public static Float64Vector valueOf(Vectorable vec) {
        return Float64Vector.valueOf(vec.getVectorData());
    }
}
