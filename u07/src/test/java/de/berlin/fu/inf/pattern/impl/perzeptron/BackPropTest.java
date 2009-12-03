/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.perzeptron;

import de.berlin.fu.inf.pattern.data.Entry;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.vector.DenseVector;
import org.jscience.mathematics.vector.Vector;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.jscience.mathematics.number.Float64.*;

/**
 *
 * @author wabu
 */
public class BackPropTest {
    private final Logger logger = Logger.getLogger(BackPropTest.class);

    @Test
    public void testXoron() throws InterruptedException {
        List<Entry<? extends Vector<Float64>, ? extends Vector<Float64>>> table =
                new ArrayList<Entry<? extends Vector<Float64>, ? extends Vector<Float64>>>();
        table.add(Entry.valueOf(DenseVector.valueOf(ZERO,ONE), DenseVector.valueOf(ONE)));
        table.add(Entry.valueOf(DenseVector.valueOf(ONE,ZERO), DenseVector.valueOf(ONE)));
        table.add(Entry.valueOf(DenseVector.valueOf(ZERO,ZERO), DenseVector.valueOf(ZERO)));
        table.add(Entry.valueOf(DenseVector.valueOf(ONE,ONE), DenseVector.valueOf(ZERO)));

        BackProptron<Float64> xontron = Perzeptrons.generatePerzeptron(2, 2, 1);
        PerzeptronSingleValueClassifier<Float64> xor
                 = new PerzeptronSingleValueClassifier<Float64>(xontron);

        for(int i=0; i<1500; i++) {
            xontron.trainOffline(table);
        }

        logger.debug("0 xor 0 = " + xor.classify(ZERO,ZERO).doubleValue());
        logger.debug("0 xor 1 = " + xor.classify(ZERO,ONE).doubleValue());
        logger.debug("1 xor 0 = " + xor.classify(ONE,ZERO).doubleValue());
        logger.debug("1 xor 1 = " + xor.classify(ONE,ONE).doubleValue());

        assertEquals("0 xor 1 = 1", 1, xor.classify(ZERO,ONE).doubleValue(), 0.5);
        assertEquals("1 xor 0 = 1", 1, xor.classify(ONE,ZERO).doubleValue(), 0.5);
        assertEquals("0 xor 0 = 0", 0, xor.classify(ZERO,ZERO).doubleValue(), 0.5);
        assertEquals("1 xor 1 = 0", 0, xor.classify(ONE,ONE).doubleValue(), 0.5);
     }
}
