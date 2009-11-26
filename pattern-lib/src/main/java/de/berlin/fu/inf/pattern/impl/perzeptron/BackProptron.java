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
    protected final F zero, gamma;


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

    public List<Matrix<F>> calcUpdate(Vector<F> input, Vector<F> expected) {
        // outputs = {o(0)=x, o(1), ... o(l)}
        List<Vector<F>> outputs = new ArrayList<Vector<F>>(super.getDepth());
        // derives = {o(1)', ..., o(n)'}
        List<Vector<F>> derives = new ArrayList<Vector<F>>(super.getDepth());

        Vector<F> in = null, out = input;

        outputs.add(extend(out));
        for (Matrix<F> layer : layers) {
            in = layer.times(extend(out));
            out = s.apply(in);
            outputs.add(extend(out));
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

        Vector<F> err = out.minus(expected);

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
    void train(Collection<Entry<? extends Vector<F>, ? extends Vector<F>>> data) {
        // accumulated error
        List<Matrix<F>> acc = null;
        for(Entry<? extends Vector<F>, ? extends Vector<F>> e : data) {
            List<Matrix<F>> dW = calcUpdate(e.getData(), e.getClassification());
            if(acc == null) {
                acc = dW;
            } else {
                for(int i=0; i<dW.size(); i++) {
                    acc.set(i, acc.get(i).plus(dW.get(i)));
                }
            }
        }
        // W(i) = W - gamma * dW(i)
        for(int i=0; i<layers.size(); i++) {
            Matrix<F> W = layers.get(i);
            Matrix<F> dW = acc.get(i);

            W = W.minus(dW.times(gamma));
            layers.set(i, W);
        }
    }

}
