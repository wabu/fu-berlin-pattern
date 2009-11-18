package de.berlin.fu.inf.pattern.util.gen;

import Jama.Matrix;
import de.berlin.fu.inf.pattern.util.data.DoubleVector;
import de.berlin.fu.inf.pattern.util.jama.MatrixString;
import de.berlin.fu.inf.pattern.util.jama.Vec;

public class MultiNormalGenerator extends AbstractGaussVectorGenerator {
	private Matrix mapping;
	private Vec transformation;

    private final double dist;

	public MultiNormalGenerator(int dim, double dist) {
		super(dim);
		
        this.dist = dist;
		this.transformation = genRandomVec();
		this.mapping = genRandomMatix();
	}

    @Override
    public String toString() {
        return "dist[+"+transformation+" *"+MatrixString.ms(mapping)+"]";
    }


    public MultiNormalGenerator(int dim) {
        this(dim, 1d);
    }

	private Vec genRandomVec() {
		double entries[] = new double[dim];
		for(int i=0; i<dim; i++){
			entries[i] = (1d-2d*rnd.nextDouble())*dist;
		}
		return new Vec(entries);
	}

	private Matrix genRandomMatix() {
		Matrix scale = new Matrix(dim, dim);
		for(int xy=0; xy<dim; xy++){
			scale.set(xy, xy, rnd.nextDouble()+Double.MIN_VALUE);
		}
		Matrix rot = new Matrix(dim, dim);
		for(int xy=0; xy<dim; xy++){
			rot.set(xy, xy, 1d);
		}
		for(int x=0; x<dim; x++){
			for(int y=x+1; y<dim; y++){
				rot = rot.times(genRandomRot(x,y));
			}
		}
		return rot.times(scale);
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


    @Override
	public DoubleVector generate() {
		Matrix vec = transformation.plus(mapping.times(new Vec(super.generate().getVectorData())));
		return new DoubleVector(vec.getRowPackedCopy());
	}
}
