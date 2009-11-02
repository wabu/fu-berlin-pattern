package de.berlin.fu.inf.pattern.data.kmean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import Jama.Matrix;

import de.berlin.fu.inf.pattern.classificators.kmeans.Vectorable;

/**
 * 
 * 
 * @author alex
 */
public class KMeanCluster<V extends Vectorable<V>> {
	private Matrix midPointMatrix, covMatrix;
	private static int ROWS = 1;
	
	/**
	 * List of Vectorable objects, which are currently associated to
	 * this cluster
	 */
	private List<V> entries;
	private final int vectorSpace;
	
	// should contain: 1/det(2*Pi*covMatrix)
	private double coeff;
	
	/**
	 * creates a default cluster with expected vector space of three
	 */
	public KMeanCluster() {
		this(3);	
	}
	
	/**
	 * creates a cluster with given expected vector space
	 * 
	 * @param vectorSpace
	 */
	public KMeanCluster(int vectorSpace) {
		this.vectorSpace=vectorSpace;
		
		this.resetEntries();
		this.refreshCovMatrix();
	}
	
	public KMeanCluster(V vec ) {
		this(vec.getVectorData());		
	}
	
	public KMeanCluster(double[] midPoint) {
		this(midPoint.length);
		
		this.midPointMatrix = new Matrix(midPoint, vectorSpace);
	}
	
	/**
	 * 
	 * @param point
	 * @return 0.0 - 1.0
	 */
	public double probability(V entry) {
		double prob = Math.E;
		
		Matrix entryMatrix = new Matrix(entry.getVectorData(), vectorSpace);
		entryMatrix.minus(midPointMatrix);
		
		Math.pow(prob, entryMatrix.transpose().times(covMatrix.det()).times(entryMatrix).times(0.5d));
		
		
		return 0.0d;
	}
	
	
	public void refreshCenter() {
		if( entries.size() == 0 ) return;
		
		Matrix newMidPoint = new Matrix(this.vectorSpace, ROWS);
		
		for(Vectorable<V> vec : entries) {
			newMidPoint.plus(new Matrix(vec.getVectorData(),this.vectorSpace));
		}
		
		newMidPoint.times(1.0d/entries.size());
		
	}
	
	/**
	 * This method calculates the covariance matrix
	 * out of all associated vectorable objects
	 */
	public void refreshCovMatrix() {
		
		covMatrix = new Matrix(this.vectorSpace, ROWS);
		if( entries.size() == 0 ) {
			// fill covMatrix upto E
			// TODO default covMatrix	
			return;
		}
		
		
		for(Vectorable<V> item : entries) {
			Matrix matrix = new Matrix(item.getVectorData(), this.vectorSpace);
			
			// center vector to midpoint
			matrix = matrix.minus(midPointMatrix);
			
			covMatrix.plus(matrix.times(matrix.transpose()));
		}
		
		
		covMatrix.times(1.0d/entries.size());
		
		// also refresh often used coeff (due to probability calculation)
		coeff = 1.0d/covMatrix.times(2*Math.PI).det();
		
		
	}
	
	/**
	 * Adds new vectorable object to this cluster
	 * 
	 * @param vectorable
	 * @return true, if vectorable was successful added
	 */
	public boolean add(V vectorable) {
		if( vectorable ==  null ) 
			throw new IllegalArgumentException("vectorable null is not allowed");
		
		return this.entries.add(vectorable);
	}
	
	
	/** 
	 * @return an unmodifiable collection of current entry list
	 */
	public Collection<V> getEntries() {
		return Collections.unmodifiableList(this.entries);
	}
	
	
	/**
	 * resets the internal entry list cluster to a new empty list 
	 * 
	 * @return the former list of entries 
	 */
	public Collection<V> resetEntries() {
		List<V> formerEntries = this.entries;
		
		this.entries = new ArrayList<V>();
		return formerEntries;
	}
	
}
