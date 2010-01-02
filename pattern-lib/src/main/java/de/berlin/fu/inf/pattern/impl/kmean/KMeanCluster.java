package de.berlin.fu.inf.pattern.impl.kmean;

import static de.berlin.fu.inf.pattern.util.matrix.MatrixString.ms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import Jama.Matrix;
import de.berlin.fu.inf.pattern.util.matrix.Vec;
import de.berlin.fu.inf.pattern.util.types.Vectorable;

/**
 * 
 * 
 * @author alex
 */
public class KMeanCluster<V extends Vectorable> {
	private final Logger log = Logger.getLogger(KMeanCluster.class);
	
	private final int dimension;
	
	private static int ROWS = 1;
	
	private Vec median;
	private Matrix covariance;
	private Matrix inverseCovariance;
	/** contains 1/sqrt(det(2*Pi*covMatrix)) */
	private double coeff;
	
	/**
	 * List of Vectorable objects, which are currently associated to
	 * this cluster
	 */
	private final List<V> entries;

	/**
	 * creates a default cluster with expected vector space of three
	 */
	public KMeanCluster() {
		this(3);	
	}
	
	/**
	 * creates a cluster with given expected vector space
	 * 
	 * @param dimension
	 */
	public KMeanCluster(int dimension) {
		this(Matrix.random(dimension, 1).getArray()[0]);
	}
	
	public KMeanCluster(V vec) {
		this(vec.getVectorData());		
	}
	
	public KMeanCluster(double[] midPoint) {
		this.dimension=midPoint.length;
		this.median = new Vec(midPoint);
		this.covariance = this.inverseCovariance = Matrix.identity(dimension, dimension);
		this.entries = new ArrayList<V>();
		this.coeff = 1.0d/Math.sqrt(inverseCovariance.times(2*Math.PI).det());
	}
	
	public Vec getMedian() {
		return median;
	}
	public Matrix getCovariance() {
		return covariance;
	}
	
	/**
	 * p(x) = 1/(sqrt(det(2 pi S)) * exp(-1/2 * x' * conv * x)
	 * @param point
	 * @return 0.0 - 1.0
	 */
	public double probability(V entry) {
		if(log.isTraceEnabled()){
			log.trace("calculating prop for "+entry);
			log.trace("covmatrix is "+ms(covariance));
			log.trace("median is "+ms(median));
		}
		Vec x = new Vec(entry).minus(median);
		Matrix exp = x.transpose().times(inverseCovariance).times(x);
		
		if(log.isTraceEnabled()){
			log.trace("exponent is "+ms(exp));
		}
		assert exp.getColumnDimension() == 1;
		assert exp.getRowDimension() == 1;
		
		double prop = coeff * Math.exp(-0.5*exp.get(0, 0));
		if(log.isTraceEnabled()){
			log.trace("returning "+prop);
		}
		return prop;
	}
	
	
	public void refreshCenter() {
		if(entries.size() == 0) {
			return;
		}
		
		Matrix summed = new Matrix(this.dimension, ROWS);
		
		for(Vectorable vec : entries) {
			summed = summed.plus(new Vec(vec));
		}
		
		median = new Vec(summed.times(1d/entries.size()));
		
		if(log.isTraceEnabled()){
			log.trace("new median is "+ms(median));
		}
	}
	
	/**
	 * This method calculates the covariance matrix
	 * out of all associated vectorable objects
	 */
	public void refreshCovMatrix() {
		if(entries.size() == 0){
			return;
		}
		
		Matrix summed = new Matrix(this.dimension, ROWS);
		for(Vectorable item : entries) {
			Vec x = new Vec(item).minus(median);
			summed = summed.plus(x.times(x.transpose()));
		}
		
		covariance = summed.times(1d/entries.size());
		if(log.isTraceEnabled()){
			log.trace("new covmatrix is "+ms(covariance));
		}
		coeff = 1.0d/Math.sqrt(covariance.times(2*Math.PI).det());
		if(log.isTraceEnabled()){
			log.trace("new coeff is "+coeff);
		}
		inverseCovariance = covariance.inverse();
	}
	
	/**
	 * Adds new vectorable object to this cluster
	 * 
	 * @param vectorable
	 * @return true, if vectorable was successful added
	 */
	public boolean add(V vectorable) {
		return this.entries.add(vectorable);
	}
	
	
	/** 
	 * @return an unmodifiable collection of current entry list
	 */
	public Collection<V> getEntries() {
		return Collections.unmodifiableList(this.entries);
	}
	
	public void clear() {
		this.entries.clear();
	}
	
	
	@Override
	public String toString() {
		return "cluster arround "+ms(median) + " with covariance "+ms(covariance);
	}

	public int size() {
		return entries.size();
	}
}
