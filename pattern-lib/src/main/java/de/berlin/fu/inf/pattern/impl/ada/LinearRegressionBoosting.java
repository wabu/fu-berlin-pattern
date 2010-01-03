/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.ada;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import de.berlin.fu.inf.pattern.iface.Classifier;
import de.berlin.fu.inf.pattern.util.matrix.List2ExtendedVectorTransform;
import de.berlin.fu.inf.pattern.util.types.Vectorable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.vector.Float64Matrix;
import org.jscience.mathematics.vector.Float64Vector;
import org.jscience.mathematics.vector.SparseMatrix;
import org.jscience.mathematics.vector.Vector;

/**
 *
 * @author wabu
 */
public class LinearRegressionBoosting<D extends Vectorable> extends AbstractAda<D> {
    private final Logger log = Logger.getLogger(LinearRegressionBoosting.class);

    public LinearRegressionBoosting() {
        super();
    }

    public LinearRegressionBoosting(int comitteeSize) {
        super(comitteeSize);
    }

    public LinearRegressionBoosting(Predicate<? super AbstractAda<D>> continueDecision) {
        super(continueDecision);
    }

    private Float64Matrix X;
    private Float64Vector Y;

    protected Float64Matrix calcDataMatrix(Collection<? extends D> c1, Collection<? extends D> c2) {
        List<? extends D> joined = Lists.newLinkedList(Iterables.concat(c1,c2));
        List<Float64Vector> transformed =
                Lists.transform(joined, new List2ExtendedVectorTransform());
        return Float64Matrix.valueOf(transformed);
    }

    protected Float64Vector calcValueVector(Collection<? extends D> c1, Collection<? extends D> c2) {
        Iterable<Float64> f1 =
                Iterables.transform(c1, new Function<Object, Float64>() {
            public Float64 apply(Object from) {
                return Float64.ONE;
            }
        });
        Iterable<Float64> f2 =
                Iterables.transform(c2, new Function<Object, Float64>() {
            public Float64 apply(Object from) {
                return Float64.ONE.opposite();
            }
        });
        LinkedList<Float64> joined =
                Lists.newLinkedList(Iterables.concat(f1, f2));
        return Float64Vector.valueOf(joined);
    }

    protected Vector<Float64> calcAlpha(Float64Vector weights) { //NOPMD not java.vector
        Float64Matrix weightend = X.transpose().times(
                SparseMatrix.valueOf(weights, Float64.ZERO));
        Float64Matrix sqr = weightend.times(X);
        Float64Vector foo = weightend.times(Y);
        return sqr.inverse().times(foo);
    }

    @Override
    protected void beginTraingin() {
        X = calcDataMatrix(getPositiveSamples(), getNetativeSamples());
        Y = calcValueVector(getPositiveSamples(), getNetativeSamples());
    }

    @Override
    public Classifier<D, Integer> getNextComitteeMemeber(final Float64Vector weights) {
        log.debug("new weights are: "+weights);
        return new LinearClassifier<D>(calcAlpha(weights));
    }

    @Override
    protected void endTraingin() {
        X = null;
        Y = null;
    }
}
