/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.util.collect;

import com.google.common.base.Function;
import de.berlin.fu.inf.pattern.iface.Classifier;

/**
 *
 * @author wabu
 */
public class GuessedRightTransform<D,C> implements Function<D, Boolean> {
    private final Classifier<? super D,C> cf;
    private final C k;

    public GuessedRightTransform(Classifier<? super D, C> cf, C k) {
        this.cf = cf;
        this.k = k;
    }

    public Boolean apply(D from) {
        return k.equals(cf.classify(from));
    }
}
