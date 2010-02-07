/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks.impls;

import de.berlin.fu.inf.pattern.tasks.AbstractPredictor;
import de.berlin.fu.inf.pattern.util.Pair;
import java.util.List;
import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.vector.DenseVector;
import org.jscience.mathematics.vector.Float64Vector;
import org.jscience.mathematics.vector.Vector;

/**
 *
 * @author wabu
 */
public class WabusSimplePredictor extends AbstractPredictor {
    public WabusSimplePredictor() {
        super(1);
    }

    @Override
    protected Vector<Float64> transformOriginalToInternState(List<Vector<Float64>> data) {
        assert data.size() == 1;
        Vector<Float64> v = data.get(0);

        return Float64Vector.valueOf(
                v.get(0), // X
                v.get(1), // Y
                v.get(2).times(v.get(4)), // cos*v
                v.get(3).times(v.get(4)) // cos*v
            );
    }

    @Override
    protected Pair<Vector<Float64>, Vector<Float64>> transformOriginalToInternState(
            List<Vector<Float64>> hist, Vector<Float64> target) {
        Vector<Float64> v = transformOriginalToInternState(hist);

        return new Pair<Vector<Float64>, Vector<Float64>>(v, target);
    }

    @Override
    protected Vector<Float64> transformInternToOriginalState(
            List<Vector<Float64>> history, Vector<Float64> state) {
        return state;
    }

}
