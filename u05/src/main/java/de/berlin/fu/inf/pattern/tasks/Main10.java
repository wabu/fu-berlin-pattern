package de.berlin.fu.inf.pattern.tasks;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import de.berlin.fu.inf.pattern.data.Entry;
import de.berlin.fu.inf.pattern.iface.Classifier;
import de.berlin.fu.inf.pattern.impl.kdtree.KDClassificator;
import de.berlin.fu.inf.pattern.util.data.DistributionGenerator;
import de.berlin.fu.inf.pattern.util.data.DoubleVector;
import de.berlin.fu.inf.pattern.util.gen.Generator;
import de.berlin.fu.inf.pattern.util.gen.MultiNormalGenerator;
import de.berlin.fu.inf.pattern.util.jama.Vec;

/**
 * K-NN vs. Fishers Discriminant
 * 
 * 7 Runs for each 
 * 
 * 
 * @author covin, wabu
 */
public class Main10 {
	private Logger logger = Logger.getLogger(Main10.class);
	
	private final int[] N = {1,2,5,10,20,30,40,50,100,250,500,1000,2000,3000,4000,5000,7500,10000};
	
	private final DistributionGenerator gen = new DistributionGenerator();
	private final int maxDimension = 10;
	private final int runs = 50; 
	private final int tests = 500; 
	
	
	private String knnFile = "knnData.gp";
	private String fisherFile = "fisherData.gp";
	
	public Main10() {	
	}
	
	
	public void run() {
		
		// TODO open files
		
		for(int dimension = 2; dimension <= maxDimension; dimension++) {
			for( int number : N) {
				this.run(dimension, number);
			}
		}
	}
	
	/**
	 * concrete run for specified size/parameters
	 * @param dim
	 * @param elements
	 */
	private void run(int dim, int elements) {
		double classificationRateKNN = 0.0d;
		double classificationRateFisher = 0.0d;
		double rate;
		logger.info("======= DIM="+dim+ " ELEMTENTS=" + elements );
		
		for( int run = 1; run < this.runs; run++ ) {
			logger.debug("run: " + run);
			// generate random distribution
			Generator<DoubleVector> gen1 = new MultiNormalGenerator(dim);
			Generator<DoubleVector> gen2 = new MultiNormalGenerator(dim);
			
			Classifier<DoubleVector, Integer> knn = trainKNN(elements, gen1, gen2);
			rate = runTest(tests, knn, gen1, gen2);
			logger.debug("KNN classified " + rate);
			
			classificationRateKNN += rate;
			
			
			// TODO extend KDClassigier for more then one 'k's
			//FisherLinearDiscriminant<Vec> fisher = new FisherLinearDiscriminant<Vec>(dim);
			//fisher.train(data1, data2);
			//fisher.classify(data)
			// test classification
			
			// fisher.classify(vec);
		
		} // end for(run)
		logger.info("======= KNN-rate = "+classificationRateKNN/runs+"\t for "+elements+" in "+dim+"d");
		
		// TODO write rates to file
	}
	
	
	private Classifier<DoubleVector, Integer> trainKNN(int size, Generator<DoubleVector> gen1, Generator<DoubleVector> gen2) {
		/// init KD-Classifier 
		KDClassificator<DoubleVector, Integer> kdClassifier = new KDClassificator<DoubleVector, Integer>();
		
		// We need to transform our data to entry-sets
		Collection<Entry<DoubleVector, Integer>> classes = new ArrayList<Entry<DoubleVector,Integer>>(2*size);
		classes.addAll(gen1.generateEntries(1, size));
		classes.addAll(gen2.generateEntries(2, size));
		// now train
		kdClassifier.train(classes);
		return kdClassifier;
	}
	
	private Classifier<DoubleVector, Integer> trainFisher(int size, Generator<DoubleVector> gen1, Generator<DoubleVector> gen2) {
		return null;
	}
	
	public double runTest(int num, Classifier<DoubleVector, Integer> c, Generator<DoubleVector> ... gens) {
		int correct=0; 
		double total = num*gens.length;
		
		logger.debug("running "+num+" tests");
		for(int i=0; i<num; i++) {
			int k=1;
			for(Generator<DoubleVector> g : gens){
				if(k == c.classify(g.generate())){
					correct++;
				}
				k++;
			}
		}
		return correct/total;
	}
	
	public static void main(String[] argv) {
		new Main10().run();
	}
	
}
