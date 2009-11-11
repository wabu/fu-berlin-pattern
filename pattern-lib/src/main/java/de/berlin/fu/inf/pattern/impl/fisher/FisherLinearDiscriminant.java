package de.berlin.fu.inf.pattern.impl.fisher;

import static de.berlin.fu.inf.pattern.util.jama.MatrixString.ms;

import java.util.Collection;

import javax.annotation.Nullable;

import org.apache.log4j.Logger;

import Jama.Matrix;
import de.berlin.fu.inf.pattern.util.jama.Vec;
import de.berlin.fu.inf.pattern.util.types.Vectorable;

public class FisherLinearDiscriminant<V extends Vectorable> {
	private Logger logger = Logger.getLogger(FisherLinearDiscriminant.class);
	private static int MIN_DIMENSION = 2;
	private static int ONE_COLUMN = 1;
	
	/** middle points of each set of vectorables */
	private Vec mPoint1, mPoint2;
	/** covariance */
	private Matrix sigma1, sigma2;

	/** real fisher discriminant ^^ */
	private Vec omega;
	private Matrix sigmaB, sigmaW;
	
	// "_" marks projection to fisher's linear discriminant
	private Vec _sigma1, _sigma2;	// type double?
	private Vec _mPoint1, _mPoint2; // type double?
	
	private final int dimension;
	
	/**
	 * 
	 * @param dimension the dimension of vector wie expect
	 */
	public FisherLinearDiscriminant(int dimension) {
		if( dimension < MIN_DIMENSION ) throw new IllegalArgumentException("dimension is to small");
		
		this.dimension = dimension;
	}

	public void init(Collection<V> c1, Collection<V> c2) {
		Matrix m;
		
		// determine middlePoint for each set of vectorable
		this.mPoint1 = this.calcMiddlePoint(c1);
		logger.trace("mPoint1 = " + mPoint1);
		this._mPoint2 = this.calcMiddlePoint(c2);
		logger.trace("mPoint2 = " + mPoint2);
		
		// we can get sigmaBetween: (m1-m2)(m1-m2)^T
		m = mPoint1.minus(mPoint2);
		this.sigmaB = m.times(m.transpose());
		
		// determine each covariance sigma1 and sigma2
		this.sigma1 = this.calcCovariance(c1, this.mPoint1);
		logger.trace("sigma1 is " + ms(this.sigma1));
		
		this.sigma2 = this.calcCovariance(c2, this.mPoint2);
		logger.trace("sigma2 is " + ms(this.sigma2));
		
		// we get sigmaWithin by sigma1 + sigma2
		this.sigmaW = sigma1.plus(sigma2);
		
		// TODO we have to solve eigenvector problem do determine omega
		// Jama: EigenvalueDecomposition ???
		
		// TODO with omega we can calculate _mPoints (omega.transpose*middlePoint)
		
		// TODO with omega we can calculate mean squared error: omega.transpose x sigma x omega
		
		
		// we are done...
	}
	
	
	/**
	 * 
	 * @param c
	 * @return
	 */
	@Nullable
	private Vec calcMiddlePoint(Collection<V> c) {
		if(c.size() == 0) {
			return null;
		}
		Matrix summed = new Matrix(this.dimension, ONE_COLUMN);
		
		for(Vectorable vec : c) {
			summed = summed.plus(new Vec(vec));
		}
		Vec vec = new Vec(summed.times(1d/c.size()));
		// logger...?
		return vec;
	}
	
	
	/**
	 * Calculates the covariance matrix...
	 * 
	 * @param c - a collection of vectorable objects
	 * @param mPoint - middle point, which belongs to this collection
	 * @return covariance matrix
	 */
	private Matrix calcCovariance(Collection<V> c, Vec mPoint) {
		Matrix summed = new Matrix(this.dimension, ONE_COLUMN);
		for(Vectorable item : c) {
			Vec x = new Vec(item).minus(mPoint);
			summed = summed.plus(x.times(x.transpose()));
		}
		
		Matrix cov = summed.times(1d/c.size());
		if(logger.isTraceEnabled()){
			logger.trace("new covmatrix is "+ms(cov));
		}
		
		return cov;
		
	}
	

}
