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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
    public void testAndon() throws InterruptedException {
        List<Entry<? extends Vector<Float64>, ? extends Vector<Float64>>> table =
                new ArrayList<Entry<? extends Vector<Float64>, ? extends Vector<Float64>>>();
        table.add(Entry.valueOf(DenseVector.valueOf(ZERO,ONE), DenseVector.valueOf(ONE)));
        table.add(Entry.valueOf(DenseVector.valueOf(ONE,ZERO), DenseVector.valueOf(ONE)));
        table.add(Entry.valueOf(DenseVector.valueOf(ZERO,ZERO), DenseVector.valueOf(ZERO)));
        table.add(Entry.valueOf(DenseVector.valueOf(ONE,ONE), DenseVector.valueOf(ONE)));

        BackProptron<Float64> xontron = Perzeptrons.generatePerzeptron(2, 2, 1);
        PerzeptronSingleValueClassifier<Float64> and
                 = new PerzeptronSingleValueClassifier<Float64>(xontron);

        for(int i=0; i<200; i++) {
            xontron.trainOffline(table);
        }

        logger.debug("0 and 0 = " + and.classify(ZERO,ZERO).doubleValue());
        logger.debug("0 and 1 = " + and.classify(ZERO,ONE).doubleValue());
        logger.debug("1 and 0 = " + and.classify(ONE,ZERO).doubleValue());
        logger.debug("1 and 1 = " + and.classify(ONE,ONE).doubleValue());

        assertEquals("0 and 1 = 1", 1, and.classify(ZERO,ONE).doubleValue(), 0.3);
        assertEquals("1 and 0 = 1", 1, and.classify(ONE,ZERO).doubleValue(), 0.3);
        assertEquals("0 and 0 = 0", 0, and.classify(ZERO,ZERO).doubleValue(), 0.3);
        assertEquals("1 and 1 = 0", 1, and.classify(ONE,ONE).doubleValue(), 0.3);
     }

    @Test
    public void testOron() throws InterruptedException {
        List<Entry<? extends Vector<Float64>, ? extends Vector<Float64>>> table =
                new ArrayList<Entry<? extends Vector<Float64>, ? extends Vector<Float64>>>();
        table.add(Entry.valueOf(DenseVector.valueOf(ZERO,ONE), DenseVector.valueOf(ONE)));
        table.add(Entry.valueOf(DenseVector.valueOf(ONE,ZERO), DenseVector.valueOf(ONE)));
        table.add(Entry.valueOf(DenseVector.valueOf(ZERO,ZERO), DenseVector.valueOf(ZERO)));
        table.add(Entry.valueOf(DenseVector.valueOf(ONE,ONE), DenseVector.valueOf(ONE)));

        BackProptron<Float64> oron = Perzeptrons.generatePerzeptron(2, 2, 1);
        PerzeptronSingleValueClassifier<Float64> or
                 = new PerzeptronSingleValueClassifier<Float64>(oron);

        for(int i=0; i<200; i++) {
            oron.trainOffline(table);
        }
        logger.debug("0 or 0 = " + or.classify(ZERO,ZERO).doubleValue());
        logger.debug("0 or 1 = " + or.classify(ZERO,ONE).doubleValue());
        logger.debug("1 or 0 = " + or.classify(ONE,ZERO).doubleValue());
        logger.debug("1 or 1 = " + or.classify(ONE,ONE).doubleValue());

        assertEquals("0 or 0 = 0", 0, or.classify(ZERO,ZERO).doubleValue(), 0.3);
        assertEquals("0 or 1 = 1", 1, or.classify(ZERO,ONE).doubleValue(), 0.3);
        assertEquals("1 or 0 = 1", 1, or.classify(ONE,ZERO).doubleValue(), 0.3);
        assertEquals("1 or 1 = 1", 1, or.classify(ONE,ONE).doubleValue(), 0.3);
     }
}
