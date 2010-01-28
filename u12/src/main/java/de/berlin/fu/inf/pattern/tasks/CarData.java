/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks;

import de.berlin.fu.inf.pattern.data.Featured;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wabu
 */
public class CarData implements Featured {
    private final Enum klass;
    private final List<Enum> attributes;

    public CarData(String value) throws IndexOutOfBoundsException, IllegalArgumentException{
        String[] vals = value.split(",");
        int i=0;

        attributes = new ArrayList<Enum>(vals.length-1);
        attributes.add(Buying.valueOf(foo(vals[i++])));
        attributes.add(Maint.valueOf(foo(vals[i++])));
        attributes.add(Doors.valueOf(foo(vals[i++])));
        attributes.add(Persons.valueOf(foo(vals[i++])));
        attributes.add(LugBoot.valueOf(foo(vals[i++])));
        attributes.add(Safty.valueOf(foo(vals[i++])));

        klass = Acceptability.valueOf(foo(vals[i++]));
    }

    public Enum getKlass() {
        return klass;
    }

    @Override
    public Enum getFeature(int i) {
        return attributes.get(i);
    }
    @Override
    public int getSize() {
        return attributes.size();
    }


    private String foo(String s) {
        if(Character.isJavaIdentifierStart(s.charAt(0))) {
            return s;
        } else {
            return "$"+s;
        }
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
