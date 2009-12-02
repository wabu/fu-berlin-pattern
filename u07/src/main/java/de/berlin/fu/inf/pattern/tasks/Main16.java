package de.berlin.fu.inf.pattern.tasks;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.jscience.mathematics.number.Float64;

import de.berlin.fu.inf.pattern.data.Entry;
import de.berlin.fu.inf.pattern.impl.perzeptron.BackProptron;
import de.berlin.fu.inf.pattern.impl.perzeptron.PerzeptronHighestValueClassifier;
import de.berlin.fu.inf.pattern.impl.perzeptron.Perzeptrons;
import de.berlin.fu.inf.pattern.tasks.u02.data.Digit;
import de.berlin.fu.inf.pattern.tasks.u02.data.DigitReader;
import de.berlin.fu.inf.pattern.util.test.ClassifierTest;
import de.berlin.fu.inf.pattern.util.types.Vectorable;

public class Main16 {
    private Logger log = Logger.getLogger(Main16.class);
    private final int maxIterations = 3;
    private final int minNeuros = 5;
    private final int maxNeuros = 25;
    private ClassifierTest<Vectorable, Integer> classifierTest;
    
    public void run() {
		log.info("16) Running classification of pendigits using Backprop-Perzeptron");
		
		String training = "pendigits-training.txt";
		String testing = "pendigits-testing.txt";
		
		
		
    	DigitReader digitReader = new DigitReader();
    	Controller controller = new Controller();
    	
    	log.info("read files");
    	// read pendigits from training and testing file
    	Collection<Digit> digitsTrain;
    	Collection<Digit> digitsTest;
    	try {
			digitsTrain = digitReader.readDigitsFromStream(
					new InputStreamReader(ClassLoader.getSystemResourceAsStream(training)));
			digitsTest = digitReader.readDigitsFromStream(
					new InputStreamReader(ClassLoader.getSystemResourceAsStream(testing)));
    	} catch( IOException ioEx ) {
    		log.fatal("reading data failed", ioEx);
    		return;
    	}
		
		Collection<Entry<Vectorable, Integer>> cTrain = controller.transformDigits(digitsTrain);
		Collection<Entry<Vectorable, Integer>> cTest  = controller.transformDigits(digitsTest);
		
		double rate;
		log.info("run tests");
		for( int neuros=minNeuros; neuros <= maxNeuros; neuros++ ) {
			
			BackProptron<Float64> penTron = Perzeptrons.generatePerzeptron(16, neuros, 10);
			PerzeptronHighestValueClassifier classifier = 
				new PerzeptronHighestValueClassifier(penTron);
			
			for( int i=1; i<=maxIterations; i++ ) {
				classifier.train(cTrain);
				
				classifierTest = new ClassifierTest<Vectorable, Integer>(classifier);
				
				rate = classifierTest.runTest(cTest);
				log.info("success rate: neuros="+neuros + " iteration=" +i + " rate="+rate );
			}
		}
    }
    
    public static void main(String[] argv) {
        new Main16().run();
    }
}
