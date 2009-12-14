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
public final class Vectors {
    private Vectors() {}

    public static Float64Vector valueOf(double[] data) {
        return Float64Vector.valueOf(data);
    }

    public static Float64Vector valueOf(Vectorable vec) {
        return Float64Vector.valueOf(vec.getVectorData());
    }
    public static Float64Vector expendedOf(Vectorable vec) {
        double[] data = new double[vec.getDimension()+1];
        System.arraycopy(vec.getVectorData(), 0, data, 0, vec.getDimension());
        data[vec.getDimension()]=1;
        return Float64Vector.valueOf(data);
    }
}
