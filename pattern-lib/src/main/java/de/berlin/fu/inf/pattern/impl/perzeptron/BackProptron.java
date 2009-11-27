/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.perzeptron;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import de.berlin.fu.inf.pattern.data.Entry;
import de.berlin.fu.inf.pattern.util.fun.Derivable;
import de.berlin.fu.inf.pattern.util.fun.Funct;
import de.berlin.fu.inf.pattern.util.fun.Functions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.jscience.mathematics.structure.Field;
import org.jscience.mathematics.vector.DenseMatrix;
import org.jscience.mathematics.vector.DenseVector;
import org.jscience.mathematics.vector.Matrix;
import org.jscience.mathematics.vector.SparseMatrix;
import org.jscience.mathematics.vector.Vector;

/**
 *
 * @author wabu
 */
public class BackProptron<F extends Field<F>> extends Perzeptron<F> {
    private final Logger logger = Logger.getLogger(BackProptron.class);
    protected final Funct<Vector<F>, Vector<F>> derive;
    protected F zero, gamma;


    public BackProptron(Derivable<F, F> s, F gamma, F zero, F one, Matrix<F> ... layers) {
        super(s, one, layers);
        derive = Functions.vectorLift(s.derive());
        this.zero = zero;
        this.gamma = gamma;
    }

    public BackProptron(Derivable<F,F> s, F gamma, F zero, F one, List<Matrix<F>> layers) {
        super(s, one, layers);
        derive = Functions.vectorLift(s.derive());
        this.zero = zero;
        this.gamma = gamma;
    }

    public void setGamma(F gamma) {
        this.gamma = gamma;
    }

    public F getGamma() {
        return gamma;
    }


    /**
     * calculates gradient for all weights accroding to error (this.apply(x)-target)^2
     * @param x input to the network
     * @param target target result for input x
     * @return gradient for weights, one matrix for each of the layers weights matrix
     */
    protected List<Matrix<F>> calcGradient(Vector<F> x, Vector<F> target) {
        // outputs = {o(0)=x, o(1), ... o(l)}
        List<Vector<F>> outputs = new ArrayList<Vector<F>>(super.getDepth());
        // derives = {o(1)', ..., o(n)'}
        List<Vector<F>> derives = new ArrayList<Vector<F>>(super.getDepth());

        // output of the input layer is just the input vector x
        Vector<F> in = null, out = extend(x);
        outputs.add(out);

        for (Matrix<F> layer : layers) {
            // in(i) = W(i) x out(i-1)
            in = layer.times(out);
            // out(i) = s(in(i))
            out = extend(s.apply(in));

            // set o(i) and o(i)'
            outputs.add(out);
            derives.add(derive.apply(in));
        }

        // Ds = { D1, D2, ..., Dl }
        List<Matrix<F>> Ds = Lists.transform(derives, new Function<Vector<F>,Matrix<F>>() {
            public Matrix<F> apply(Vector<F> from) {
                return SparseMatrix.valueOf(from, zero);
            }
        });

        // ds = { delta(l), delta(l-1), ... , delta(1) }
        List<Vector<F>> ds = new ArrayList<Vector<F>>(super.getDepth());

        Vector<F> err = intend(out).minus(target);

        Iterator<Matrix<F>> ls = Iterables.reverse(layers).iterator();
        for(Matrix<F> D : Iterables.reverse(Ds)) {
            // delta(i) = D(i) x err(i)
            Vector<F> d = D.times(err);
            ds.add(d);

            Matrix<F> W = ls.next();
            err = intend(W.transpose().times(d));
            // err(i-1) = W(i)T x d(i)
            // => delta(i-1) = D(i-1) x err = D(i-1) x W(i)T x d(i)
        }

        // ws = { dW(1), ..., dW(l) }
        List<Matrix<F>> ws = new ArrayList<Matrix<F>>(super.getDepth());

        Iterator<Vector<F>> oit = outputs.iterator();
        for(Vector<F> d : Iterables.reverse(ds)) {
            // dW(i) = delta(i)T x o(i-1) (as o begins at layer 0, but delta at 1)
            @SuppressWarnings("unchecked")
            Matrix<F> a = DenseMatrix.valueOf(DenseVector.valueOf(d)).transpose();
            @SuppressWarnings("unchecked")
            Matrix<F> b = DenseMatrix.valueOf(DenseVector.valueOf(oit.next()));
            ws.add(a.times(b));
        }
        return ws;
    }

    /**
     * apply an update to the weights of the network
     * @param dWs gradient for weights, one matrix for each of the layers weights matrix
     * @see calcUpdate
     */
    protected void applyUpdate(List<Matrix<F>> dWs) {
        for (int i = 0; i < layers.size(); i++) {
            Matrix<F> W = layers.get(i);
            Matrix<F> dW = dWs.get(i);
            W = W.minus(dW.times(gamma));
            layers.set(i, W);
        }
    }


    /**
     * onlinetraining with the given data
     * @param x input to the network
     * @param target targeted output of the network
     * @see getGamma, setGamma
     */
    public void trainOnline(Vector<F> x, Vector<F> target) {
        List<Matrix<F>> dWs = calcGradient(x, target);
        applyUpdate(dWs);
    }

    /**
     * train the network with the given data once
     * @param data collection of input/target pairs
     * @see getGamma, setGamma
     */
    public void trainOffline(Collection<Entry<? extends Vector<F>, ? extends Vector<F>>> data) {
        // accumulated error
        List<List<Matrix<F>>> grads = new ArrayList<List<Matrix<F>>>(data.size());
        for(Entry<? extends Vector<F>, ? extends Vector<F>> e : data) {
            List<Matrix<F>> dW = calcGradient(e.getData(), e.getClassification());
            grads.add(dW);
        }
        for (List<Matrix<F>> dWs : grads) {
            applyUpdate(dWs);
        }
    }

}
