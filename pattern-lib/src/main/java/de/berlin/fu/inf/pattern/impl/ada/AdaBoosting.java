/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.ada;

import com.google.common.base.Predicate;
import de.berlin.fu.inf.pattern.iface.Classifier;
import de.berlin.fu.inf.pattern.iface.DiscriminatingClassifier;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;
import org.jscience.mathematics.vector.Float64Matrix;
import org.jscience.mathematics.vector.Float64Vector;

/**
 * @author wabu
 */
public class AdaBoosting<D> extends AbstractAda<D> implements DiscriminatingClassifier<D> {
    private final Logger log = Logger.getLogger(AdaBoosting.class);

    private final List<Classifier<D, Integer>> cloud;
    protected final ThreadLocal<Float64Matrix> F;

    public AdaBoosting(List<Classifier<D, Integer>> cloud,
            final Predicate<? super AbstractAda<D>> continueDecision) {
        super(continueDecision);
        this.cloud = cloud;
        this.F = new ThreadLocal<Float64Matrix>();
    }

    public AdaBoosting(List<Classifier<D, Integer>> cloud, int comitteeSize) {
        this(cloud, new ComitteeSizePredicate(comitteeSize));
    }
    public AdaBoosting(List<Classifier<D, Integer>> cloud) {
        this(cloud, DEFUALT_COMITTEESIZE);
    }

    @Override
    protected void beginTraingin() {
        Collection<? extends D> c1 = getPositiveSamples();
        Collection<? extends D> c2 = getNetativeSamples();

        log.info("begining training");
        int num = c1.size() + c2.size();
        double bad = Math.E;
        double good = 1.0 / Math.E;
        double[][] values = new double[cloud.size()][num];
        for (int x = 0; x < cloud.size(); x++) {
            Classifier<D, Integer> c = cloud.get(x);
            int y = 0;
            for (boolean b : rightMapping(c)) {
                values[x][y++] = b ? good : bad;
            }
        }
        F.set(Float64Matrix.valueOf(values));
        log.info("calculated f matrix");
    }

    @Override
    public Classifier<D, Integer> getNextComitteeMemeber(Float64Vector weights) {
        Float64Vector valuation = F.get().times(weights);
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

    @Override
    protected void endTraingin() {
        F.remove();
    }
}
