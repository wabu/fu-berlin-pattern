package de.berlin.fu.inf.pattern.tasks;

import java.io.IOException;
import java.util.Collection;

import org.apache.log4j.Logger;

import de.berlin.fu.inf.pattern.impl.fisher.FisherLinearDiscriminant;
import de.berlin.fu.inf.pattern.tasks.u02.data.Digit;
import de.berlin.fu.inf.pattern.util.data.DoubleVector;


public class Main7 {
	public static void main(String[] args) throws IOException {
		Logger log = Logger.getRootLogger();
		
		log.info("7) Running classifier for each digit");
		String training = "pendigits-training.txt";
		
		FishersPenController controller = new FishersPenController();
		Collection<Digit> digits = controller.readDigits(training);
		log.info("read " + digits.size() + " digits from file " + training);
		
		int[] classes = new int[]{0,1,2,3,4,5,6,7,8,9};
		
		
		for (int group : classes ) {
			log.info("run for group " + group);
			
			FisherLinearDiscriminant<DoubleVector> fisher = controller.trainSingleClassification(group, digits);
			float rate = controller.runSingleClassification(group, fisher, digits);
			
			log.info("success rate for "+group+" is "+rate);
		}
	}
}
