/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks;

import com.google.common.base.Function;
import de.berlin.fu.inf.pattern.util.Pair;

/**
 *
 * @author wabu
 */
public class FstTransformation<A> implements Function<Pair<? extends A, ?>, A>{
    @Override
    public A apply(Pair<? extends A, ?> from) {
        return from.fst;
    }
}
