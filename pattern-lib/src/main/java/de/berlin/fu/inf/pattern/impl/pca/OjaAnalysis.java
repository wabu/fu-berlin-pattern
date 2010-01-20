/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.pca;

import com.google.common.collect.Lists;
import de.berlin.fu.inf.pattern.util.matrix.Vectors;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;
import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.vector.Float64Vector;

/**
 *
 * @author alex
 */
public class OjaAnalysis implements PrincipleComponentAnalysis {
    private static String NAME = "OjaAnalyser";
    public static double DEFAULT_LAMBDA = 0.001;
    /** learn constant */
    private final Logger logger = Logger.getLogger(OjaAnalysis.class);

    private final double lambda;
    private final Random rand;

    public OjaAnalysis() {
        this(DEFAULT_LAMBDA);
    }

    public OjaAnalysis(double lambda) {
        this.lambda = lambda;
        rand = new Random();
    }

    /**
     *
     * @param analysisData
     * @return
     */
    public Collection<Float64Vector> principleComponents(Collection<Float64Vector> inputData) {
        // working on analysi data
        if( inputData == null || inputData.size() == 0)
            throw new IllegalArgumentException("anaylsisData is null or empty");

        List<Float64Vector> analysisData = Lists.newArrayList(inputData);;
        List<Float64Vector> mainComponents = new ArrayList<Float64Vector>();

        final int dimension = analysisData.get(0).getDimension();

        ComponentReductionTransformator trans = new ComponentReductionTransformator();

        for( int component = 0; component < dimension; component++) {

            Float64Vector omega = Vectors.random(dimension);
            Float64Vector center = Vectors.centerOf(analysisData);

            int range = analysisData.size();

            for(int i=0; i<analysisData.size()*10; i++) {
                Float64Vector randomVec = analysisData.get(rand.nextInt(range));

                randomVec = randomVec.minus(center);
                if( logger.isTraceEnabled() || i%100 == 0 ) {
                    logger.debug("round " + i + ": omega="+omega+", random="+ randomVec);
                }

                omega = refreshOmega(omega,randomVec);

            }
            logger.debug("component " + component + " is " + omega);
           
            // FIXME goes wrong when dim > 2 because of lazy list
            trans.setComponent(omega);
            trans.setTranslation(center.times(-1));

            mainComponents.add(omega);

            analysisData = Lists.transform(analysisData, trans);

        }

        return mainComponents;
        
    }

    private Float64Vector refreshOmega(Float64Vector omega, Float64Vector vec) {
        Float64 scalar = omega.times(vec);
        // w_neu = w + lambda*scalar(vec-scalar*w)
        Float64Vector omegaNeu = omega.plus(vec.minus(omega.times(scalar)).times(scalar.times(lambda)));
        // omegaNeu = omegaNeu.times(1/omegaNeu.normValue());

        return omegaNeu;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
