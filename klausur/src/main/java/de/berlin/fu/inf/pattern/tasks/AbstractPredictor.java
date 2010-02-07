/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.CheckForNull;
import org.jscience.mathematics.number.Float64;
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

    /** number of attributes we want to predict */
    protected final int numTargetAttribs;

    @CheckForNull
    private Matrix<Float64> params;

    public AbstractPredictor(int historySize, int numTargetAttribs) {
        this.historySize = historySize;
        this.numTargetAttribs = numTargetAttribs;
    }

    /**
     * transforms/combines a histsized list of original state vectors
     * it into a vector used internally in the linear aproximation
     * @param <code>historySize</code> original state vectors,
     *      asscending in time
     * @return vector to use in linear prediction
     */
    protected abstract Vector<Float64> transformOriginalToInternState(
            List<Vector<Float64>> data);

    /**
     * @param n count of attribute
     * @param hist list of original states of size <code>historySize</code>
     * @param state original target state
     * @return value of nth attribute for the given state
     */
    protected abstract Float64 transformOriginalToInternTarget(
            int n,
            List<Vector<Float64>> hist,
            Vector<Float64> state);

    /**
     * @param vector of <code>numTargetAttribs</code> attributes
     * @return state vector in original space
     */
    protected abstract Vector<Float64> transformInternToOriginalState(
            Vector<Float64> state);

    public void train(List<Vector<Float64>> data) {
        int numPoints = historySize+1;
        List<List<Vector<Float64>>> lists;
        for (int i = 0; i < numPoints; i++) {
            List<Vector<Float64>> l =
                    data.subList(i, data.size() - numPoints + i);
        }
    }

    public Vector<Float64> predict(List<Vector<Float64>> states) {
        Preconditions.checkState(params!=null, "predictor not trained yet");

        Vector<Float64> s = transformOriginalToInternState(states);
        Vector<Float64> s_ = params.times(s);
        return transformInternToOriginalState(s_);
    }
}
