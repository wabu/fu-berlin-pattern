/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks;

import de.berlin.fu.inf.pattern.data.feature.Feature;
import de.berlin.fu.inf.pattern.data.feature.FeatureImpl;
import de.berlin.fu.inf.pattern.data.feature.FeatureVector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author wabu
 */
public class CarData implements FeatureVector {
    private final Acceptability klass;
    private final List<Feature> attributes;

    public CarData(String value) throws IndexOutOfBoundsException, IllegalArgumentException{
        String[] vals = value.split(",");
        int i=0;

        attributes = new ArrayList<Feature>(vals.length-1);
        // can't abstract funcalls to class without maior pain in da ass
        attributes.add(
                new FeatureImpl(Buying.values(), Buying.valueOf(foo(vals[i++]))));
        attributes.add(
                new FeatureImpl(Maint.values(), Maint.valueOf(foo(vals[i++]))));
        attributes.add(
                new FeatureImpl(Doors.values(), Doors.valueOf(foo(vals[i++]))));
        attributes.add(
                new FeatureImpl(Persons.values(), Persons.valueOf(foo(vals[i++]))));
        attributes.add(
                new FeatureImpl(LugBoot.values(), LugBoot.valueOf(foo(vals[i++]))));
        attributes.add(
                new FeatureImpl(Safty.values(), Safty.valueOf(foo(vals[i++]))));

        klass = Acceptability.valueOf(foo(vals[i++]));
    }


    private String foo(String s) {
        if(Character.isJavaIdentifierStart(s.charAt(0))) {
            return s;
        } else {
            return "$"+s;
        }
    }

    public Acceptability getKlass() {
        return klass;
    }

    @Override
    public int getSize() {
        return attributes.size();
    }

    @Override
    public Feature getFeature(int i) {
        return attributes.get(i);
    }

    @Override
    public List<Feature> getFeatures() {
        return Collections.unmodifiableList(attributes);
    }

    public static enum Acceptability {
        unacc, acc, good, vgood
    }

    public static enum Buying {
        vhigh, high, med, low
    }
    public static enum Maint {
        vhigh, high, med, low
    }
    public static enum Doors {
        $2, $3, $4, $5more
    }
    public static enum Persons {
        $2, $4, more
    }
    public static enum LugBoot {
        small, med, big
    }
    public static enum Safty {
        low, med, high
    }
}
