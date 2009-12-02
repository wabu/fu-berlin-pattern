/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.util.jama;

import de.berlin.fu.inf.pattern.util.types.Vectorable;
import java.util.ArrayList;
import java.util.List;
import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.vector.DenseVector;
import org.jscience.mathematics.vector.Vector;

/**
 *
 * @author wabu
 */
public class Vectors {
    public static Vector<Float64> valueOf(Vectorable vec) {
        List<Float64> fs = new ArrayList<Float64>(vec.getDimension());
        for(double d : vec.getVectorData()) {
            fs.add(Float64.valueOf(d));
        }
        return DenseVector.valueOf(fs);
    }
}
