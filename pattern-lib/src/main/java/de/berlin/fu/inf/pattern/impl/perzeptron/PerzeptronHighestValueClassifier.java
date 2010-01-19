/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.perzeptron;

import com.google.common.base.Function;
import de.berlin.fu.inf.pattern.data.Entry;
import de.berlin.fu.inf.pattern.iface.SupervisedClassifier;
import de.berlin.fu.inf.pattern.util.Threads;
import de.berlin.fu.inf.pattern.util.matrix.Vectors;
import de.berlin.fu.inf.pattern.util.types.Vectorable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.vector.DenseVector;
import org.jscience.mathematics.vector.SparseVector;
import org.jscience.mathematics.vector.Vector;

/**
 *
 * @author wabu
 */
public class PerzeptronHighestValueClassifier
        implements SupervisedClassifier<Vectorable, Integer> {

    protected final BackProptron<Float64> tron;
    private final int dim;

    public PerzeptronHighestValueClassifier(BackProptron<Float64> tron) {
        this.tron = tron;
        this.dim = tron.getOutputSize();
    }

    public Integer classify(Vectorable data) {
        List<Float64> fs = new ArrayList<Float64>(data.getDimension());
        for(double d : data.getVectorData()) {
            fs.add(Float64.valueOf(d));
        }
        Vector<Float64> out = tron.apply(DenseVector.valueOf(fs));
        assert out.getDimension() == dim;

        int best=-1;
        double v, val = Double.NEGATIVE_INFINITY;
        for(int i=0; i<dim; i++) {
            if((v=out.get(i).doubleValue()) > val) {
                best = i;
                val = v;
            }
        }
        return best;
    }

    public double train(Collection<Entry<Vectorable, Integer>> trainingData) {
        try {
            return tron.trainOffline(Threads.doParralell(trainingData, new Function<Entry<Vectorable, Integer>, Entry<? extends Vector<Float64>, ? extends Vector<Float64>>>() {
                public Entry<? extends Vector<Float64>, ? extends Vector<Float64>> apply(Entry<Vectorable, Integer> entry) {
                    // map data to (0 0 0 ... 0 1 0 ... 0) vectors
                    Vector<Float64> in =
                            Vectors.valueOf(entry.getData());
                    Vector<Float64> target =
                            SparseVector.valueOf(entry.getClassification(), Float64.ONE, dim);
                    return new Entry<Vector<Float64>, Vector<Float64>>(in, target);
                }
            })).doubleValue()/(double)trainingData.size();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

}
