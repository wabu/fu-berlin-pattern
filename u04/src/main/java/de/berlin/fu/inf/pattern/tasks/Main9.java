package de.berlin.fu.inf.pattern.tasks;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import de.berlin.fu.inf.pattern.impl.fisher.FisherLinearDiscriminant;
import de.berlin.fu.inf.pattern.tasks.u02.data.Digit;
import de.berlin.fu.inf.pattern.util.data.DoubleVector;


public class Main9 {
	public static void main(String[] args) throws IOException {
		Logger log = Logger.getRootLogger();
		log.info("9) Running combine clasifier");
		
		String training = "pendigits-training.txt";
		String testing = "pendigits-testing.txt";
		
		
		FishersPenController controller = new FishersPenController();
		Collection<Digit> digits = controller.readDigits(training);
		
		List<FisherLinearDiscriminant<DoubleVector>> fishers = controller.trainIncrementalClassification(digits);
		float rate = controller.runIncrementalClassification(fishers, digits);
		log.info("total success rate on training data is "+rate);
		
		Collection<Digit> testDigs = controller.readDigits(testing);
		float rateTest = controller.runIncrementalClassification(fishers, testDigs);
		log.info("total success rate on training data is "+rateTest);
	}
}
