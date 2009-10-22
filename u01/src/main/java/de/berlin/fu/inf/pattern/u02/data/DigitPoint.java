/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.u02.data;

import de.berlin.fu.inf.pattern.types.Messurable;

/**
 *
 * @author alex
 */
public class DigitPoint extends java.awt.Point implements Messurable<DigitPoint> {

    public DigitPoint(int arg0, int arg1) {
        super(arg0, arg1);
    }

    public double getDistance(DigitPoint other) {
        return this.distance(other);
    }

    @Override
    public String toString() {
        if(this != null)
            return "(" + this.x + ", " + this.y + ")";
        else
            return "NULL";
    }


}
