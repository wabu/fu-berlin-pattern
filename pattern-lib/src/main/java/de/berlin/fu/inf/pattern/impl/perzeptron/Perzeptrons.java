/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.perzeptron;

import de.berlin.fu.inf.pattern.util.fun.Sigmoid;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.vector.DenseMatrix;
import org.jscience.mathematics.vector.Float64Matrix;
import org.jscience.mathematics.vector.Matrix;

/**
 *
 * @author wabu
 */
public class Perzeptrons {
    private static final Random rnd = new Random();

    /**
     * @param sizeSpec contains inputSize, layerSize*, outputSize
     * @return
     */
    public static BackProptron<Float64> generatePerzeptron(int ... sizeSpec){
        List<Matrix<Float64>> layers =
                new ArrayList<Matrix<Float64>>(sizeSpec.length - 1);
        for (int i = 1; i < sizeSpec.length; i++) {
            int inSize = sizeSpec[i-1] + 1;
            int outSize = sizeSpec[i];

            double[][] weights = new double[outSize][inSize];
            for(int x=0; x<outSize; x++) {
                for(int y=0; y<inSize; y++) {
                    weights[x][y]= rnd.nextDouble()*2.0d-1.0d;
                }
            }
            layers.add(Float64Matrix.valueOf(weights));
        }
        return new BackProptron<Float64>(
                new Sigmoid(), Float64.valueOf(2d), Float64.ZERO, Float64.ONE, layers);
    }
}
