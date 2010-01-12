/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.pca;

import de.berlin.fu.inf.pattern.util.matrix.Vectors;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import org.apache.log4j.Logger;
import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.vector.Float64Vector;

/**
 *
 * @author alex
 */
public class OjaAnalysis extends PrincipleComponentAnalysis {
    public static double DEFAULT_LAMBDA = 0.0001;
    /** learn constant */
    private final Logger logger = Logger.getLogger(OjaAnalysis.class);
    private final double lambda;
    private final Random rand;

    public OjaAnalysis(Collection<Float64Vector> floatVectors) {
        this(floatVectors, DEFAULT_LAMBDA);
    }

    public OjaAnalysis(Collection<Float64Vector> floatVectors, double lambda) {
        super(floatVectors);
        this.lambda = lambda;
        rand = new Random();
    }



    @Override
    protected Float64Vector calculateMainComponent() {
        // working on analysi data
        Float64Vector omega = Vectors.random(this.getDimension());

        final Float64Vector center = Vectors.centerOf(analysisData);
        // int i = analysisData.size();

        int i = analysisData.size();
        int range = i;
        do {
            Float64Vector randomVec = analysisData.get(rand.nextInt(range));
            
            randomVec = randomVec.minus(center);
            if( logger.isDebugEnabled() )
                logger.debug("round " + i + ": omega="+omega+", random="+ randomVec);


            omega = refreshOmega(omega,randomVec);

        } while(i-- > 0);
        
        return omega;
    }

    private Float64Vector refreshOmega(Float64Vector omega, Float64Vector vec) {
        Float64 scalar = omega.times(vec);
        // w_neu = w + lambda*scalar(vec-scalar*w)
        Float64Vector omegaNeu = omega.plus(vec.minus(omega.times(scalar)).times(scalar.times(lambda)));
        // omegaNeu = omegaNeu.times(1/omegaNeu.normValue());

        return omegaNeu;
    }

}
