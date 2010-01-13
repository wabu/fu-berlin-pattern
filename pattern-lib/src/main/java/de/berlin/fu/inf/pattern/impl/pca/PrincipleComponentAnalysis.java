/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.impl.pca;

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
public interface PrincipleComponentAnalysis {
    /**
     *
     * @return
     */
    public Collection<Float64Vector> principleComponents(Collection<Float64Vector> vectors);
}
