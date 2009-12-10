/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.ada;

import Jama.Matrix;
import de.berlin.fu.inf.pattern.iface.Classifier;
import de.berlin.fu.inf.pattern.iface.DiscriminatingClassifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
     * @param c1 0 class
     * @param c2 1 class
     * @return
     */
    protected Matrix calcFMatrix(Collection<D> c1, Collection<D> c2) {
        //TODO debug, fucking jama javadoc does not state which is row/collumn
        int num = c1.size() + c2.size();
        double bad = Math.E;
        double good = 1d/Math.E;

        Matrix F = new Matrix(num, cloud.size());
        for(int column=0; column<cloud.size(); column++){
            Classifier<D, Integer> c = cloud.get(column);
            int row=0;
            for(D d : c1) {
                int klass = 0;
                F.set(row, column, c.classify(d)==klass ? good : bad);
                row++;
            }
            for(D d : c2) {
                int klass = 1;
                F.set(row, column, c.classify(d)==klass ? good : bad);
                row++;
            }
        }
        return F;
    }

    /**
     * returns a column vector containing 1/n
     * @param num
     * @return
     */
    protected Matrix getInitalWeights(int num){
        double v = 1/(double)num;
        return new Matrix(1, num, v);
    }

    /**
     * 
     * @param weights column-vector containing the weights
     * @return
     */
    protected Classifier<D, Integer>selectNextComitteeMemeber(Matrix weights, Matrix F){
        Matrix valuation = weights.times(F);
        double v, val = Double.NEGATIVE_INFINITY;
        int best = -1;
        for(int i=0; i<valuation.getRowDimension(); i++) {
            if((v = valuation.get(i, 0)) > val) {
                best = i;
                val = v;
            }
        }
        return cloud.get(best);
    }

    public double train(Collection<D> c1, Collection<D> c2) {
        Matrix F = calcFMatrix(c1, c2);
        Matrix weights = getInitalWeights(c1.size() + c2.size());

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
