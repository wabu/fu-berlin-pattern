/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.u02.data;

import de.berlin.fu.inf.pattern.types.Messurable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Alexander Münn
 */
public class Digit implements Messurable<Digit>{
    public static final int POINT_NUMBER = 8;

    private List<DigitPoint> pointPath;
    private int group;

    public Digit() {
        this.pointPath = new LinkedList<DigitPoint>();
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    /**
     * @return an unmodifiableList 
     */
    public List<DigitPoint> getPoints() {
        return Collections.unmodifiableList(pointPath);
    }

    /**
     *
     * @param index look for index'th point on the pointPath
     * @return null if no such point exists
     */
    public DigitPoint getPoint(int index) {
        if( index < this.pointPath.size())
            return this.pointPath.get(index);
        return null;
    }

    /**
     *
     */
    public boolean addPoint(DigitPoint point) {
        return this.pointPath.add(point);
    }

    public double getDistance(Digit other) {
        double distance = 0.0;

        Iterator<DigitPoint> iterPoint = other.pointPath.iterator();
        for( DigitPoint point : pointPath) {
            distance += point.getDistance(iterPoint.next());
        }
        return distance;
    }
    
    @Override
    public String toString() {
    	return group + ":" + pointPath.toString();
    }
}
