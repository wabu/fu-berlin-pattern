package de.berlin.fu.inf.pattern.util.mesure;

import java.util.Comparator;

import de.berlin.fu.inf.pattern.util.types.Messurable;

public class DistanceComperator<E> implements Comparator<E> {
	private final Messurable<E> ref;
	
	public DistanceComperator(Messurable<E> refPoint) {
		this.ref = refPoint;
	}
	public DistanceComperator(Messure<E> messure, E refPoint) {
		this(messure.getMessurable(refPoint));
	}

	public int compare(E o1, E o2) {
		if(o1.equals(o2)){
			return 0;
		}
		
		double d1 = ref.getDistance(o1);
		double d2 = ref.getDistance(o2);

        int cmp = Double.compare(d1,d2);

		if(cmp == 0){
			int h1 = o1.hashCode();
			int h2 = o2.hashCode();
			if(h1 == h2){
				return o1.toString().compareTo(o2.toString());
			}
			return h2 - h1;
		} else {
            return cmp;
        }
	}
}
