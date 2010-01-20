/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.util.matrix;

import java.util.AbstractList;
import org.jscience.mathematics.vector.Float64Matrix;
import org.jscience.mathematics.vector.Float64Vector;

/**
 *
 * @author alex
 */
public class MatrixColumnList extends AbstractList<Float64Vector>{
    private final Float64Matrix matrix;

    public MatrixColumnList(Float64Matrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public Float64Vector get(int i) {
        return matrix.getColumn(i);
    }

    @Override
    public int size() {
        return matrix.getNumberOfColumns();
    }
}
