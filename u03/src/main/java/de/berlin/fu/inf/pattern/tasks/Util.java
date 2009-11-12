package de.berlin.fu.inf.pattern.tasks;

public class Util {
	
	public static String toBinaryString(int value) {
		
		StringBuilder builder = new StringBuilder();
		
		int tmp = value;
		int size = Integer.SIZE;
		for( int i = 0; i < size; i++) {
			if( i%(Byte.SIZE) == 0 && i!=0) builder.append(".");
			builder.append(tmp & 0x01);
			tmp >>= 1;
		}
		return builder.reverse().toString();		
	}
	
	public static void main(String[] argv) {
		System.out.println(0x000F);
		System.out.println(0x00FF);
		System.out.println(0xFFFF);
		
		System.out.println(Util.toBinaryString(255));
		
	}
	
}
