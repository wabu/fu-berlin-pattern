package de.berlin.fu.inf.pattern.util.gen;

import Jama.Matrix;
import de.berlin.fu.inf.pattern.util.data.DoubleVector;
import de.berlin.fu.inf.pattern.util.jama.Vec;

public class MultiNormalGenerator extends AbstractGaussVectorGenerator {
	private Matrix mapping;
	private Vec transformation;

	public MultiNormalGenerator(int dim) {
		super(dim);
		
		this.transformation = genRandomVec();
		this.mapping = genRandomMatix();
	}

	private Vec genRandomVec() {
		double entries[] = new double[dim];
		for(int i=0; i<dim; i++){
			entries[i] = rnd.nextDouble();
		}
		return new Vec(entries);
	}

	private Matrix genRandomMatix() {
		Matrix map = new Matrix(dim, dim);
		for(int xy=0; xy<dim; xy++){
			map.set(xy, xy, rnd.nextDouble()+Double.MIN_VALUE);
		}
		for(int x=0; x<dim; x++){
			for(int y=x+1; y<dim; y++){
				map = map.times(genRandomRot(x,y));
			}
		}
		return map;
	}

	/**
	 * generates random rotation around a sub-space
	 * @param x
	 * @param y
	 * @return
	 */
	private Matrix genRandomRot(int x, int y) {
		double t = Math.PI * rnd.nextDouble();
		double c = Math.cos(t);
		double s = Math.sin(t);
		
		double entries[][] = new double[dim][dim];
		for(int xy=0; xy<dim; xy++){
			entries[xy][xy] = 1;
		}
		entries[x][x] = c;
		entries[y][y] = c;
		entries[x][y] = s;
		entries[y][x] = -s;
		
		return new Matrix(entries);
	}


	public DoubleVector generate() {
		Matrix vec = transformation.plus(mapping.times(new Vec(super.generate().getVectorData())));
		return new DoubleVector(vec.getRowPackedCopy());
	}
}
