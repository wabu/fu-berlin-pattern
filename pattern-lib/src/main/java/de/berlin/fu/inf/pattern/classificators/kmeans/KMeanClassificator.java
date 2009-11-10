package de.berlin.fu.inf.pattern.classificators.kmeans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import de.berlin.fu.inf.pattern.data.kmean.KMeanCluster;

/**
 * 
 * 
 * @author alex, wabu
 *
 * @param <V>
 */
public class KMeanClassificator<V extends Vectorable> {
	private final Logger log = Logger.getLogger(KMeanClassificator.class); 
	
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
	private int terminationBehavior = TERMINATE_BY_ITERATION;
	
	public KMeanClassificator(int dimension) {
		this(2, dimension);
	}
	
	public KMeanClassificator(int classes, int dimension) {
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
	public KMeanClassificator(V...vs) {
		
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
	@SuppressWarnings("unchecked")
	public Collection<V>[] classify(Collection<V> c) {
		log.debug("anaylsing data");
		for (V v : c) {
			KMeanCluster<V> cluster = this.bestCluster(v);
			cluster.add(v);
		}
		
		runEM(c);
		
		return (Collection<V>[]) Collections2.transform(clusters, new Function<KMeanCluster<V>, Collection<V>>() {
			public Collection<V> apply(KMeanCluster<V> cluster) {
				return cluster.getEntries();
			}
		}).toArray(new Collection<?>[clusters.size()]);
	}
	
	// TODO abstraction
	private void runEM(Collection<V> data) {
		int i=0;
		for(KMeanCluster<V> cluster : clusters){
			log.debug(" starting with "+i+++" "+cluster);
		}
		for(int k=0; k<iterations; k++){
			i=0;
			for(KMeanCluster<V> cluster : clusters){
				log.debug(" new size of "+i+++" is "+cluster.size());
			}
			log.debug("iteration "+k+" ...");
			
			i=0;
			for(KMeanCluster<V> cluster : clusters){
				log.debug(" remaximising "+i);
				scales.set(i, (double)cluster.size()/(double)data.size());
				cluster.refreshCenter();
				cluster.refreshCovMatrix();
				cluster.clear();
				log.debug(" got "+cluster);
				log.debug(" scaled with "+scales.get(i));
				i++;
			}
			log.debug(" recalculating expectation");
			for(V v : data){
				bestCluster(v).add(v);
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
		
		assert bestCluster != null;
		return bestCluster;
	}
	
}
