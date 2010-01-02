/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.ada;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import de.berlin.fu.inf.pattern.iface.DiscriminatingClassifier;
import de.berlin.fu.inf.pattern.util.matrix.List2VecotrTransform;
import de.berlin.fu.inf.pattern.util.matrix.Vectors;
import de.berlin.fu.inf.pattern.util.types.Vectorable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.vector.Float64Matrix;
import org.jscience.mathematics.vector.Float64Vector;
import org.jscience.mathematics.vector.Vector;

/**
 *
 * @author wabu
 */
public class LinearRegression<D extends Vectorable> implements DiscriminatingClassifier<D> {
    protected Vector<Float64> alpha; // NOPMD - thats not a java.util.Vector

    protected Float64Matrix calcDataMatrix(Collection<? extends D> c1, Collection<? extends D> c2) {
        List<? extends D> joined = Lists.newLinkedList(Iterables.concat(c1,c2));
        List<Float64Vector> transformed =
                Lists.transform(joined, new List2VecotrTransform());
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

    protected void calcAlpha(Float64Matrix X, Float64Vector Y){
        this.alpha = X.solve(Y);
    }

    public double train(Collection<? extends D> c1, Collection<? extends D> c2) {
        Float64Matrix X = calcDataMatrix(c1, c2);
        Float64Vector Y = calcValueVector(c1, c2);
        calcAlpha(X, Y);
        return Double.NaN;
    }

    public Integer classify(D data) {
        double val = Vectors.valueOf(data).times(alpha).doubleValue();
        return (val >= 0) ? 0 : 1;
    }
}
