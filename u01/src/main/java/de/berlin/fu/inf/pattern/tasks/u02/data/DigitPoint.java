/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks.u02.data;

import de.berlin.fu.inf.pattern.util.data.DoubleVector;
import de.berlin.fu.inf.pattern.util.types.Messurable;

/**
 *
 * @author alex
 */
public class DigitPoint extends DoubleVector {
	private static final long serialVersionUID = 1L;
	
	public static int MAX_Y = 100;
	public static int MAX_X = 100;
	
    public DigitPoint(int arg0, int arg1) {
        super(arg0, arg1);
    }

    public double getDistance(DigitPoint other) {
    	double distance = 0.0;
    	
    	for(int i=0; i<2; i++){
    		double val = getVectorData()[i]-other.getVectorData()[i];
    		distance += val*val;
    	}
    	return Math.sqrt(distance);
    }
    
 
    public int getX() {
        // TODO not secure
    	return (int) getVectorData()[0];
    }

    public int getY() {
    	return (int) getVectorData()[1];
    }
    
    @Override
    public String toString() {
        if(this != null)
            return "(" + this.getX() + ", " + this.getY() + ")";
        else
            return "NULL";
    }
}
