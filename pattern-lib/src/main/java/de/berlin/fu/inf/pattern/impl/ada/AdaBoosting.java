/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.ada;

import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import de.berlin.fu.inf.pattern.iface.Classifier;
import de.berlin.fu.inf.pattern.iface.DiscriminatingClassifier;
import de.berlin.fu.inf.pattern.util.collect.GuessedRightTransform;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Iterator;
import org.jscience.mathematics.vector.Float64Matrix;
import org.jscience.mathematics.vector.Float64Vector;

/**
 *
 * @author wabu
 */
public class AdaBoosting<D> implements DiscriminatingClassifier<D> {
    private final List<Classifier<D, Integer>> cloud;
    private final List<Classifier<D, Integer>> comittee;
    private final List<Double> betas;

    public AdaBoosting(List<Classifier<D, Integer>> cloud) {
        this.cloud = cloud;
        this.comittee = new ArrayList<Classifier<D, Integer>>();
        this.betas = new ArrayList<Double>();
    }

    protected Iterable<Boolean> rightMapping(
            Classifier<D,Integer> cf, Collection<D> c1, Collection<D> c2){
        Collection<Boolean> b1 =
                Collections2.transform(c1, new GuessedRightTransform<D, Integer>(cf, 0));
        Collection<Boolean> b2 =
                Collections2.transform(c2, new GuessedRightTransform<D, Integer>(cf, 1));
        return Iterables.concat(b1, b2);
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
            for(boolean b : rightMapping(c,c1,c2)) {
                values[x][y++] = b ? good : bad;
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

    protected double calcErr(Classifier<D,Integer> c, Collection<D> c1, Collection<D> c2,
            Float64Vector weights){
        int y=0;
        double err = 0, sum = 0;
        for(boolean b : rightMapping(c, c1, c2)) {
            double w = weights.getValue(y++);
            if(!b) {
                err += w;
            }
            sum += w;
        }
        return err/sum;
    }

    public double train(Collection<D> c1, Collection<D> c2) {
        Float64Matrix F = calcFMatrix(c1, c2);
        Float64Vector weights = getInitalWeights(c1.size() + c2.size());

        for(;;) {
            Classifier<D, Integer> next = selectNextComitteeMemeber(weights, F);
            comittee.add(next);

            double err = calcErr(next, c1, c2, weights);
            double beta = 0.5 * Math.log((1-err)/err);
            betas.add(beta);

            double ws[] = new double[weights.getDimension()];
            int y=0;
            for(boolean b : rightMapping(next, c1, c2)) {
                ws[y] = weights.getValue(y) *
                        (b ? Math.exp(-beta) : Math.exp(beta));
                y++;
            }
            weights = Float64Vector.valueOf(ws);

            //TODO call callback to know when to stop
            return err;
        }
    }


    public Integer classify(D data) {
        double sum = 0;
        Iterator<Double> beta = betas.iterator();
        for(Classifier<D,Integer> c : comittee) {
            if(c.classify(data) == 0) {
                sum+=beta.next();
            } else {
                sum-=beta.next();
            }
        }
        return sum >= 0 ? 0 : 1;
    }
}