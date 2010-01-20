/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks.digits;

import de.berlin.fu.inf.pattern.tasks.gui.AbstractRasterModel;
import de.berlin.fu.inf.pattern.tasks.gui.ModelChangedEvent;
import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.vector.Float64Vector;

/**
 *
 * @author alex
 */
public class VectorAsRasterModel extends AbstractRasterModel {

    private Float64Vector vector;
    private double maxVal = 0d;

    public VectorAsRasterModel() {}

    public VectorAsRasterModel(Float64Vector vector, int cols, int rows) {
        setData(vector, cols, rows);
    }

    public void setData(Float64Vector vector, int cols, int rows) {
        if( vector == null)
            throw new IllegalArgumentException("vector must not be null");
        if(vector.getDimension() != cols*rows || cols <0 || rows < 0)
            throw new IllegalArgumentException("vector does not correspond to given dimension");

        this.vector = vector;
        this.cols = cols;
        this.rows = rows;

        // normalize output
        this.maxVal = 0d;
        for(Float64 v : vector.asList()) {
            if(maxVal < v.doubleValue()) {
                maxVal = v.doubleValue();
            }
        }

        notifyModelChangedListeners(new ModelChangedEvent(this));
    }

    @Override
    public double getColor(int x, int y) {
        if( x<0 || x >= this.cols || y < 0 || y >= this.rows)
            throw new IndexOutOfBoundsException();
        if( vector != null )
            return vector.getValue(y*cols+x)/maxVal;
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
