/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.util.collect;

import com.google.common.base.Function;
/**
 *
 * @author alex
 */
public class String2DoubleTransformator implements Function<String, Double>{

    public Double apply(String x) {
        try {
            return Double.parseDouble(x);
        } catch( NumberFormatException nfEx) {
            nfEx.printStackTrace();
            return Double.NaN;
        }
    }
}
