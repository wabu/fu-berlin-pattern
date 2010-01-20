/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.nmf;

import de.berlin.fu.inf.pattern.iface.MatrixFactorization;
import de.berlin.fu.inf.pattern.util.matrix.MatrixColumnList;
import de.berlin.fu.inf.pattern.util.matrix.Vectors;
import java.util.List;
import org.apache.log4j.Logger;
import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.vector.DiagonalMatrix;
import org.jscience.mathematics.vector.Float64Matrix;
import org.jscience.mathematics.vector.Float64Vector;
import org.jscience.mathematics.vector.Matrix;

/**
 *
 * @author alex
 */
public class EMNegativeMatrixFactorization implements MatrixFactorization {
    private final Logger logger = Logger.getLogger(EMNegativeMatrixFactorization.class);

    public static final int DEFAULT_FEATURENUMBER = 10;
    private final int featureNumber;
    private Float64Matrix features;

    public EMNegativeMatrixFactorization() {
        this(DEFAULT_FEATURENUMBER);
    }

    public EMNegativeMatrixFactorization(int featureNumber) {
        this.featureNumber = featureNumber;
    }



    public Float64Vector decode(Float64Vector data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Float64Vector encode(Float64Vector data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Float64Vector> getFeatures() {
        return new MatrixColumnList(features);
    }
    /**
     *
     * @param data
     */
    public void learn(List<Float64Vector> data) {
        final int sampleSize = data.size();
        // create V from data
        final Matrix<Float64> v = Float64Matrix.valueOf(data).transpose();
        final int dataDimenstion = v.getNumberOfRows();
        logger.debug("start learning: [sampleSize="+sampleSize +"] [dataDimension="+ dataDimenstion+"]");
        // create W random
        Float64Matrix w = Vectors.randomMatrix(dataDimenstion, this.featureNumber);
        w = normColumns(w);
        // create H random
        Float64Matrix h = Vectors.randomMatrix(featureNumber, sampleSize);

        // EM Algo
        for( int i = 0; i<200; i++ )
        {
            // -> V/V'
            Float64Matrix vQuot = componentwiseQuot(v, w.times(h));
            // -> W korrigieren ( und normieren)
            w = this.componentwiseMult(w, vQuot.times(h.transpose()));
            w = this.normColumns(w);

            // -> oder H korrigieren
            vQuot = componentwiseQuot(v, w.times(h));
            h = this.componentwiseMult(h, w.transpose().times(vQuot));
        }

        this.features = w;
    }

    /**
     * @param m
     * @return
     */
    protected Float64Matrix normColumns(Float64Matrix m) {
        Matrix<Float64> vec = Float64Matrix.valueOf(
                Vectors.filledVector(m.getNumberOfRows(), 1.0d));

        // calculate sums of each row
        vec = vec.times(m);
        vec = vec.times(Float64.valueOf(1d/m.getNumberOfRows()));
        assert vec.getNumberOfRows() == 1;
        // create diagonal matrix
        Matrix<Float64> diag = DiagonalMatrix.valueOf(vec.getRow(0));
        return m.times(diag);
    }

    /**
     * 
     * @param x
     * @param y
     * @return X/Y componentwise     
     */
    protected Float64Matrix componentwiseQuot(Matrix<Float64> x, Matrix<Float64> y) {
        final int rows = x.getNumberOfRows();
        final int cols = x.getNumberOfColumns();

        assert y.getNumberOfRows() == rows;
        assert y.getNumberOfColumns() == cols;

        double[][] quotData = new double[rows][cols];

        for(int row = 0; row < rows; row++) {
            for( int col=0; col <cols; col++) {
                quotData[row][col] = x.get(row, col).doubleValue()/y.get(row, col).doubleValue();
            }
        }
        Float64Matrix foo = Float64Matrix.valueOf(quotData);
        assert foo.getNumberOfColumns() == cols;
        assert foo.getNumberOfRows() == rows;
        
        return foo;
    }
    
    /**
     * 
     * @param x
     * @param y
     * @return X*Y (componentwise)
     */
    protected Float64Matrix componentwiseMult(Matrix<Float64> x, Matrix<Float64> y) {
        final int rows = x.getNumberOfRows();
        final int cols = x.getNumberOfColumns();

        assert y.getNumberOfRows() == rows;
        assert y.getNumberOfColumns() == cols;

        double[][] quotData = new double[rows][cols];

        for(int row = 0; row < rows; row++) {
            for( int col=0; col <cols; col++) {
                quotData[row][col] = x.get(row, col).doubleValue()*y.get(row, col).doubleValue();
            }
        }
        Float64Matrix foo = Float64Matrix.valueOf(quotData);
        assert foo.getNumberOfColumns() == cols;
        assert foo.getNumberOfRows() == rows;
        
        return foo;
    }



}
