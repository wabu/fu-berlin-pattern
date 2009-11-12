package de.berlin.fu.inf.pattern.task7;

import java.net.URL;
import java.util.Collection;

import org.apache.log4j.Logger;

import de.berlin.fu.inf.pattern.impl.fisher.FisherLinearDiscriminant;
import de.berlin.fu.inf.pattern.u02.data.Digit;
import de.berlin.fu.inf.pattern.util.data.DoubleVector;


public class Main7 {
	public static void main(String[] args) {
		Logger log = Logger.getRootLogger();
		
		log.info("4.1) PINDIGITS TEST");
		String training = "pendigits-training.txt";
		
		URL url = ClassLoader.getSystemClassLoader().getResource(training);
		
		FishersPenController controller = new FishersPenController();
		Collection<Digit> digits = controller.readDigits(url);
		log.info("read " + digits.size() + " digits from file " + url.getFile());
		
		int[] classes = new int[]{0,1,2,3,4,5,6,7,8,9};
		
		
		for (int group : classes ) {
			log.info("run for group " + group);
			
			FisherLinearDiscriminant<DoubleVector> fisher = controller.trainSingleClassification(group, digits);
			float result = controller.runSingleClassification(group, fisher, digits);
			
			log.info("success rate for "+group+" is "+result);
		}
		// build 10 Fisher Linear Discriminants
	}
}
