package de.berlin.fu.inf.pattern.u03;

public class Main01e3 {
	public static void main(String[] args) {
		for(int k=1; k<8; k++){
			for(int n=0; n<21; n++){
				
				int num = 5+n*10;
				
				RunTest e = new RunTest(num, k);
				e.addKlass(1, 1, 1);
				e.addKlass(2, 2, 2);
				System.out.println(k+"\t"+num+"\t"+e.runTests(10000));
			}
			System.out.println();
		}
	}

}
