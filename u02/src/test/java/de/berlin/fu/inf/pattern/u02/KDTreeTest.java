package de.berlin.fu.inf.pattern.u02;

import java.util.Random;

import org.apache.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

import de.berlin.fu.inf.pattern.impl.kdtree.KDTreeImpl;
import de.berlin.fu.inf.pattern.util.data.IntVector;

public class KDTreeTest {
	final Logger log = Logger.getLogger(KDTreeTest.class);

	
	@Test
	public void randomizedTest() {
		int points = 2000, runs=500;
		Random rnd = new Random();
		
		log.debug("generating ranodm data");
		
		IntVector[] values = new IntVector[points];
		for(int i=0; i<points; i++) {
			values[i] = new IntVector(new int[] {rnd.nextInt(1000), rnd.nextInt(1000)});
		}
		
		
		KDTreeImpl<IntVector> tree = new KDTreeImpl<IntVector>(2);
		tree.buildTree(values);
		
		for(int i=0; i<runs; i++) {
			runTest(rnd, values, tree);
			if(i%100 == 99){
				log.debug(i+1+"/"+runs+" random tests");
			}
		}
	}

	private void runTest(Random rnd, IntVector[] values, KDTreeImpl<IntVector> tree) {
		IntVector point = new IntVector(new int[]{rnd.nextInt(), rnd.nextInt()});
		
		IntVector kdBest = tree.findNearestValues(point);
		
		double kdDist = point.getDistance(kdBest);
		
		for(IntVector v : values){
			double dist = v.getDistance(point)*1.00001;
			assertTrue("all points have smaller dist as kdNearest ("+kdDist+"<="+dist+")",kdDist<=dist);
		}
	}
}
