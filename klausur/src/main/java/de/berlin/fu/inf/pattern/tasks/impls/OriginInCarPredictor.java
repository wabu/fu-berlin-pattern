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
import org.jscience.mathematics.vector.Matrix;
import org.jscience.mathematics.vector.Vector;

/**
 *
 * @author wabu
 */
public class OriginInCarPredictor extends AbstractPredictor {

    public OriginInCarPredictor() {
        super(5);
    }

    public OriginInCarPredictor(int i) {
        super(i);
    }

    @Override
    protected Vector<Float64> transformOriginalToInternState(List<Vector<Float64>> data) {
        DataEntry lastState = new DataEntry(data.get(data.size() - 1));
        data.remove(data.size()-1);

        Vector<Float64> trans = lastState.getPos();
        Matrix<Float64> rot = lastState.getRotation();

        List<Float64> concated =
                new ArrayList<Float64>(getHistorySize()*4);

        concated.add(Float64.valueOf(lastState.getV()));

        for(Vector<Float64> v : data) {
            DataEntry d = new DataEntry(v);

            Vector<Float64> pos = rot.times(d.getPos().minus(trans));
            Vector<Float64> vel = rot.times(d.getVel());

            concated.addAll(
               Lists.newArrayList(
                  pos.get(0),
                  pos.get(1),
                  vel.get(0),
                  vel.get(1),
                  Float64.valueOf(d.getV()) ));
        }
        return Float64Vector.valueOf(concated);
    }

    @Override
    protected Pair<Vector<Float64>, Vector<Float64>> transformOriginalToInternState(
            List<Vector<Float64>> hist, Vector<Float64> target) {
        Vector<Float64> v = transformOriginalToInternState(hist);

        DataEntry lastState = new DataEntry(hist.get(hist.size() - 1));
        Vector<Float64> trans = lastState.getPos();
        Matrix<Float64> rot = lastState.getRotation();

        DataEntry t = new DataEntry(target);
        Vector<Float64> pos = rot.times(t.getPos().minus(trans));
        Vector<Float64> vel = rot.times(t.getVel());

        Vector<Float64> intern = Float64Vector.valueOf(
                  pos.get(0),
                  pos.get(1),
                  vel.get(0),
                  vel.get(1));

        return new Pair<Vector<Float64>, Vector<Float64>>(v, intern);
    }

    @Override
    protected Vector<Float64> transformInternToOriginalState(
            List<Vector<Float64>> history, Vector<Float64> state) {

        DataEntry lastState = new DataEntry(history.get(history.size() - 1));
        Vector<Float64> trans = lastState.getPos(); //NOPMD
        Matrix<Float64> rot = lastState.getRotation();

        DenseVector<Float64> pos =
                Float64Vector.valueOf(state.get(0), state.get(1));
        Float64Vector vel =
                Float64Vector.valueOf(state.get(2).doubleValue(),
                    state.get(3).doubleValue());

        Vector<Float64> newPos = rot.inverse().times(pos).plus(trans);
        Float64 v = vel.norm();

        return Float64Vector.valueOf(
                newPos.get(0),
                newPos.get(1),
                vel.get(0).divide(v),
                vel.get(1).divide(v),
                v
            );
    }

}
