/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.util.fun;

import org.jscience.mathematics.number.Float64;

/**
 *
 * @author wabu
 */
public class Sigmoid implements Derivable<Float64, Float64>{
    public Funct<Float64, Float64> derive() {
        return new Funct<Float64, Float64>() {
            public Float64 apply(Float64 x) {
                Float64 o = Sigmoid.this.apply(x);
                return o.times(Float64.ONE.minus(o));
            }
        };
    }

    public Float64 apply(Float64 x) {
        return Float64.valueOf(1.0d/(1d+Math.exp(-x.doubleValue())));
    }
}
