package de.berlin.fu.inf.pattern.tasks;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

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
    private final int maxIterations = 1000;
    private final int minNeuros = 5;
    private final int maxNeuros = 100;
    private ClassifierTest<Vectorable, Integer> classifierTest;
    
    public void run() throws InterruptedException {
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
		
        double rate, lastErr = 0;
		log.info("run tests");
		for( int neuros=minNeuros; neuros <= maxNeuros; neuros*=1.2 ) {
			BackProptron<Float64> penTron = Perzeptrons.generatePerzeptron(16, neuros, 10);
            penTron.setGamma(Float64.valueOf(0.003d));

			PerzeptronHighestValueClassifier classifier = 
				new PerzeptronHighestValueClassifier(penTron);
			
			for( int i=0; i<=maxIterations; i++ ) {
                double error = classifier.train(cTrain);
                if(error > lastErr) {
                    penTron.incGamma(Float64.valueOf(0.8f));
                } else {
                    penTron.incGamma(Float64.valueOf(1.02f));
                }
                lastErr = error;
				
				classifierTest = new ClassifierTest<Vectorable, Integer>(classifier);
				
				rate = classifierTest.runTest(cTest);

				log.info(
                        String.format("neurons=%d i=%03d rate=%.6f err=%.6f, gamma=%.6f",
                        neuros, i, rate, error, penTron.getGamma().doubleValue()));

			}
		}
    }
    
    public static void main(String[] argv) throws InterruptedException {
        new Main16().run();
    }
}
