package de.berlin.fu.inf.pattern.util.gen;

import java.util.Random;

import de.berlin.fu.inf.pattern.util.data.DoubleVector;

public class AbstractGaussVectorGenerator extends AbstractGenerator<DoubleVector> {
	protected final int dim;
	protected final Random rnd = new Random();

	public AbstractGaussVectorGenerator(int dim) {
		this.dim = dim;
	}

	public DoubleVector generate() {
		double data[] = new double[dim];
		for(int i=0; i<dim; i++){
			data[i] = rnd.nextGaussian();
		}
		return new DoubleVector(data);
	}

}
