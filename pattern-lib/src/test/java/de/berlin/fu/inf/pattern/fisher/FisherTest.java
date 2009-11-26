package de.berlin.fu.inf.pattern.fisher;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.berlin.fu.inf.pattern.impl.fisher.FisherLinearDiscriminant;
import de.berlin.fu.inf.pattern.util.data.DoubleVector;

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
	
    @SuppressWarnings("UWF_FIELD_NOT_INITIALIZED_IN_CONSTRUCTOR")
	FisherLinearDiscriminant<DoubleVector> fisher;

	@Before
	public void setUp() throws Exception {
		fisher = new FisherLinearDiscriminant<DoubleVector>(2);
		
		List<DoubleVector> l1 = new ArrayList<DoubleVector>(4);
		List<DoubleVector> l2 = new ArrayList<DoubleVector>(4);
		
		for(double c[] : class0){
			l1.add(new DoubleVector(c));
		}
		for(double c[] : class1){
			l2.add(new DoubleVector(c));
		}
		fisher.train(l1, l2);
	}

	@Test
	public void testClassify() {
		for(double c[] : class0){
			assertEquals("all data of class 0 is classified as 0", 0, fisher.classify(new DoubleVector(c)).intValue());
		}
		for(double c[] : class1){
			assertEquals("all data of class 1 is classified as 1", 1, fisher.classify(new DoubleVector(c)).intValue());
		}
		assertEquals("(0,0) is classifed as 1", 1, fisher.classify(new DoubleVector(0,0)).intValue());
		assertEquals("(1,1) is classifed as 0", 0, fisher.classify(new DoubleVector(1,1)).intValue());
		assertEquals("(-3,1) is classifed as 1", 1, fisher.classify(new DoubleVector(-3,1)).intValue());
	}

}
