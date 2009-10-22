package de.berlin.fu.inf.pattern.u02;

import org.apache.log4j.Logger;

import de.berlin.fu.inf.pattern.u02.control.Controller;

public class Main01e2 {
	public static void main(String[] args) {
		Logger log = Logger.getLogger(Main01e2.class);
		log.info("1.2) PINDIGITS TEST");
		
		String training = "pendigits-training.txt";
		String testing = "pendigits-testing.txt";
		
		Controller controller = new Controller();
		
		log.info("learning from "+training);
		controller.learnFromFile(training);
		float data[] = new float[8];
		for(int k=1; k<8; k++){
			log.info("testing with k="+k+" on"+testing+" ...");
			data[k] = controller.testOnFile(testing, k);
			log.info("got successreate "+data[k]); 
		}
		for(int k=1; k<8; k++){
			System.out.println(k+"\t"+data[k]);
		}
	}
}
