/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.pca;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import de.berlin.fu.inf.pattern.util.matrix.Vectors;
import java.util.Collection;
import org.jscience.mathematics.vector.Float64Matrix;
import org.jscience.mathematics.vector.Float64Vector;

/**
 *
 * @author alex
 */
public class CovarianceMethodAnalysis extends PrincipleComponentAnalysis {

    public CovarianceMethodAnalysis(Collection<Float64Vector> floatVectors) {
        super(floatVectors);
    }

    @Override
    protected Float64Vector calculateMainComponent() {

        Float64Matrix cov = Vectors.covarianceOf(analysisData);
        // transform Float64Matrix to jama.Matrix
        
        int cols = cov.getNumberOfColumns();
        int rows = cov.getNumberOfRows();
        double data[][] = new double[rows][cols];
        for(int col = 0; col < cols; col++) {
            for(int row=0;row < rows; row++) {
                data[row][col] = cov.get(row, col).doubleValue();
            }
        }
        Matrix jamaMatrix = new Matrix(data);
        
        // solve eigenvalue problem
        EigenvalueDecomposition ed = new EigenvalueDecomposition(jamaMatrix);
        Matrix eigenValue  = ed.getD();
        Matrix eigenVector = ed.getV();
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
