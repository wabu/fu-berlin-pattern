package de.berlin.fu.inf.pattern.classificators.kmeans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
	public static final int DEFAULT_ITERATIONS = 10;
	public static final int TERMINATE_BY_ITERATION = 1;
	public static final int TERMINATE_BY_APPROXIMATION = 2;
	
	/**
	 * number of expected classes
	 */
	private final int classes;
	private final List<KMeanCluster<V>> clusters;
	
	private int iterations = DEFAULT_ITERATIONS;
	private int terminationBehavior = TERMINATE_BY_ITERATION;
	
	public KMeanClassificator(int dimension) {
		this(2, dimension);
	}
	
	public KMeanClassificator(int classes, int dimension) {
		this.classes = classes;
		clusters = new ArrayList<KMeanCluster<V>>(classes);
		for(int i=0; i<classes; i++){
			clusters.add(new KMeanCluster<V>(dimension));
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
		clusters = new ArrayList<KMeanCluster<V>>(classes);
		
		for(V vec : vs) {
			clusters.add(new KMeanCluster<V>(vec));
		}
	}
	
	/**
	 * 
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Collection<V>[] classify(Collection<V> c) {
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
		for(int k=0; k<iterations; k++){
			for(KMeanCluster<V> cluster : clusters){
				cluster.refreshCenter();
				cluster.refreshCovMatrix();
				cluster.clear();
			}
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
		for( KMeanCluster<V> cluster : clusters ) {
			if( ( prob = cluster.probability(vec)) > bestProb ) {
				bestProb = prob;
				bestCluster = cluster;
			}
		}
		
		assert bestCluster != null;
		return bestCluster;
	}
	
}
