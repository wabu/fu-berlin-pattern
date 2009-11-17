package de.berlin.fu.inf.pattern.util.jama;


import Jama.Matrix;
import java.util.Arrays;

public class MatrixString {
	public static String ms(Matrix m){
        if(m == null) {
            return "nil matrix";
        }
        return Arrays.toString(m.getRowPackedCopy());
	}
}
