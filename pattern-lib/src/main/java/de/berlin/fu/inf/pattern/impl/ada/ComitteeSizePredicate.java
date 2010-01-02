/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.ada;

import com.google.common.base.Predicate;

/**
 *
 * @author wabu
 */
public class ComitteeSizePredicate implements Predicate<AbstractAda<?>> {
    private final int size;
    public ComitteeSizePredicate(int size) {
        this.size = size;
    }


    public boolean apply(AbstractAda<?> input) {
        return input.getComitteeSize()<=size;
    }
}
