package de.berlin.fu.inf.pattern.fisher;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.berlin.fu.inf.pattern.impl.fisher.FisherLinearDiscriminant;
import de.berlin.fu.inf.pattern.util.data.DoubleVecor;

public class FisherTest {
	/*
	 *      0 0
	 *      0 0
	 *      |
	 * -----+----
	 *    1 1
	 *    1 1
	 */
	double[][] class0 = new double[][] {{0,2}, {0,3}, {1,2}, {1,3}};
	double[][] class1 = new double[][] {{0,-1}, {0,-2}, {-1,-1}, {-1,-2}};
	
	FisherLinearDiscriminant<DoubleVecor> fisher;

	@Before
	public void setUp() throws Exception {
		fisher = new FisherLinearDiscriminant<DoubleVecor>(2);
		
		List<DoubleVecor> l1 = new ArrayList<DoubleVecor>(4);
		List<DoubleVecor> l2 = new ArrayList<DoubleVecor>(4);
		
		for(double c[] : class0){
			l1.add(new DoubleVecor(c));
		}
		for(double c[] : class1){
			l2.add(new DoubleVecor(c));
		}
		fisher.train(l1, l2);
	}

	@Test
	public void testClassify() {
		for(double c[] : class0){
			assertEquals("all data of class 0 is classified as 0", 0, fisher.classify(new DoubleVecor(c)).intValue());
		}
		for(double c[] : class1){
			assertEquals("all data of class 1 is classified as 1", 1, fisher.classify(new DoubleVecor(c)).intValue());
		}
		assertEquals("(0,0) is classifed as 1", 1, fisher.classify(new DoubleVecor(0,0)).intValue());
		assertEquals("(1,1) is classifed as 0", 0, fisher.classify(new DoubleVecor(1,1)).intValue());
		assertEquals("(-3,1) is classifed as 1", 1, fisher.classify(new DoubleVecor(-3,1)).intValue());
	}

}
