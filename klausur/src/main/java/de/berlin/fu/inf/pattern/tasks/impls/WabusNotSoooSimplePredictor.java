/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks.impls;

import com.google.common.collect.Lists;
import de.berlin.fu.inf.pattern.tasks.AbstractPredictor;
import de.berlin.fu.inf.pattern.util.Pair;
import java.util.ArrayList;
import java.util.List;
import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.vector.DenseVector;
import org.jscience.mathematics.vector.Float64Vector;
import org.jscience.mathematics.vector.Vector;

/**
 *
 * @author wabu
 */
public class WabusNotSoooSimplePredictor extends AbstractPredictor {
    public WabusNotSoooSimplePredictor() {
        super(5);
    }

    public WabusNotSoooSimplePredictor(int i) {
        super(i);
    }

    @Override
    protected Vector<Float64> transformOriginalToInternState(List<Vector<Float64>> data) {
        List<Float64> concated =
                new ArrayList<Float64>(getHistorySize()*4);

        for(Vector<Float64> v : data) {
            concated.addAll(
               Lists.newArrayList(
                    v.get(0), // X
                    v.get(1), // Y
                    v.get(2).times(v.get(4)), // cos*v
                    v.get(3).times(v.get(4)) // cos*v
                ));
        }
        return Float64Vector.valueOf(concated);
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
