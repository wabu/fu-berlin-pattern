/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.perzeptron;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import de.berlin.fu.inf.pattern.data.Entry;
import de.berlin.fu.inf.pattern.util.Threads;
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
    protected F inc, dec;
    protected F lastError;

    public BackProptron(Derivable<F, F> s, F gamma, F zero, F one, Matrix<F> ... layers) {
        super(s, one, layers);
        derive = Functions.vectorLift(s.derive());
        this.zero = zero;
        this.gamma = gamma;
        this.inc = one;
        this.dec = one;
        this.lastError = zero;
    }

    public BackProptron(Derivable<F, F> s, F gamma, F zero, F one, F inc, F dec, Matrix<F> ... layers) {
        super(s, one, layers);
        derive = Functions.vectorLift(s.derive());
        this.zero = zero;
        this.gamma = gamma;
        this.inc = inc;
        this.dec = dec;
        this.lastError = zero;
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
    protected GradiantUpdate<F> calcGradient(Vector<F> x, Vector<F> target) {
        GradiantUpdate<F> grad = new GradiantUpdate<F>(gamma);

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
        F error = zero;
        for(int i=0; i<err.getDimension(); i++) {
            F e = err.get(i);
            error = error.plus(e.times(e));
        }
        grad.setError(error);

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

        // grad = { dW(1), ..., dW(l) }
        Iterator<Vector<F>> oit = outputs.iterator();
        for(Vector<F> d : Iterables.reverse(ds)) {
            // dW(i) = delta(i)T x o(i-1) (as o begins at layer 0, but delta at 1)
            @SuppressWarnings("unchecked")
            Matrix<F> a = DenseMatrix.valueOf(DenseVector.valueOf(d)).transpose();
            @SuppressWarnings("unchecked")
            Matrix<F> b = DenseMatrix.valueOf(DenseVector.valueOf(oit.next()));
            grad.add(a.times(b));
        }
        return grad;
    }

    /**
     * onlinetraining with the given data
     * @param x input to the network
     * @param target targeted output of the network
     * @return total Error
     * @see getGamma, setGamma
     */
    public F trainOnline(Vector<F> x, Vector<F> target) {
        GradiantUpdate<F> dWs = calcGradient(x, target);
        dWs.applyTo(layers);
        return dWs.error;
    }

    /**
     * train the network with the given data once
     * @param data collection of input/target pairs
     * @return total Error
     * @see getGamma, setGamma
     */
    public F trainOffline(Collection<Entry<? extends Vector<F>, ? extends Vector<F>>> data) throws InterruptedException {
        // accumulated error
        Collection<GradiantUpdate<F>> grads = Threads.doParralell(data,
            new Function<Entry<? extends Vector<F>, ? extends Vector<F>>, GradiantUpdate<F>>() {
                public GradiantUpdate<F> apply(Entry<? extends Vector<F>, ? extends Vector<F>> entry) {
                    return calcGradient(entry.getData(), entry.getClassification());
                }
            });
        if(logger.isTraceEnabled()) {
            logger.trace("done weights update");
            for(Matrix<F> W : layers) {
                logger.trace("\n"+W);
            }
        }
        F error = zero;
        for (GradiantUpdate<F> dWs : grads) {
            error = error.plus(dWs.error);
            dWs.applyTo(layers);
        }
        return error;
    }

    public void incGamma(F factor) {
        gamma = gamma.times(factor);
    }
}
