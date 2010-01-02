package de.berlin.fu.inf.pattern.util.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import de.berlin.fu.inf.pattern.util.matrix.Vec;

import static de.berlin.fu.inf.pattern.util.matrix.MatrixString.ms;

import Jama.CholeskyDecomposition;
import Jama.Matrix;

/**
 * a class to generate covariance matrices and vectors belonging to them
 * 
 * @author covin
 */
public class DistributionGenerator {
	/** range specifies the maximum space of matrix */
	public final static int DEFAULT_RANGE = 10;
	
	private Logger logger = Logger.getLogger(DistributionGenerator.class);
	
	// range of random generated values
	private int range;
	
	private final Random rand;
	
	public DistributionGenerator() {
		this.rand = new Random();
        this.range = DEFAULT_RANGE;
	}
	
	/**
	 * TODO encapsulate extra datatype for covariance!?
	 * 
	 * @return a random generated covariance matrix
	 */
	public Matrix createCovariance(int dim) {
		Matrix covMatrix = new Matrix(dim, dim);
		
		double value;
		
		// build new covariance matrix until it passed the test
		do {
			for(int row = 0; row < dim; row++) {
				for( int col = row; col < dim; col++) {
					value = this.range*rand.nextDouble();
					covMatrix.set(row, col, value);
					// mirror
					if( row != col) 
						covMatrix.set(col, row, value);
				}
			}
		} while( !testCovarianceMatrix(covMatrix) );
		
		logger.debug("covMatrix generated: " + ms(covMatrix));
		return covMatrix;
	}
	
	/**
	 * @param covarianceMatrix
	 * @return
	 */
	public Collection<Vec> createVectors(Matrix covarianceMatrix, Vec middlePoint, int size) {
		int dim = covarianceMatrix.getRowDimension();
		// TODO verify parameter (dimensions)
		
		CholeskyDecomposition cDecomp = covarianceMatrix.chol();
		
		
		if( !cDecomp.isSPD() ) {
			throw new IllegalArgumentException("matrix is not symmetric and positiv definite");
		}
		
		Matrix lower = cDecomp.getL();
		if( logger.isTraceEnabled() ) {
			logger.trace("cholesky: cov=" + ms(covarianceMatrix) + " lower=" + ms(lower));
		}
		
		List<Vec> list = new ArrayList<Vec>(size);
		
		Vec vec;
		
		// generate [size] random multivariat normal distributed vectors
		for( int i = 0; i<size; i++ ) {
			Matrix m = lower.times(this.randomGaussVector(dim));
			// expecting just a vector
			assert m.getColumnDimension() == 1;
			
			vec = middlePoint.plus(m); 
			list.add(vec);
		}
		
		return list;
	}
	
	public Collection<Vec> createVectors(Matrix covarianceMatrix, int size) {
		return this.createVectors(covarianceMatrix,				// covMatrix
				new Vec(covarianceMatrix.getRowDimension()),	// zero Vector
				size);											// number of generated vectors
	}
	/**
	 * @param dimension of new vector
	 * @return a random normal distributed vector
	 */
	public Vec randomGaussVector(int dim) {
		
		double[] data = new double[dim];
		for( int i = 0; i < data.length; i++ ) {
			data[i] = rand.nextGaussian();
		}
		return new Vec(data);
	}
	
	/**
	 * @param dimension of new vector
	 * @return a random normal distributed vector
	 */
	public Vec randomVector(int dim) {
		
		double[] data = new double[dim];
		for( int i = 0; i < data.length; i++ ) {
			data[i] = randomDouble();
		}
		return new Vec(data);
	}
	
	/**
	 * @return random generated value in space (-range...0...+range)
	 */
	private double randomDouble() {
		return this.range*(2*rand.nextDouble()-1.0d);
	}
	
	/**
	 *  covariance matrix have to be linear independent
	 * 
	 *  independency is represented by full rank
	 *  
	 *  @return true if matrix passed the test
 	 */
	private boolean testCovarianceMatrix(Matrix m) {
		if( logger.isTraceEnabled() ) 
			logger.trace("rank of " + ms(m) + " is " + m.rank());
		return m.rank() == m.getRowDimension() && m.chol().isSPD();
	}
	
/* *********************************************************************
 * 
 * Getter and Setter
 * 
 */

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}
}
