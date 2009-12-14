/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import de.berlin.fu.inf.pattern.data.Entry;
import de.berlin.fu.inf.pattern.impl.ada.Ada;
import de.berlin.fu.inf.pattern.impl.ada.AdaBoosting;
import de.berlin.fu.inf.pattern.tasks.u02.data.Digit;
import de.berlin.fu.inf.pattern.tasks.u02.data.DigitPoint;
import de.berlin.fu.inf.pattern.tasks.u02.data.DigitReader;
import de.berlin.fu.inf.pattern.util.data.DoubleVector;
import de.berlin.fu.inf.pattern.util.gen.MultiNormalGenerator;
import de.berlin.fu.inf.pattern.util.test.ClassifierTest;
import de.berlin.fu.inf.pattern.util.test.IntClassifierTest;
import de.berlin.fu.inf.pattern.util.types.Vectorable;
import org.apache.log4j.Logger;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

/**
 * Run Adaboosting on Pen-Digits
 * 
 * @author covin
 */
public class Task18 implements Runnable {
    private final Logger logger = Logger.getLogger(Task18.class);

    // transformation classes for Collections2.transform
    class DigitToVec implements Function<Digit, Vectorable> {
		public Vectorable apply(Digit digit) {
			double vecData[] = new double[Digit.POINT_NUMBER*2];
			int i = 0;
			for( DigitPoint point : digit.getPoints() ) {
				// scale
				vecData[i++] = point.getX()*1d/DigitPoint.MAX_X;
				vecData[i++] = point.getY()*1d/DigitPoint.MAX_Y;
			}
			DoubleVector vec = new DoubleVector(vecData);
			if( logger.isTraceEnabled() ) {
				logger.trace("new Entry: Vect" + vec + " Group:" + digit.getGroup());
			}
			
			return vec;
		}
	}
    
    class DigitToEntrySplitSeven implements Function<Digit, Entry<Vectorable, Integer>> {
    	private static final int SEVEN_GROUP = 7;
		public Entry<Vectorable, Integer> apply(Digit digit) {
			double vecData[] = new double[Digit.POINT_NUMBER*2];
			int i = 0;
			for( DigitPoint point : digit.getPoints() ) {
				// scale
				vecData[i++] = point.getX()*1d/DigitPoint.MAX_X;
				vecData[i++] = point.getY()*1d/DigitPoint.MAX_Y;
			}
			DoubleVector vec = new DoubleVector(vecData);
			if( logger.isTraceEnabled() ) {
				logger.trace("new Entry: Vect" + vec + " Group:" + digit.getGroup());
			}
			return new Entry<Vectorable, Integer>(vec, digit.getGroup() == SEVEN_GROUP ? 1 : 0);
		}
	}
    
    public void run() {
    	logger.info("18) Running classification of pendigits using Ada-boosting");
    	
    	String training = "pendigits-training.txt";
		String testing = "pendigits-testing.txt";
		
    	DigitReader digitReader = new DigitReader();

    	logger.info("read files");
    	// read pendigits from training and testing file
    	Collection<Digit> digitsTrain;
    	Collection<Digit> digitsTest;
    	try {
			digitsTrain = digitReader.readDigits(training);
			digitsTest = digitReader.readDigits(testing);
    	} catch( IOException ioEx ) {
    		logger.fatal("reading data failed", ioEx);
    		return;
    	}
    	// 
    	Collection<Vectorable> sevenDigits = new ArrayList<Vectorable>(digitsTrain.size()/10);
    	Collection<Vectorable> nonSevenDigits = new ArrayList<Vectorable>(digitsTrain.size());
    	 
    	DigitToVec digitToVec = new DigitToVec();
    	int sevenGroup = 7;
    	for( Digit digit : digitsTrain ) {
    		if( digit.getGroup() == sevenGroup) {
    			sevenDigits.add(digitToVec.apply(digit));
    		} else {
    			nonSevenDigits.add(digitToVec.apply(digit));
    		}
    	}
    	
    	Collection<Entry<Vectorable,Integer>> testDigits = Collections2.transform(digitsTest, new DigitToEntrySplitSeven());
    	
    	
    	
    	AdaBoosting<Vectorable> ada = Ada.get(16, 10000);
    	logger.info("run training");
        ada.train(sevenDigits, nonSevenDigits);

        ClassifierTest<Vectorable,Integer> test =
                new ClassifierTest<Vectorable, Integer>(ada);

        double rate = test.runTest(testDigits);
        logger.info("rate is "+rate);
    }
}