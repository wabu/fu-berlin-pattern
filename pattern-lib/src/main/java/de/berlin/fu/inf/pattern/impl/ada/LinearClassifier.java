/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.ada;

import de.berlin.fu.inf.pattern.iface.Classifier;
import de.berlin.fu.inf.pattern.util.matrix.Vectors;
import de.berlin.fu.inf.pattern.util.types.Vectorable;
import java.util.Random;
import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.vector.Float64Vector;
import org.jscience.mathematics.vector.Vector;

/**
 *
 * @author wabu
 */
public class LinearClassifier<D extends Vectorable> implements Classifier<D, Integer>{
    private final Vector<Float64> vec;

    LinearClassifier(Vector<Float64> vec) {
        this.vec = vec;
    }

    public Integer classify(Vectorable data) {
        Float64 result = vec.times(Vectors.expendedOf(data));
        return result.doubleValue() >= 0 ? 0 : 1;
    }

    private static Random rnd = new Random();
    public static LinearClassifier generate(int dim) {
        double data[] = new double[dim+1];
        for(int i=0; i<dim+1; i++) {
            data[i] = 1d-2d*rnd.nextDouble();
        }
        Float64Vector vec = Vectors.valueOf(data);
        vec = vec.times(1d/vec.normValue());
        return new LinearClassifier(vec);
    }

    @Override
    public String toString() {
        return "LinearClassifier:"+vec;
    }
}
