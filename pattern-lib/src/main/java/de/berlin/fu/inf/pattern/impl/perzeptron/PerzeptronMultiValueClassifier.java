/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.perzeptron;

import de.berlin.fu.inf.pattern.iface.Classifier;
import org.jscience.mathematics.structure.Field;
import org.jscience.mathematics.vector.Vector;

/**
 *
 * @author wabu
 */
public class PerzeptronMultiValueClassifier<F extends Field<F>> implements Classifier<Vector<F>, Vector<F>> {
    protected final Perzeptron<F> tron;

    public PerzeptronMultiValueClassifier(Perzeptron<F> tron) {
        this.tron = tron;
    }

    public Vector<F> classify(Vector<F> data) {
        return tron.apply(data);
    }
}
