package de.berlin.fu.inf.pattern.u02;

import java.util.StringTokenizer;

import de.berlin.fu.inf.pattern.u02.control.Controller;

public class Main {
	public static void main(String[] args) {
		Controller controller = new Controller();
		
		StringTokenizer t = new StringTokenizer(" 1 2 3");
		
		System.out.println(t.nextToken());
		
		System.out.println("learning ...");
		controller.learnFromFile("pendigits-training.txt");
		System.out.println("testing ...");
		System.out.println(controller.testOnFile("pendigits-testing.txt"));
	}
}
