/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.ada;

import de.berlin.fu.inf.pattern.iface.Classifier;
import de.berlin.fu.inf.pattern.iface.DiscriminatingClassifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.jscience.mathematics.vector.Float64Matrix;
import org.jscience.mathematics.vector.Float64Vector;

/**
 *
 * @author wabu
 */
public class AdaBoosting<D> implements DiscriminatingClassifier<D> {
    private final List<Classifier<D, Integer>> cloud;
    private final List<Classifier<D, Integer>> comittee;

    public AdaBoosting(List<Classifier<D, Integer>> cloud) {
        this.cloud = cloud;
        this.comittee = new ArrayList<Classifier<D, Integer>>();
    }

    /**
     * Calculates a matrix containing the error for each classifier and each data
     * @param c1 possitive class
     * @param c2 negative class
     * @return
     */
    protected Float64Matrix calcFMatrix(Collection<D> c1, Collection<D> c2) {
        //TODO debug, fucking jama javadoc does not state which is row/collumn
        int num = c1.size() + c2.size();
        double bad = Math.E;
        double good = 1d/Math.E;

        double[][] values = new double[cloud.size()][num];
        for(int x=0; x<cloud.size(); x++){
            Classifier<D, Integer> c = cloud.get(x);
            int y=0;
            for(D d : c1) {
                int klass = 0;
                values[x][y] = c.classify(d)==klass ? good : bad;
                y++;
            }
            for(D d : c2) {
                int klass = 1;
                values[x][y] = c.classify(d)==klass ? good : bad;
                y++;
            }
        }
        Float64Matrix F = Float64Matrix.valueOf(values);
        return F;
    }

    /**
     * returns a column vector containing 1/n
     * @param num
     * @return
     */
    protected Float64Vector getInitalWeights(int num){
        double v = 1/(double)num;
        double[] values = new double[num];

        Arrays.fill(values, v);
        return Float64Vector.valueOf(values);
    }

    /**
     * 
     * @param weights column-vector containing the weights
     * @return
     */
    protected Classifier<D, Integer>selectNextComitteeMemeber(
            Float64Vector weights, Float64Matrix F){
        Float64Vector valuation = F.times(weights);
        double v, val = Double.NEGATIVE_INFINITY;
        int best = -1;
        for(int i=0; i<valuation.getDimension(); i++) {
            if((v = valuation.get(i).doubleValue()) > val) {
                best = i;
                val = v;
            }
        }
        return cloud.get(best);
    }

    public double train(Collection<D> c1, Collection<D> c2) {
        Float64Matrix F = calcFMatrix(c1, c2);
        Float64Vector weights = getInitalWeights(c1.size() + c2.size());

        for(;;) {
            Classifier<D, Integer> next = selectNextComitteeMemeber(weights, F);
            comittee.add(next);
            //TODO update weigts

            //TODO call callback to know when to stop
            break;
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }


    public Integer classify(D data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
