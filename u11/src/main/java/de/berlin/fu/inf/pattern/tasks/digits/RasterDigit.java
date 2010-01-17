/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks.digits;

import org.jscience.mathematics.vector.Float64Vector;

/**
 *
 * @author alex
 */
public class RasterDigit {
    private final int width;
    private final int height;
    private final Float64Vector vec;

    public RasterDigit(int width, int height, Float64Vector vec) {
        this.width = width;
        this.height = height;
        this.vec = vec;
    }
}
