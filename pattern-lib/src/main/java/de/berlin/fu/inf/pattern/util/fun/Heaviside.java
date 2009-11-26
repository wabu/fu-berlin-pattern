/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.util.fun;

import org.jscience.mathematics.structure.Field;

/**
 *
 * @author wabu
 */
public class Heaviside<F extends Field<F> & Comparable<F>> implements Function<F,F>{
    private final F zero; 
    private final F one;

    /**
     * heavestep on zero from zero to one
     * @param zero
     * @param one
     */
    public Heaviside(F zero, F one) {
        this.zero = zero;
        this.one = one;
    }

    public F apply(F x) {
        if(x.compareTo(zero) <= 0) {
            return zero;
        } else {
            return one;
        }
    }
}
