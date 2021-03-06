/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks.digits;

import de.berlin.fu.inf.pattern.tasks.gui.AbstractRasterModel;
import de.berlin.fu.inf.pattern.tasks.gui.ModelChangedEvent;
import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.vector.Float64Vector;
import org.jscience.mathematics.vector.Vector;

/**
 *
 * @author alex
 */
public class VectorAsRasterModel extends AbstractRasterModel {

    private Vector<Float64> vector;
    private double maxVal = 0d;

    public VectorAsRasterModel() {}

    public VectorAsRasterModel(Float64Vector vector, int cols, int rows) {
        setData(vector, cols, rows);
    }

    public void setData(Vector<Float64> vector, int cols, int rows) {
        if( vector == null)
            throw new IllegalArgumentException("vector must not be null");
        if(vector.getDimension() != cols*rows || cols <0 || rows < 0)
            throw new IllegalArgumentException("vector does not correspond to given dimension");

        this.vector = vector;
        this.cols = cols;
        this.rows = rows;

        // normalize output
        this.maxVal = 0d;
        for(int i=0; i<vector.getDimension(); i++) {
            double v = vector.get(i).doubleValue();
            if(maxVal < v) {
                maxVal = v;
            }
        }

        notifyModelChangedListeners(new ModelChangedEvent(this));
    }

    @Override
    public double getColor(int x, int y) {
        if( x<0 || x >= this.cols || y < 0 || y >= this.rows)
            throw new IndexOutOfBoundsException();
        if( vector != null )
            return vector.get(y*cols+x).doubleValue()/maxVal;
        else
            return Double.NaN;
    }

    public void setData(RasterDigit digit) {
        this.setData(digit.getVec(), digit.getWidth(), digit.getHeight());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append(VectorAsRasterModel.class.getSimpleName());
        builder.append(" raster=");
        builder.append(getCols());
        builder.append("x");
        builder.append(getRows());
        builder.append("]");

        return builder.toString();
    }


}
