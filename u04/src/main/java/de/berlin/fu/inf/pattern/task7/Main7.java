package de.berlin.fu.inf.pattern.task7;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import de.berlin.fu.inf.pattern.impl.fisher.FisherLinearDiscriminant;
import de.berlin.fu.inf.pattern.u02.data.Digit;
import de.berlin.fu.inf.pattern.u02.data.DigitPoint;
import de.berlin.fu.inf.pattern.u02.data.DigitReader;


public class Main7 {
	public static void main(String[] args) {
		Logger log = Logger.getRootLogger();
		
		log.info("4.1) PINDIGITS TEST");
		String training = "pendigits-training.txt";
		
		URL url = ClassLoader.getSystemClassLoader().getResource(training);
		
		DigitReader digitReader = new DigitReader();
		
		Collection<Digit> c = digitReader.readDigitsFromFile(url.getFile());
		log.info("read " + c.size() + " digits from file " + url.getFile());
		
		int[] classes = new int[]{0,1,2,3,4,5,6,7,8,9};
		
		for (int group : classes ) {
			log.info("run for group " + group);
			// create lists to separate points of digits, which belongs to current group an vv
			// we can assume size in dependency to number of digits
			List<DigitPoint> inClass = new ArrayList<DigitPoint>(c.size()*Digit.POINT_NUMBER/classes.length);
			List<DigitPoint> beyondClass = new ArrayList<DigitPoint>(c.size()*Digit.POINT_NUMBER);
			
			// FIXME we have to put all the digit data into *one* vector, so we have a 16D space
			for(Digit digit : c) {
				if( digit.getGroup() == group ) {
					inClass.addAll(digit.getPoints());
				} else {
					beyondClass.addAll(digit.getPoints());
				}
			}
			
			FisherLinearDiscriminant<DigitPoint> fld = new FisherLinearDiscriminant<DigitPoint>(2);
			fld.train(inClass, beyondClass);
			log.info("train done: " + fld);
			// bad practice!
			// TODO create controller to classify a hole digit... 
			
			
			
		}
		// build 10 Fisher Linear Discriminants
	}
}
