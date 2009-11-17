package de.berlin.fu.inf.pattern.util.data;

import static org.junit.Assert.*;
import static de.berlin.fu.inf.pattern.util.jama.MatrixString.ms;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import de.berlin.fu.inf.pattern.util.jama.Vec;

import Jama.Matrix;

public class DistributionGeneratorTest {
	
	private Logger logger = Logger.getLogger(DistributionGeneratorTest.class);
	
	
	DistributionGenerator gen;
	int dimension = 3;
	
	@Before
	public void setUp() {
		gen = new DistributionGenerator();
		gen.setRange(5);
	}
	
	@Test
	public void testCreateCovariance() {
		Matrix tmpMatrix = gen.createCovariance(dimension);
		
		assertEquals(tmpMatrix.getColumnDimension(), dimension);
		assertEquals(tmpMatrix.getRowDimension(), dimension);
		assertTrue(tmpMatrix.chol().isSPD());
	}

	@Test
	public void testCreateVector() {
		
		int size = 10000;
		
		Matrix tmpMatrix = gen.createCovariance(dimension);
		assertTrue(tmpMatrix.chol().isSPD());
		
		
		Collection<Vec> cllctn = gen.createVectors(
				tmpMatrix,
				size);
		
		assertNotNull(cllctn);
		assertEquals(cllctn.size(), size);
		
		Matrix realCov = new Matrix(dimension, dimension);
		for(Vec vec : cllctn) {
			realCov = realCov.plus(vec.times(vec.transpose()));
			logger.trace(ms(vec));
		}
		realCov = realCov.times( 1.0/size);
		// compare random cov with real of cov of generated data
		logger.info("compare random cov="+ms(tmpMatrix) + " calculated cov= " + ms(realCov));
		
		/*
		for(int row = 0; row < dimension; row++) {
			for( int col = 0; col < dimension; col++) {
				assertTrue(
						Math.abs(tmpMatrix.get(row, col)/realCov.get(row, col)) < 0.2 );
			}
		}
		*/
		
		// fail("Not yet implemented");
	}

}
