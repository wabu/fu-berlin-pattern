/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.perzeptron;

import java.util.LinkedList;
import java.util.List;
import org.jscience.mathematics.structure.Field;
import org.jscience.mathematics.vector.Matrix;

/**
 *
 * @author wabu
 */
class GradiantUpdate<F extends Field<F>> {
    final List<Matrix<F>> grads = new LinkedList<Matrix<F>>();
    final F gamma;
    F error = null;

    public GradiantUpdate(F gamma) {
        this.gamma = gamma;
    }

    void setError(F error) {
        this.error = error;
    }

    void add(Matrix<F> layerGrad) {
        grads.add(layerGrad);
    }

    void applyTo(List<Matrix<F>> layers) {
        assert layers.size() == grads.size();
        for(int i=0; i<layers.size(); i++){
            layers.set(i, layers.get(i).minus(grads.get(i).times(gamma)));
        }
    }
}
