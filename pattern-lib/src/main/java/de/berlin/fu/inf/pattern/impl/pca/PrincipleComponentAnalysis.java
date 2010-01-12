/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.pca;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.log4j.Logger;
import org.jscience.mathematics.vector.Float64Vector;

/**
 * abstract frame class for principle component analysis. implement any specialized
 * algorithm in <code>calculateMainComponent</code> method
 *
 * @author covin
 */
public abstract class PrincipleComponentAnalysis {
    private final Logger logger = Logger.getLogger(PrincipleComponentAnalysis.class);

    private final Collection<Float64Vector>        origin;
    private final List<Float64Vector>              principleComponents;
    // counter for component analysis
    private int                                    currentComponent;
    /** expected number of components */
    private final int                              dimension;
    private final ComponentReductionTransformator  trans = new ComponentReductionTransformator();

    protected List<Float64Vector> analysisData;

    public PrincipleComponentAnalysis(Collection<Float64Vector> floatVectors) {
        if( floatVectors == null || floatVectors.size() == 0)
            throw new IllegalArgumentException("parameter floatVectors is null or empty");
        this.principleComponents = new ArrayList<Float64Vector>();
        this.analysisData = new ArrayList<Float64Vector>(floatVectors);
        this.origin = floatVectors;
        // center data

        this.currentComponent = 0;
        // first vector indicates expected dimension
        this.dimension = floatVectors.iterator().next().getDimension();
    }

    public boolean hastNextPrincipleComponent() {
        return currentComponent < dimension;
    }

    @Nullable
    public Float64Vector nextPrincipleComponent() {

        if( hastNextPrincipleComponent() ) {
            currentComponent++;
            Float64Vector vec = this.calculateMainComponent();
            // norm
            vec = vec.times(vec.norm());
            this.principleComponents.add(vec);
            logger.info("component " + currentComponent + ": " + vec );
            this.trans.setComponent(vec);
            analysisData = Lists.transform(analysisData, trans);

            return vec;
        } else {
            return null;
        }
    }

    protected abstract Float64Vector calculateMainComponent();

    int getDimension() {
        return dimension;
    }

    /**
     *
     * @return
     */
    public Collection<Float64Vector> principleComponents() {
        return Collections.unmodifiableCollection(principleComponents);
    }
}
