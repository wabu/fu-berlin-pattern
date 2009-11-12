package de.berlin.fu.inf.pattern.tasks.u03;

import org.apache.log4j.Logger;

public class Main01e3 {
	public static void main(String[] args) {
		Logger log = Logger.getLogger(Main01e3.class);
		log.info("1.3) GAUSS TEST");
		
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
