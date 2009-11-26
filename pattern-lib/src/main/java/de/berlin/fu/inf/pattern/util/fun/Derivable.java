/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.util.fun;

/**
 *
 * @author wabu
 */
public interface Derivable<X,Y> extends Funct<X,Y> {
    Funct<X,Y> derive();
}
