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
public class SndTransformation<B> implements Function<Pair<?, ? extends B>, B>{
    @Override
    public B apply(Pair<?, ? extends B> from) {
        return from.snd;
    }
}
