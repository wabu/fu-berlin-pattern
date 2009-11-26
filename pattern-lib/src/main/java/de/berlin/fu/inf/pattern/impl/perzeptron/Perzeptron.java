/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.perzeptron;

import com.google.common.collect.Lists;
import de.berlin.fu.inf.pattern.util.fun.Function;
import de.berlin.fu.inf.pattern.util.fun.Functions;
import java.util.ArrayList;
import java.util.List;
import org.jscience.mathematics.structure.Field;
import org.jscience.mathematics.vector.DenseVector;
import org.jscience.mathematics.vector.Matrix;
import org.jscience.mathematics.vector.Vector;

/**
 *
 * @author wabu
 */
public class Perzeptron<F extends Field<F>> implements Function<Vector<F>, Vector<F>> {
    private final Function<Vector<F>, Vector<F>> s;
    private final List<Matrix<F>> layers;
    private final F one;

    public Perzeptron(Function<F, F> s, F one, Matrix<F> ... layers) {
        checkLayers(layers);

        this.s = Functions.vectorLift(s);
        this.layers = Lists.newArrayList(layers);
        this.one = one;
    }

    @SuppressWarnings("unchecked")
    public Perzeptron(Function<F,F> heaviside, F one, List<Matrix<F>> layers) {
        this(heaviside, one, layers.toArray(new Matrix[layers.size()]));
    }

    protected void checkLayers(Matrix<F>[] layers) throws IllegalArgumentException {
        if(layers.length == 0) {
            throw new IllegalArgumentException("perzeptron needs at least one layer");
        }
        for (int i = 1; i < layers.length; i++) {
            if (getOutputSize(layers[i-1])+1 != getInputSize(layers[i])) {
                throw new IllegalArgumentException("the output size of layer " +
                        (i - 1) + " does not match the input size of layer " + i);
            }
        }
    }

    protected int getInputSize(Matrix<F> layer) {
        return layer.getNumberOfColumns();
    }
    protected int getOutputSize(Matrix<F> layer) {
        return layer.getNumberOfRows();
    }
    protected Vector<F> extend(Vector<F> v) {
        List<F> os = new ArrayList<F>(v.getDimension()+1);
        for (int i = 0; i < v.getDimension(); i++) {
            os.add(v.get(i));
        }
        os.add(one);
        return DenseVector.valueOf(os);
    }

    public Function<Vector<F>, Vector<F>> getFunction() {
        return s;
    }
    public int getOutputSize() {
        return getOutputSize(layers.get(layers.size()-1));
    }
    public int getInputSize() {
        return getInputSize(layers.get(0));
    }

    public Vector<F> apply(Vector<F> input) {
        Vector<F> output = null;
        for (Matrix<F> layer : layers) {
            output = layer.times(extend(input));
            input = s.apply(output);
        }
        assert input != null;
        return input;
    }
}
