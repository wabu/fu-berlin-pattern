package de.berlin.fu.inf.pattern.tasks;

import java.io.FileNotFoundException;

import org.apache.log4j.Logger;

import de.berlin.fu.inf.pattern.impl.perzeptron.Perzeptron;
import de.berlin.fu.inf.pattern.impl.perzeptron.PerzeptronSingleValueClassifier;
import de.berlin.fu.inf.pattern.util.fun.Heaviside;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

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
    private int runs = 10000;
    
    private TruthTable truthTable = new TruthTable();
    private Map<Integer, Integer> boolFuncCounter = new HashMap<Integer, Integer>();
    
    public void run() throws FileNotFoundException, InterruptedException {

        for(int run=1; run<=runs; run++) {
	    	Perzeptron<Float64> tron = generatePerzeptron(2,3,1);
	        PerzeptronSingleValueClassifier<Float64> bool
	                = new PerzeptronSingleValueClassifier<Float64>(tron);
	        
	        int[] boolFunc = new int[4];
	        int counter = 0;
	        int tronResult, func;

	        for(int x1=0; x1<2; x1++){
	            for(int x2=0; x2<2; x2++){
	            	// we expect a result from either one or zero
	            	tronResult = bool.classify(Float64.valueOf(x1), Float64.valueOf(x2)).intValue();
	                logger.trace("tron("+x1+","+x2+")="+tronResult);
	                boolFunc[counter] = tronResult;
	                counter++;
	            }
	        }
	        // identify Boolean-Function
	        int code = boolFunc.hashCode();
	        func = this.truthTable.getBoolFuncType(boolFunc);
	        logger.trace("boolFunc is " + this.truthTable.boolFuncTypeToString(func));
	        this.updateStatistic(func);
        }
        
        
        for( Entry<Integer, Integer> e : this.boolFuncCounter.entrySet() ) {
        	float prob = e.getValue()/(float) runs;
        	
        	logger.info(e.getKey() + ": " +this.truthTable.boolFuncTypeToString(e.getKey()) + " = " + e.getValue() + "("+prob+")" );
        }
        
        
    }
    
    /**
     * increments counter for specified Boolean-Function, if not exists, a new
     * counter will be created
     * 
     * @param boolFuncType
     */
    private void updateStatistic(int boolFuncType) {
    	if( this.boolFuncCounter.containsKey(boolFuncType)) {
    		int counter = this.boolFuncCounter.get(boolFuncType);
    		counter++;
    		this.boolFuncCounter.put(boolFuncType, counter);
    	} else {
    		this.boolFuncCounter.put(boolFuncType, 1);	
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
