package de.berlin.fu.inf.pattern.u01;

import static org.junit.Assert.*;

import org.junit.Test;

import de.berlin.fu.inf.pattern.u03.RunTest;

public class ExersizeTest {
	
	public void runTest(int k, int n){
	}
	
	@Test
	public void testU3() {
		RunTest e = new RunTest(100, 4);
		e.addKlass(1, 1, 1);
		e.addKlass(2, 3, 1);
		assertEquals("classifiver is better as random", 0.8, e.runTests(500), 0.2);
	}
}
