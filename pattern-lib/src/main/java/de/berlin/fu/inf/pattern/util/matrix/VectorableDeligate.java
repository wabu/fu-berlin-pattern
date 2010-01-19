/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.util.matrix;

import de.berlin.fu.inf.pattern.util.types.Vectorable;
import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.vector.Vector;

/**
 *
 * @author wabu
 */
public class VectorableDeligate implements Vectorable {
    private final Vector<? extends Number> data;
    public VectorableDeligate(Vector<? extends Number> data) {
        this.data = data;
    }

    public double[] getVectorData() {
        double d[] = new double[getDimension()];
        for(int i=0; i<getDimension(); i++) {
            d[i] = data.get(i).doubleValue();
        }
        return d;
    }

    public int getDimension() {
        return data.getDimension();
    }

    public double get(int i) {
        return data.get(i).doubleValue();
    }
}
