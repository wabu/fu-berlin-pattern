package de.berlin.fu.inf.pattern.impl.kmean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import de.berlin.fu.inf.pattern.iface.SelflearningClassifier;
import de.berlin.fu.inf.pattern.util.types.Vectorable;

/**
 * 
 * 
 * @author alex, wabu
 *
 * @param <V>
 */
public class KMeanClassifier<V extends Vectorable> implements SelflearningClassifier<V, Integer>{
	private final Logger log = Logger.getLogger(KMeanClassifier.class); 
	
	public static final int DEFAULT_ITERATIONS = 5;
	public static final int TERMINATE_BY_ITERATION = 1;
	public static final int TERMINATE_BY_APPROXIMATION = 2;
	
	/**
	 * number of expected classes
	 */
	private final int classes;
	private final List<KMeanCluster<V>> clusters;
	private final List<Double> scales;
	
	private int iterations = DEFAULT_ITERATIONS;
	//TODO terminate by stability: private int terminationBehavior = TERMINATE_BY_ITERATION;
	
	public KMeanClassifier(int dimension) {
		this(2, dimension);
	}
	
	public KMeanClassifier(int classes, int dimension) {
		this.classes = classes;
		clusters = new ArrayList<KMeanCluster<V>>(classes);
		scales = new ArrayList<Double>();
		for(int i=0; i<classes; i++){
			clusters.add(new KMeanCluster<V>(dimension));
			scales.add(0.5);
		}
	}
	
	/**
	 * Constructs a classifier with some Vectorables to
	 * control classification behavior. There should be at least
	 * two vectorables, to get classifier working
	 * 
	 * @param vs
	 */
	public KMeanClassifier(V...vs) {
		
		this.classes = vs.length;
		scales = new ArrayList<Double>();
		clusters = new ArrayList<KMeanCluster<V>>(classes);
		
		for(V vec : vs) {
			clusters.add(new KMeanCluster<V>(vec));
			scales.add(0.5);
		}
	}
	
	/**
	 * 
	 * @param c
	 * @return
	 */
	public void train(Collection<V> c) {
		log.debug("anaylsing data");
		runEM(c);
	}
	
	// TODO abstraction
	private void runEM(Collection<V> data) {
		int i=0;
		for(KMeanCluster<V> cluster : clusters){
			log.debug(" starting with "+i+++" "+cluster);
		}
		for(int k=0; k<iterations; k++){
			log.debug("iteration "+k+" ...");
			
			log.debug(" calculating expectation");
			for(V v : data){
				bestCluster(v).add(v);
			}
			i=0;
			for(KMeanCluster<V> cluster : clusters){
				log.debug(" new size of "+i+++" is "+cluster.size());
			}
			
			i=0;
			for(KMeanCluster<V> cluster : clusters){
				log.debug(" remaximising "+i);
				scales.set(i, (double)cluster.size()/(double)data.size());
				cluster.refreshCenter();
				cluster.refreshCovMatrix();
				cluster.clear();
				log.debug(" got "+cluster+" scaled with "+scales.get(i));
				i++;
			}
		}
	}

	private KMeanCluster<V> bestCluster(V vec) {
		double bestProb = Double.NEGATIVE_INFINITY;
		double prob;
		KMeanCluster<V> bestCluster = null;
		
		/**
		 * calculate probability for any cluster
		 */
		int all = 0;
		for( KMeanCluster<V> cluster : clusters ) {
			all += cluster.size();
		}
		int i = 0;
		for( KMeanCluster<V> cluster : clusters ) {
			double scale = scales.get(i++);
			
			if( ( prob = cluster.probability(vec)*scale) > bestProb ) {
				bestProb = prob;
				bestCluster = cluster;
			}
		}
		
		assert (bestCluster == null) == (clusters.isEmpty());
		return bestCluster;
	}
	
	public Integer classify(V vec){
		int best=-1, all = 0;
		double bestProb = Double.NEGATIVE_INFINITY, prob;
		
		for( KMeanCluster<V> cluster : clusters ) {
			all += cluster.size();
		}
		int i = 0;
		for( KMeanCluster<V> cluster : clusters ) {
			double scale = scales.get(i);
			if( ( prob = cluster.probability(vec)*scale) > bestProb ) {
				bestProb = prob;
				best = i;
				i++;
			}
		}
		assert (best == -1) == (clusters.isEmpty());
		return best;
	}
}
