/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import de.berlin.fu.inf.pattern.impl.nmf.EMNegativeMatrixFactorization;
import de.berlin.fu.inf.pattern.tasks.digits.RasterDigit;
import de.berlin.fu.inf.pattern.tasks.digits.RasterDigitReader;
import de.berlin.fu.inf.pattern.tasks.gui.JMainFrameNMF;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import org.apache.log4j.Logger;
import org.jscience.mathematics.vector.Float64Vector;

/**
 *
 * @author alex
 */
public class Exercise11 implements Runnable {
    private final Logger logger = Logger.getRootLogger();

    public void run() {
        logger.info("23) Running Exercise 11");

        String digitFile = "digits-training.txt";
        RasterDigitReader reader = new RasterDigitReader();

        Map<RasterDigit, Integer> digits = null;
        try {
            digits = reader.readRasterDigitsFromStream(
                    ClassLoader.getSystemResourceAsStream(digitFile));
        } catch( IOException ioEx) {
            logger.fatal("reading " + digitFile + " failed", ioEx);
            System.exit(-1);
        }

        logger.info("read " + digits.size() + " RasterDigits");


        // start GUI
        final JMainFrameNMF mainFrame = new JMainFrameNMF();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                mainFrame.setVisible(true);
            }
        });
        EMNegativeMatrixFactorization nmf = new EMNegativeMatrixFactorization();

        nmf.learn(
            Lists.transform(new ArrayList<RasterDigit>(digits.keySet()), new Function<RasterDigit, Float64Vector>() {
                public Float64Vector apply(RasterDigit from) {
                    return from.getVec();
                }
        }));

        GUIControllerImpl ctrl = new GUIControllerImpl(digits);
        mainFrame.setGUIController(ctrl);


        


    }


}
