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
 * @author alex
 */
public class Float64Vector2VectorableTransform implements Function<Float64Vector, Vectorable> {

    public Vectorable apply(Float64Vector vec) {
        return Vectors.convert(vec);
    }


}
