package de.berlin.fu.inf.pattern.util.jama;

import de.berlin.fu.inf.pattern.util.matrix.Vec;
import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class VecTest {
	private final Logger logger = Logger.getLogger(VecTest.class);
	private double[] data;
	private Vec vec;

	@Before
	public void setUp() throws Exception {
		data = new double[]{1,2,5,1,6,3};
		vec = new Vec(data);
	}

	@Test
	public void testGetVectorData() {
		double[] data2 = vec.getVectorData();
		
		assertEquals(data.length, data2.length);
		
		for(int i = 0; i<data.length-1; i++) {
			logger.info(data[i] + " == " + data2[i]);
			assertTrue(data[i] == data2[i]);
		}
		
	}

    @Test
    public void testDimension() {
        assertEquals("dim of the vector is "+data.length, data.length, vec.getDimension());
    }

}
