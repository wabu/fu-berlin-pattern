/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import de.berlin.fu.inf.pattern.data.Entry;
import de.berlin.fu.inf.pattern.tasks.u02.data.Digit;
import de.berlin.fu.inf.pattern.tasks.u02.data.DigitPoint;
import de.berlin.fu.inf.pattern.tasks.u02.data.DigitReader;
import de.berlin.fu.inf.pattern.util.data.DoubleVector;
import de.berlin.fu.inf.pattern.util.test.ClassifierTest;
import de.berlin.fu.inf.pattern.util.types.Vectorable;
import org.apache.log4j.Logger;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import de.berlin.fu.inf.pattern.impl.ada.AbstractAda;
import de.berlin.fu.inf.pattern.impl.ada.LinearRegressionBoosting;
import de.berlin.fu.inf.pattern.util.Threads;
import javax.annotation.Nullable;

/**
 * Run Adaboosting on Pen-Digits
 * 
 * @author covin
 */
public class Task18andAHalf implements Runnable, Predicate<AbstractAda<Vectorable>> {
    private final Logger logger = Logger.getLogger(Task18andAHalf.class);

    @Nullable
    private ClassifierTest<Vectorable,Integer> test = null;
    @Nullable
    private Collection<Entry<Vectorable, Integer>> testDigits = null;

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
			return new Entry<Vectorable, Integer>(vec, digit.getGroup() == SEVEN_GROUP ? 0 : 1);
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
        int size = digitsTrain.size()/10;
    	Collection<Vectorable> sevenDigits = new ArrayList<Vectorable>(size);
    	Collection<Vectorable> nonSevenDigits = new ArrayList<Vectorable>(size);
    	 
    	DigitToVec digitToVec = new DigitToVec();
    	int sevenGroup = 7;
    	for( Digit digit : digitsTrain ) {
    		if( digit.getGroup() == sevenGroup) {
                //if(sevenDigits.size() < size)
                    sevenDigits.add(digitToVec.apply(digit));
    		} else {
                //if(nonSevenDigits.size() < size)
                    nonSevenDigits.add(digitToVec.apply(digit));
    		}
    	}
    	
        LinearRegressionBoosting<Vectorable> ada = 
                new LinearRegressionBoosting<Vectorable>(this);
    	testDigits =
                Collections2.transform(digitsTest, new DigitToEntrySplitSeven());
        test = new ClassifierTest<Vectorable, Integer>(ada);

    	logger.info("run training");
        ada.train(sevenDigits, nonSevenDigits);
    }

    private double bestRate = 0;
    private double badCount = 0;

    public boolean apply(AbstractAda<Vectorable> ada) {
        logger.debug("checking wether to train more");

        assert test != null;
        assert testDigits != null;

        double rate = test.runTest(testDigits);
        logger.info("rate with "+ada.getComitteeSize()+" members is "+rate);

        if(ada.getComitteeSize() >= 500) {
            logger.info("comittee gets to big, stopping boost.");
            return false;
        }
        if(rate <= bestRate) {
            badCount++;
        } else {
            badCount = 0;
            bestRate = rate;
        }
        if(badCount > 25) {
            logger.info("last 25 comittee additions weren't good, stoping boost");
            logger.info("best rate was "+bestRate);
            return false;
        }
        return true;
    }

    public static void main(String args[]){
        new Task18andAHalf().run();
        Threads.shutdown();
    }
}
