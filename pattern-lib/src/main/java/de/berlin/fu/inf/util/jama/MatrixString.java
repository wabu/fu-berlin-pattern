package de.berlin.fu.inf.util.jama;

import java.io.PrintWriter;
import java.io.StringWriter;

import Jama.Matrix;

public class MatrixString {
	public static String ms(Matrix m){
		StringWriter sw = new StringWriter();
		m.print(new PrintWriter(sw), 4, 2);
		return sw.toString();
	}
}
