/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.perzeptron;

import de.berlin.fu.inf.pattern.iface.Classifier;
import org.jscience.mathematics.structure.Field;
import org.jscience.mathematics.vector.DenseVector;

/**
 *
 * @author wabu
 */
public class PerzeptronSingleValueClassifier<F extends Field<F>> implements Classifier<F[], F> {
    protected final Perzeptron<F> tron;

    public PerzeptronSingleValueClassifier(Perzeptron<F> tron) {
        this.tron = tron;
        if(tron.getOutputSize() != 1) {
            throw new IllegalArgumentException(getClass()+" can only use perzeptrons with a single output value.");
        }
    }

    public F classify(F ... data) {
        return tron.apply(DenseVector.valueOf(data)).get(0);
    }
}
