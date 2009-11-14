package de.berlin.fu.inf.pattern.tasks;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import Jama.Matrix;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;

import de.berlin.fu.inf.pattern.data.Entry;
import de.berlin.fu.inf.pattern.impl.kdtree.KDClassificator;
import de.berlin.fu.inf.pattern.util.data.DistributionGenerator;
import de.berlin.fu.inf.pattern.util.data.DoubleVector;
import de.berlin.fu.inf.pattern.util.jama.MatrixString;
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
	private final int runs = 7; 
	
	
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
			logger.info("run: " + run);
			// generate random distribution
			Matrix cov1 = gen.createCovariance(dim);
			Matrix cov2 = gen.createCovariance(dim);
			
			Vec middlePoint1 = gen.randomVector(dim);
			Vec middlePoint2 = gen.randomVector(dim);
			
			logger.trace("cov1: "+MatrixString.ms(cov1));
			logger.trace("cov2: "+MatrixString.ms(cov2));
			logger.trace("mid1: "+MatrixString.ms(middlePoint1));
			logger.trace("mid2: "+MatrixString.ms(middlePoint2));
			
			Collection<Vec> train1 = gen.createVectors(cov1, middlePoint1, elements);
			Collection<Vec> train2 = gen.createVectors(cov2, middlePoint2, elements);
			
			Collection<Vec> test1 = gen.createVectors(cov1, middlePoint1, elements);
			Collection<Vec> test2 = gen.createVectors(cov2, middlePoint2, elements);
		
			rate = runKNN(train1, train2, test1, test2);
			logger.info("KNN classified " + rate);
			classificationRateKNN += rate;
			
			
			// TODO extend KDClassigier for more then one 'k's
			//FisherLinearDiscriminant<Vec> fisher = new FisherLinearDiscriminant<Vec>(dim);
			//fisher.train(data1, data2);
			//fisher.classify(data)
			// test classification
			
			// fisher.classify(vec);
		
		} // end for(run)
		
		// TODO write rates to file
	}
	
	
	/**
	 * @param data1 - data representing class1
	 * @param data2 - data representing class2
	 * @param test1 - testing data for class 2
	 * @param test2 - testing data for class 1
	 * @return classification (success) rate
	 */
	private double runKNN(Collection<Vec> data1, Collection<Vec> data2, Collection<Vec> test1, Collection<Vec> test2 ) {
		
		/// init KD-Classifier 
		KDClassificator<DoubleVector, Integer> kdClassifier = new KDClassificator<DoubleVector, Integer>();
		
		// We need to transform our data to entry-sets
		Collection<Entry<DoubleVector, Integer>> classes = new ArrayList<Entry<DoubleVector,Integer>>(data1.size() + data2.size());
		classes.addAll(mapToVectorEntry(data1, 1));
		classes.addAll(mapToVectorEntry(data2, 2));
		// now train
		kdClassifier.train(classes);
		
		// classify
		int correctClassified = 0;
		
		logger.debug("elements: " + classes.size());
		for( Entry<DoubleVector, Integer> e : Iterables.concat(mapToVectorEntry(test1, 1), mapToVectorEntry(test2, 2))) {
			Integer klass = kdClassifier.classify(e.getData());
			logger.trace("found " + klass + " Vec is " + e.getClassification());
			
			if( klass.equals(e.getClassification()) ) {
				correctClassified++;
			}
		}
		logger.debug("classified: " + correctClassified);
		return correctClassified/(double) classes.size();
	}
	
	
	
	private Collection<Entry<DoubleVector, Integer>> mapToVectorEntry(Collection<Vec> data, final int i) {
		return Collections2.transform(data, new Function<Vec, Entry<DoubleVector, Integer>>() {
			public Entry<DoubleVector, Integer> apply(Vec arg0) {
				return new Entry<DoubleVector, Integer>(new DoubleVector(arg0.getVectorData()), i);
		}});
	}


	public double runFisher(Collection<Vec> data1, Collection<Vec> data2) {
		
		return 0;
	}
	
	public static void main(String[] argv) {
		new Main10().run();
	}
	
}
