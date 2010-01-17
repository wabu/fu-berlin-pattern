/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks.gui;

/**
 *
 * @author alex
 */
public interface RasterModel {

    void addModelChangedListener(ModelChangedListener listener);
    void removeModelChangedListener(ModelChangedListener listener);

    /**
     * returns gray-scale value between 0.0 and 1.0 whereas postion (0, 0) is
     * the upper left corner
     *
     * @param x
     * @param y
     * @return value between 0.0 and 1.0
     */
    double getColor(int x, int y);
    /**
     * We need to know the width of any raster
     *
     * @return
     */
    int getCols();

    /**
     * we need to know the height of any raster
     * @return
     */
    int getRows();
}
