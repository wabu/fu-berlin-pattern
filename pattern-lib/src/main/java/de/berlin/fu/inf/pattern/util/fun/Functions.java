/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.util.fun;

import java.util.ArrayList;
import java.util.List;
import org.jscience.mathematics.structure.Field;
import org.jscience.mathematics.vector.DenseVector;
import org.jscience.mathematics.vector.Vector;

/**
 *
 * @author wabu
 */
public class Functions {
    public static <X,Y> Funct<X[], Y[]> arrayLift(final Funct<X,Y> f, final Y[] type) {
        return new Funct<X[],Y[]>() {
            public Y[] apply(X[] xs) {
                List<Y> ys = new ArrayList<Y>(xs.length);
                for(X x : xs) {
                    ys.add(f.apply(x));
                }
                return ys.toArray(type);
            }
        };
    }

    public static <X,Y> Funct<List<X>, List<Y>> listLift(final Funct<X,Y> f) {
        return new Funct<List<X>,List<Y>>() {
            public List<Y> apply(List<X> xs) {
                List<Y> ys = new ArrayList<Y>(xs.size());
                for(X x : xs) {
                    ys.add(f.apply(x));
                }
                return ys;
            }
        };
    }

    public static <X,Y,Z> Funct<X,Z> compose(final Funct<X,Y> f, final Funct<Y,Z> g){
        return new Funct<X, Z>() {
            public Z apply(X x) {
                return g.apply(f.apply(x));
            }
        };
    }

    public static <X extends Field<X>, Y extends Field<Y>> 
            Funct<Vector<X>, Vector<Y>> vectorLift(final Funct<X,Y> f) {
        return new Funct<Vector<X>,Vector<Y>>() {
            public Vector<Y> apply(Vector<X> xv) {
                List<Y> ys = new ArrayList<Y>(xv.getDimension());
                for(int i=0; i<xv.getDimension(); i++) {
                    ys.add(f.apply(xv.get(i)));
                }
                return DenseVector.valueOf(ys);
            }
        };
    }
}
