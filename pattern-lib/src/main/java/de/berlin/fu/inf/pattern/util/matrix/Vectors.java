/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.util.matrix;

import de.berlin.fu.inf.pattern.util.data.DoubleVector;
import de.berlin.fu.inf.pattern.util.types.Vectorable;
import java.util.Collection;
import java.util.Random;
import javax.annotation.Nullable;
import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.vector.Float64Matrix;
import org.jscience.mathematics.vector.Float64Vector;

/**
 *
 * @author wabu
 */
public final class Vectors {
    private static final Random rand = new Random();
    private Vectors() {}

    public static Float64Vector valueOf(double[] data) {
        return Float64Vector.valueOf(data);
    }

    public static Float64Vector valueOf(Vectorable vec) {
        return Float64Vector.valueOf(vec.getVectorData());
    }
    public static Float64Vector expendedOf(Vectorable vec) {
        double[] data = new double[vec.getDimension()+1];
        System.arraycopy(vec.getVectorData(), 0, data, 0, vec.getDimension());
        data[vec.getDimension()]=1;
        return Float64Vector.valueOf(data);
    }

    public static Vectorable convert(Float64Vector vec) {
        return new VectorableDeligate(vec);
    }

    /**
     *
     * @param vecs
     * @return center vector or null if collection is empty
     */
    public static Float64Vector centerOf(Iterable<Float64Vector> vecs) {
        int expectedDimension = 0;
        if( vecs.iterator().hasNext() ) {
            expectedDimension = vecs.iterator().next().getDimension();
        } else {
            throw new IllegalArgumentException("collection must not be empty");
        }

        int size = 0;
        Float64Vector centerVec = Float64Vector.valueOf(new double[expectedDimension]);
        for( Float64Vector vec : vecs ) {
            centerVec = vec.plus(centerVec);
            size++;
        }
        centerVec = centerVec.times(1d/size);
        return centerVec;
    }


    /**
     *
     * @param dimension
     * @return
     */
    @Nullable
    public static Float64Vector random(int dimension) {
        if( dimension <= 0) return null;

        //FIXME I don't think that this is a good random vector ... (wabu)
        double[] data = new double[dimension];
        // init random data
        for( int i = 0; i<dimension; i++) {
            data[i] = 1-2*rand.nextDouble();
        }
        // create and norm vector
        Float64Vector randVec = valueOf(data);
        randVec = randVec.times(1/randVec.normValue());

        return randVec;
    }

    public static Float64Matrix covarianceOf(Collection<Float64Vector> vectors) {
        if( vectors == null || vectors.size() == 0 )
            throw new IllegalArgumentException("vectors is null or empty");

        Float64Vector center = Vectors.centerOf(vectors);
        int expectedDimension = vectors.iterator().next().getDimension();

        Float64Matrix cov = Float64Matrix.valueOf(new double[expectedDimension][expectedDimension]);

        for (Float64Vector vec : vectors ) {
            Float64Matrix m = Float64Matrix.valueOf(vec.minus(center));
            cov = cov.plus(m.transpose().times(m));
        }
        cov = cov.times(Float64.valueOf(1d/vectors.size()));
        
        return cov;
    }
}
