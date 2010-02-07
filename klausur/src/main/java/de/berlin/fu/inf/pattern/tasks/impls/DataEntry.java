/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks.impls;

import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.vector.Float64Matrix;
import org.jscience.mathematics.vector.Float64Vector;
import org.jscience.mathematics.vector.Matrix;
import org.jscience.mathematics.vector.Vector;

/**
 *
 * @author wabu
 */
public class DataEntry {
    public final static int X = 0;
    public final static int Y = 1;
    public final static int COS = 2;
    public final static int SIN = 3;
    public final static int V = 4;

    private final Vector<Float64> data;

    public DataEntry(Vector<Float64> data) {
        this.data = data;
    }

    public Vector<Float64> getVector() {
        return data;
    }

    double getX() {
        return data.get(X).doubleValue();
    }
    double getY() {
        return data.get(Y).doubleValue();
    }
    double getCos() {
        return data.get(COS).doubleValue();
    }
    double getSin() {
        return data.get(SIN).doubleValue();
    }
    double getV() {
        return data.get(V).doubleValue();
    }
    double getVx() {
        return getCos()*getV();
    }
    double getVy() {
        return getSin()*getV();
    }

    Vector<Float64> getPos() {
        return Float64Vector.valueOf(getX(), getY());
    }
    Vector<Float64> getVel() {
        return Float64Vector.valueOf(getVx(), getVy());
    }


    Matrix<Float64> getRotation() {
        return Float64Matrix.valueOf(new double[][]{
                    {getCos(), -getSin()},
                    {getSin(), getCos()},
        });
    }
}
