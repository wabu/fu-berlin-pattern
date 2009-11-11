package de.berlin.fu.inf.pattern.util.jama;

import java.io.PrintWriter;
import java.io.StringWriter;

import Jama.Matrix;

public class MatrixString {
	public static String ms(Matrix m){
		StringWriter sw = new StringWriter();
		m.print(new PrintWriter(sw), 8, 6);
		return sw.toString();
	}
}
