/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.nmf;

import de.berlin.fu.inf.pattern.iface.MatrixFactorization;
import java.util.List;
import org.jscience.mathematics.vector.Float64Vector;

/**
 *
 * @author alex
 */
public class EMNegativeMatrixFactorization implements MatrixFactorization {
    
    public static final int DEFAULT_FEATURENUMBER = 10;
    private final int featureNumber;




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
        throw new UnsupportedOperationException("Not supported yet.");
    }


    /**
     *
     * @param data
     */
    public void learn(List<Float64Vector> data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
