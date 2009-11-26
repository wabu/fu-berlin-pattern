/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.perzeptron;

import de.berlin.fu.inf.pattern.util.fun.Function;
import org.jscience.mathematics.structure.Field;
import org.jscience.mathematics.vector.Vector;

/**
 *
 * @author wabu
 */
public class PerzeptronReducedMultiValueClassifier<F extends Field<F>>
        extends PerzeptronMultiValueClassifier<F> {
    private final Function<Vector<F>,Vector<F>> reduce;

    public PerzeptronReducedMultiValueClassifier(Perzeptron<F> tron) {
        super(tron);
        this.reduce = tron.getFunction();
    }

    @Override
    public Vector<F> classify(Vector<F> data) {
        return reduce.apply(super.classify(data));
    }
}
