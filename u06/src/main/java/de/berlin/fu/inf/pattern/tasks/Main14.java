package de.berlin.fu.inf.pattern.tasks;

import java.io.FileNotFoundException;

import org.apache.log4j.Logger;

import de.berlin.fu.inf.pattern.impl.perzeptron.Perzeptron;
import de.berlin.fu.inf.pattern.impl.perzeptron.PerzeptronSingleValueClassifier;
import de.berlin.fu.inf.pattern.util.fun.Heaviside;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.vector.DenseMatrix;
import org.jscience.mathematics.vector.Matrix;

/**
 * K-NN vs. Fishers Discriminant
 * 
 * 7 Runs for each 
 * 
 * 
 * @author covin, wabu
 */
public class Main14 {
    private Logger logger = Logger.getLogger(Main14.class);
    private Random rnd = new Random();

    public void run() throws FileNotFoundException, InterruptedException {
        Perzeptron<Float64> tron = generatePerzeptron(2,3,1);
        PerzeptronSingleValueClassifier<Float64> bool
                = new PerzeptronSingleValueClassifier<Float64>(tron);

        for(int i=0; i<2; i++){
            for(int j=0; j<2; j++){
                logger.info("tron("+i+","+j+")="+bool.classify(Float64.valueOf(i), Float64.valueOf(j)));
            }
        }
    }

    /**
     * 
     * @param sizeSpec contains inputSize, layerSize*, outputSize
     * @return
     */
    public Perzeptron<Float64> generatePerzeptron(int ... sizeSpec){
        List<Matrix<Float64>> layers = 
                new ArrayList<Matrix<Float64>>(sizeSpec.length - 1);
        for (int i = 1; i < sizeSpec.length; i++) {
            int inSize = sizeSpec[i-1] + 1;
            int outSize = sizeSpec[i];

            Float64[][] weights = new Float64[outSize][inSize];
            for(int x=0; x<outSize; x++) {
                for(int y=0; y<inSize; y++) {
                    weights[x][y]= Float64.valueOf(rnd.nextGaussian());
                }
            }
            layers.add(DenseMatrix.valueOf(weights));
            logger.debug("\n"+DenseMatrix.valueOf(weights));
        }
        return new Perzeptron<Float64>(
                new Heaviside<Float64>(Float64.ZERO, Float64.ONE), Float64.ONE, layers);
    }

    public static void main(String[] argv) throws FileNotFoundException, InterruptedException {
        new Main14().run();
    }
}
