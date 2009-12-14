/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.ada;

import de.berlin.fu.inf.pattern.iface.Classifier;
import de.berlin.fu.inf.pattern.util.types.Vectorable;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author wabu
 */
public final class Ada {
    private final static Logger LOG = Logger.getLogger(Ada.class);
    private Ada() {}

    public static AdaBoosting<Vectorable> get(int dim, int cloudSize) {
        List<Classifier<Vectorable, Integer>> cloud =
                new ArrayList<Classifier<Vectorable,Integer>>(cloudSize);
        LOG.debug("generating "+cloudSize+" random classifiers");
        for(int i=0; i<cloudSize; i++) {
            cloud.add(RandomLinearClassifier.generate(dim));
        }
        return new AdaBoosting<Vectorable>(cloud);
    }
}
