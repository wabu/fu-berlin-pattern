package de.berlin.fu.inf.pattern.impl.fisher;

import static de.berlin.fu.inf.pattern.util.jama.MatrixString.ms;

import java.util.Collection;

import org.apache.log4j.Logger;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import de.berlin.fu.inf.pattern.iface.DiscriminatingClassifier;
import de.berlin.fu.inf.pattern.util.jama.Vec;
import de.berlin.fu.inf.pattern.util.types.Vectorable;

public class FisherLinearDiscriminant<V extends Vectorable> implements DiscriminatingClassifier<V> {
	private Logger logger = Logger.getLogger(FisherLinearDiscriminant.class);
	private static int MIN_DIMENSION = 2;
	private static int ONE_COLUMN = 1;
	
	/** middle points of each set of vectorables */
	private Vec mPoint1, mPoint2;
	/** covariance */
	private Matrix sigma1, sigma2;

	/** real fisher discriminant ^^ */
	private Matrix omega;
	private Matrix sigmaB, sigmaW;
	
	// "_" marks projection to fisher's linear discriminant
	private double _sigma1, _sigma2;	// type double?
	private double _mPoint1, _mPoint2; // type double?
	
	private final int dimension;
	private Matrix sigma;
	
	/**
	 * 
	 * @param dimension the dimension of vector wie expect
	 */
	public FisherLinearDiscriminant(int dimension) {
		if( dimension < MIN_DIMENSION ) throw new IllegalArgumentException("dimension is to small");
		
		this.dimension = dimension;
	}

	public void train(Collection<V> c1, Collection<V> c2) {
		Matrix m;
		
		// determine middlePoint for each set of vectorable
		this.mPoint1 = this.calcMiddlePoint(c1);
		logger.trace("mPoint1 = " + mPoint1);
		this.mPoint2 = this.calcMiddlePoint(c2);
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
		
		this.sigma = sigmaW.inverse().times(sigmaB);
		
		EigenvalueDecomposition dec = sigma.eig();
		Matrix d = dec.getD();
		Matrix v = dec.getV();
		logger.trace("eigenvalues are "+ms(d));
		logger.trace("eigenvectors are "+ms(v));
		
		for(int i=0; i<v.getRowDimension(); i++){
			if(d.get(i, i) != 0) {
				// TODO das so richtig? spalten zeilen sonstwas vector?
				this.omega = new Vec(v.getArray()[i]).transpose();
			}
		}
		logger.trace("using omega "+ms(omega));
		
		Matrix _m1 = omega.times(mPoint1);
		Matrix _m2 = omega.times(mPoint2);
		
		Matrix _s1 = omega.times(sigma1).times(omega.transpose());
		Matrix _s2 = omega.times(sigma1).times(omega.transpose());
		
		assert _m1.getRowDimension() == 1 && _m1.getColumnDimension() == 1;
		assert _m2.getRowDimension() == 1 && _m2.getColumnDimension() == 1;
		assert _s1.getRowDimension() == 1 && _s1.getColumnDimension() == 1;
		assert _s2.getRowDimension() == 1 && _s2.getColumnDimension() == 1;
		
		_mPoint1 = _m1.get(0, 0);
		_mPoint2 = _m2.get(0, 0);
		
		_sigma1 = _s1.get(0, 0);
		_sigma2 = _s2.get(0, 0);
		
		logger.trace("linear class 1 around "+_mPoint1+" with sigma^2 "+_sigma1);
		logger.trace("linear class 2 around "+_mPoint2+" with sigma^2 "+_sigma2);
		
		// we are done...
	}
	
	public Integer classify(V data) {
		Vec vec = new Vec(data.getVectorData());
		double val = omega.times(vec).get(0, 0);
		
		//TODO scale?
		
		double p1 = getPropability(val, _mPoint1, _sigma1);
		double p2 = getPropability(val, _mPoint2, _sigma2);
		
		return (p1>p2) ? 0 : 1;
	};
	
	private double getPropability(double val, double m, double s) {
		// only need to compare, so exponent is enough
		return -(m-val)*(m-val)/s;
	}

	/**
	 * 
	 * @param c
	 * @return
	 */
	private Vec calcMiddlePoint(Collection<V> c) {
		if(c.size() == 0) {
			throw new IllegalArgumentException("size of collection must be >0");
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
		Matrix summed = new Matrix(this.dimension, this.dimension);
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