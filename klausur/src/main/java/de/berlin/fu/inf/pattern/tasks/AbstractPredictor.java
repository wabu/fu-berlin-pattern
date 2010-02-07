/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import de.berlin.fu.inf.pattern.util.Pair;
import java.util.List;
import javax.annotation.CheckForNull;
import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.vector.Float64Matrix;
import org.jscience.mathematics.vector.Matrix;
import org.jscience.mathematics.vector.Vector;

/**
 * The Predictor tries to approximate a future state,
 * given some states in history. To <code>train</code>
 * the Predictor you have to give him a list of consecutive states.
 *
 * The Aproximation is not done in the original space, but
 * uses an intern space. Therefore we have to transform between the
 * original space and the intern space:
 * <ul>
 * <li>
 *   transform histSize original vectors into a vector
 *   used internally for the approximation
 *   @see transformOriginalToInternState
 * </li>
 * <li>
 *   transform an original state into a single attribute that should be apprximated
 *   @see transformOriginalToInternTarget
 * </li>
 * <li>
 *   transform a vector of the interanl attributes into an original state
 *   @see transformInternToOriginalState
 * </li>
 * </ul>
 * @author wabu
 */
public abstract class AbstractPredictor {
    /** number of states used for prediction */
    protected final int historySize;

    @CheckForNull
    private Matrix<Float64> params;

    public AbstractPredictor(int historySize) {
        this.historySize = historySize;
    }

    /**
     * transforms/combines a histsized list of original state vectors
     * it into a vector used internally in the linear aproximation
     * @param <code>historySize</code> original state vectors,
     *      asscending in time
     * @return vector to use in linear prediction
     */
    protected abstract Vector<Float64> transformOriginalToInternState( //NOPMD
            List<Vector<Float64>> data);

    /**
     * transforms/combines a histsized list of original state vectors
     * and the target state it into a vector used internally in the linear
     * aproximation
     * @param <code>historySize</code> original state vectors,
     *      asscending in time
     * @param target  target state
     * @return pair of (a,b), where a is the transformed state list
     *      and b is transformed target vector
     */
    protected abstract Pair<Vector<Float64>, Vector<Float64>> 
            transformOriginalToInternState(
            List<Vector<Float64>> hist,
            Vector<Float64> target); //NOPMD

    /**
     * transoform prediction in internal space to original state
     * @param <code>historySize</code> original state vectors,
     *      asscending in time
     * @param prediction prediction vector in internal space
     * @return prediction vector in original space
     */
    protected abstract Vector<Float64> transformInternToOriginalState( //NOPMD
            List<Vector<Float64>> history,
            Vector<Float64> prediction); //NOPMD

    public void train(List<Vector<Float64>> data) {
        int numPoints = historySize+1;

        // queue n data points into a list and create an iterator over all data
        Iterable<List<Vector<Float64>>> queued =
                new QueuedIterable<Vector<Float64>>(numPoints, data);
        // transform data into internal state
        Iterable<Pair<Vector<Float64>, Vector<Float64>>> intern =
                Iterables.transform(queued, new Function<List<Vector<Float64>>, Pair<Vector<Float64>, Vector<Float64>>>() {

            @Override
            public Pair<Vector<Float64>, Vector<Float64>> apply(List<Vector<Float64>> from) {
                int last = from.size() - 1;
                Vector<Float64> target = from.get(last); //NOPMD
                from.remove(last);
                return transformOriginalToInternState(from, target);
            }
        });
        Iterable<Vector<Float64>> state =
            Iterables.transform(intern, new FstTransformation<Vector<Float64>>());
        Iterable<Vector<Float64>> prediction =
            Iterables.transform(intern, new SndTransformation<Vector<Float64>>());

        Float64Matrix sM = Float64Matrix.valueOf(Lists.newArrayList(state));
        Float64Matrix pM = Float64Matrix.valueOf(Lists.newArrayList(prediction));


        params = sM.pseudoInverse().times(pM);
    }

    public Vector<Float64> predict(List<Vector<Float64>> states) { //NOPMD
        Preconditions.checkState(params!=null, "predictor not trained yet");

        Vector<Float64> s = transformOriginalToInternState(states); //NOPMD
        Vector<Float64> s_ = params.times(s); //NOPMD
        return transformInternToOriginalState(states, s_);
    }
}
