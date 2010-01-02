/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.ada;

import com.google.common.base.Predicate;
import de.berlin.fu.inf.pattern.iface.Classifier;
import de.berlin.fu.inf.pattern.util.types.Vectorable;
import java.util.Collection;
import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.vector.Float64Matrix;
import org.jscience.mathematics.vector.Float64Vector;
import org.jscience.mathematics.vector.Matrix;
import org.jscience.mathematics.vector.SparseMatrix;

/**
 *
 * @author wabu
 */
public class LinearRegressionBoosting<D extends Vectorable> extends AbstractAda<D> {
    public LinearRegressionBoosting() {
        super();
    }

    public LinearRegressionBoosting(int comitteeSize) {
        super(comitteeSize);
    }

    public LinearRegressionBoosting(Predicate<? super AbstractAda<D>> continueDecision) {
        super(continueDecision);
    }

    @Override
    protected void beginTraingin() {
        // Nothing to do here
    }

    @Override
    public Classifier<D, Integer> getNextComitteeMemeber(final Float64Vector weights) {
        LinearRegression<D> reg = new LinearRegression<D>() {
            @Override
            protected Float64Matrix calcDataMatrix(
                    Collection<? extends D> c1, Collection<? extends D> c2) {
                Matrix<Float64> W = SparseMatrix.valueOf(weights, Float64.ZERO);
                return super.calcDataMatrix(c1, c2).times(W);
            }
        };
        reg.train(getPositiveSamples(), getNetativeSamples());
        return reg;
    }
}
