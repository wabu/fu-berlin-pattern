/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.util.fun;

/**
 * f: X -> Y
 * @author wabu
 */
public interface Funct<X,Y> {
    Y apply(X x);
}
