package de.berlin.fu.inf.pattern.u02;

import de.berlin.fu.inf.pattern.u02.control.Controller;

public class Main {
	public static void main(String[] args) {
		Controller controller = new Controller();
		
		System.out.println("learning ...");
		controller.learnFromFile("pendigits-training.txt");
		for(int k=1; k<8; k++){
			System.out.println("testing with k="+k+"...");
			System.out.println(controller.testOnFile("pendigits-testing.txt", k));
		}
	}
}
