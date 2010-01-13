/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.pca;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import de.berlin.fu.inf.pattern.util.matrix.Vectors;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import org.apache.log4j.Logger;
import org.jscience.mathematics.vector.Float64Matrix;
import org.jscience.mathematics.vector.Float64Vector;
import static de.berlin.fu.inf.pattern.util.matrix.MatrixString.ms;

/**
 *
 * @author alex
 */
public class CovarianceMethodAnalysis implements PrincipleComponentAnalysis {
    private static String NAME = "CovAnalyser";
    private static Logger logger = Logger.getLogger(CovarianceMethodAnalysis.class);

    public CovarianceMethodAnalysis() {

    }

    public Collection<Float64Vector> principleComponents(Collection<Float64Vector> inputData) {

        Float64Matrix cov = Vectors.covarianceOf(inputData);
        logger.debug("cov is " + cov);
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
        Matrix d  = ed.getD();
        Matrix v = ed.getV();
        logger.trace("eigenvalues are "+ms(d));
	logger.trace("eigenvectors are "+ms(v));


        double lambda = 0.0d;
        Map<Double, Float64Vector> components = new TreeMap<Double, Float64Vector>();
        for(int i=0; i<v.getRowDimension(); i++){
            if(d.get(i, i) > 0.0d) {
                lambda = d.get(i, i);
                components.put(
                        lambda, 
                        Vectors.valueOf(v.transpose().getArray()[i]));
            }
	}
        logger.debug("found components=" + components);

        return components.values();
    }

    @Override
    public String toString() {
        return NAME;
    }
}
