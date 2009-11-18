package de.berlin.fu.inf.pattern.util.jama;


import Jama.Matrix;

public class MatrixString {
	public static String ms(Matrix m){
        if(m == null) {
            return "nil matrix";
        }
        StringBuilder sb = new StringBuilder();
        double [] data = m.getRowPackedCopy();
        for(double d : data){
            sb.append(String.format("%5e ", d));
        }
        return "[ "+sb+"]";
	}
}
