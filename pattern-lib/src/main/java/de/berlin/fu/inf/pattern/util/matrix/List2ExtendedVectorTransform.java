/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.util.matrix;

import com.google.common.base.Function;
import de.berlin.fu.inf.pattern.util.types.Vectorable;
import org.jscience.mathematics.vector.Float64Vector;

/**
 *
 * @author wabu
 */
public class List2ExtendedVectorTransform implements Function<Vectorable, Float64Vector> {
    public Float64Vector apply(Vectorable from) {
        return Vectors.expendedOf(from);
    }
}
