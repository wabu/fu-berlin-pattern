/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.perzeptron;

import de.berlin.fu.inf.pattern.util.fun.Heaviside;
import org.jscience.mathematics.number.Rational;
import org.jscience.mathematics.vector.DenseMatrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.jscience.mathematics.number.Rational.*;

/**
 *
 * @author wabu
 */
public class SimplePerzeptronTest {

    public SimplePerzeptronTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

     @Test
     public void testXoron() {
         Rational half = Rational.valueOf(1,2);

         Heaviside<Rational> s = new Heaviside<Rational>(Rational.ZERO, Rational.ONE);

         @SuppressWarnings("unchecked")
         Perzeptron<Rational> xoron = new Perzeptron<Rational>(s, Rational.ONE,
             DenseMatrix.valueOf(new Rational[][]{
                 {ONE, ONE.opposite(), half.opposite()},
                 {ONE.opposite(), ONE, half.opposite()},
             }),
             DenseMatrix.valueOf(new Rational[][]{
                { ONE, ONE, half.opposite()},
             })
         );

         PerzeptronSingleValueClassifier<Rational> xor
                 = new PerzeptronSingleValueClassifier<Rational>(xoron);

         assertEquals("0 xor 1 = 1", Rational.ONE, xor.classify(Rational.ZERO,Rational.ONE));
         assertEquals("1 xor 0 = 1", Rational.ONE, xor.classify(Rational.ONE,Rational.ZERO));
         assertEquals("0 xor 0 = 0", Rational.ZERO, xor.classify(Rational.ZERO,Rational.ZERO));
         assertEquals("1 xor 1 = 0", Rational.ZERO, xor.classify(Rational.ONE,Rational.ONE));
     }
}
