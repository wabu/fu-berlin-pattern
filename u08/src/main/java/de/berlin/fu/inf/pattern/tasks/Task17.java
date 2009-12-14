/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks;

import de.berlin.fu.inf.pattern.impl.ada.Ada;
import de.berlin.fu.inf.pattern.impl.ada.AdaBoosting;
import de.berlin.fu.inf.pattern.util.gen.MultiNormalGenerator;
import de.berlin.fu.inf.pattern.util.test.IntClassifierTest;
import de.berlin.fu.inf.pattern.util.types.Vectorable;
import org.apache.log4j.Logger;

/**
 * @author wabu
 */
public class Task17 implements Runnable {
    private final Logger logger = Logger.getLogger(Task17.class);

    public void run() {
        AdaBoosting<Vectorable> ada = Ada.get(10, 10000);

        MultiNormalGenerator g1 = new MultiNormalGenerator(10,0.5d);
        MultiNormalGenerator g2 = new MultiNormalGenerator(10,0.5d);

        ada.train(g1.getGeneratedData(1000), g2.getGeneratedData(1000));

        IntClassifierTest<Vectorable> test =
                new IntClassifierTest<Vectorable>(ada);

        double rate = test.runTest(1000, g1, g2);
        logger.info("rate is "+rate);
    }
}
