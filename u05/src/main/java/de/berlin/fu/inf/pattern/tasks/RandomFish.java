/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks;

import Jama.Matrix;
import de.berlin.fu.inf.pattern.iface.Classifier;
import de.berlin.fu.inf.pattern.util.data.DoubleVector;
import de.berlin.fu.inf.pattern.util.matrix.MatrixString;
import de.berlin.fu.inf.pattern.util.matrix.Vec;
import java.util.Random;

/**
 *
 * @author wabu
 */
public class RandomFish implements Classifier<DoubleVector, Integer> {
    private final Matrix projection;
    private final double threshold;
    public RandomFish(double threshold, double ... trans) {
        this.threshold = threshold;
        this.projection = new Vec(trans).transpose();
    }

    public Integer classify(DoubleVector data) {
        double val = projection.times(new Vec(data)).get(0, 0);
        return (val>threshold) ? 1 : 0;
    }

    private static Random rnd = new Random();
    public static RandomFish generate2dFish() {
        double thres = rnd.nextDouble()*5;
        double theta = rnd.nextDouble()*2*Math.PI;
        return new RandomFish(thres, Math.cos(theta), Math.sin(theta));
    }

    @Override
    public String toString() {
        return "Fish["+threshold+"]"+MatrixString.ms(projection);
    }
}
