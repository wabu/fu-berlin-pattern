package de.berlin.fu.inf.pattern;

import java.io.IOException;

import de.berlin.fu.inf.pattern.u02.Main01e2;
import de.berlin.fu.inf.pattern.u02.StartGUI;
import de.berlin.fu.inf.pattern.u03.Main01e3;

public class Main01 {
	public static void main(String[] args) throws IOException {
		System.out.print("welche aufgabe soll getestet werden? (1,2,3) ");
		System.out.flush();
		int c = System.in.read();
		switch(c){
		case '1':
			StartGUI.main(args);
			break;
		case '2':
			Main01e2.main(args);
			break;
		case '3':
			Main01e3.main(args);
			break;
		}
	}
}
