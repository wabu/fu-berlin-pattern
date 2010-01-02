/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.ada;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import de.berlin.fu.inf.pattern.iface.Classifier;
import de.berlin.fu.inf.pattern.iface.DiscriminatingClassifier;
import de.berlin.fu.inf.pattern.util.Pair;
import de.berlin.fu.inf.pattern.util.collect.GuessedRightTransform;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.jscience.mathematics.vector.Float64Vector;

/**
 *
 * @author wabu
 */
public abstract class AbstractAda<D> implements DiscriminatingClassifier<D> {
    public static final int DEFUALT_COMITTEESIZE = 20;

    private final Logger log = Logger.getLogger(AbstractAda.class);

    private final List<Classifier<D, Integer>> comittee;
    private final List<Double> betas;
    private final Predicate<? super AbstractAda<D>> continueDecision;

    private final ThreadLocal<Pair<? extends Collection<? extends D>,
                                   ? extends Collection<? extends D>>>
            samples;


    public AbstractAda(final Predicate<? super AbstractAda<D>> continueDecision) {
        this.comittee = new ArrayList<Classifier<D, Integer>>();
        this.betas = new ArrayList<Double>();
        this.continueDecision = continueDecision;
        this.samples = new ThreadLocal<
                Pair<? extends Collection<? extends D>, 
                     ? extends Collection<? extends D>>>();
    }

    public AbstractAda(int comitteeSize) {
        this(new ComitteeSizePredicate(comitteeSize));
    }
    public AbstractAda() {
        this(DEFUALT_COMITTEESIZE);
    }

    abstract protected void beginTraingin();
    abstract public Classifier<D, Integer> getNextComitteeMemeber(Float64Vector weights);

    protected Collection<? extends D> getPositiveSamples(){
        if(samples.get() == null) {
            throw new IllegalStateException("samples not set, object not in training phase.");
        }
        return samples.get().fst;
    }
    protected Collection<? extends D> getNetativeSamples(){
        if(samples.get() == null) {
            throw new IllegalStateException("samples not set, object not in training phase.");
        }
        return samples.get().snd;
    }

    public int getComitteeSize() {
        return comittee.size();
    }

    public Integer classify(D data) {
        double sum = 0;
        Iterator<Double> beta = betas.iterator();
        for (Classifier<D, Integer> c : comittee) {
            if (c.classify(data) == 0) {
                sum += beta.next();
            } else {
                sum -= beta.next();
            }
        }
        return sum >= 0 ? 0 : 1;
    }

    public double train(Collection<? extends D> c1, Collection<? extends D> c2) {
        assert samples.get() == null;
        try {
            samples.set(new Pair<Collection<? extends D>, Collection<? extends D>>(c1,c2));

            beginTraingin();
            Float64Vector weights = getInitalWeights(c1.size() + c2.size());
            do {
                Classifier<D, Integer> next = getNextComitteeMemeber(weights);
                comittee.add(next);
                log.debug("next comittee member is " + next);
                weights = getUpdatedWeights(next, weights);

            } while (continueDecision.apply(this));
        } finally {
            samples.remove();
        }
        return Float.NaN;
    }

    protected Float64Vector getInitalWeights(int num) {
        double v = 1 / (double) num;
        double[] values = new double[num];
        Arrays.fill(values, v);
        return Float64Vector.valueOf(values);
    }

    protected Iterable<Boolean> rightMapping(Classifier<D, Integer> cf) {
        Collection<Boolean> b1 =
                Collections2.transform(getPositiveSamples(),
                new GuessedRightTransform<D, Integer>(cf, 0));
        Collection<Boolean> b2 =
                Collections2.transform(getNetativeSamples(),
                new GuessedRightTransform<D, Integer>(cf, 1));
        return Iterables.concat(b1, b2);
    }

    protected Float64Vector getUpdatedWeights(
            Classifier<D, Integer> next, Float64Vector weights) {
        Double err = calcErr(next, weights);
        double beta = 0.5 * Math.log((1 - err) / err);
        betas.add(beta);
        log.debug("his weighted error is " + err + ", beta is " + beta);
        double[] ws = new double[weights.getDimension()];
        int y = 0;
        for (boolean b : rightMapping(next)) {
            ws[y] =
                    weights.getValue(y) *
                    (b ? Math.exp(-beta) : Math.exp(beta));
            y++;
        }
        log.debug("weights recalucalted");
        return Float64Vector.valueOf(ws);
    }

    protected double calcErr(Classifier<D, Integer> c, Float64Vector weights) {
        int y = 0;
        double err = 0;
        double sum = 0;
        for (boolean b : rightMapping(c)) {
            double w = weights.getValue(y++);
            if (!b) {
                err += w;
            }
            sum += w;
        }
        return err / sum;
    }
}
