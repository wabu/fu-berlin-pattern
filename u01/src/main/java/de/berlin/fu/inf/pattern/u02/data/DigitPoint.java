/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.u02.data;

import de.berlin.fu.inf.pattern.util.types.Messurable;

/**
 *
 * @author alex
 */
public class DigitPoint extends java.awt.Point implements Messurable<DigitPoint> {
	private static final long serialVersionUID = 1L;
	
	public static int MAX_Y = 100;
	
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
