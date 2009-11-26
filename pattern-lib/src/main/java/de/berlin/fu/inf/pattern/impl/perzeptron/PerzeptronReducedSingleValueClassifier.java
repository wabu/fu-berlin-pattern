/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.perzeptron;

import de.berlin.fu.inf.pattern.util.fun.Function;
import org.jscience.mathematics.structure.Field;
import org.jscience.mathematics.vector.DenseVector;
import org.jscience.mathematics.vector.Vector;

public class PerzeptronReducedSingleValueClassifier<F extends Field<F>>
extends PerzeptronSingleValueClassifier<F> {
    private final Function<Vector<F>,Vector<F>> reduce;

    public PerzeptronReducedSingleValueClassifier(Perzeptron<F> tron) {
        this(tron, tron.getFunction());
    }

    public PerzeptronReducedSingleValueClassifier(Perzeptron<F> tron,
            Function<Vector<F>, Vector<F>> reduce) {
        super(tron);
        this.reduce = reduce;
    }

    @Override
    public F classify(F ... data) {
        return reduce.apply(super.tron.apply(DenseVector.valueOf(data))).get(0);
    }
}
