package de.berlin.fu.inf.pattern.impl.kdtree;

import javax.annotation.Nullable;

import de.berlin.fu.inf.pattern.data.Entry;
import de.berlin.fu.inf.pattern.util.types.Dimensionable;

public class DimensionableEntry<D extends Dimensionable<D>, C> extends Entry<D, C>  implements Dimensionable<DimensionableEntry<D,C>> {

	public DimensionableEntry(D data, @Nullable C klass) {
		super(data, klass);
	}

	public int compareInDimension(DimensionableEntry<D,C> dimObject, int dimension) {
		return getData().compareInDimension(dimObject.getData(), dimension);
	}

	public double getDistance(DimensionableEntry<D,C> o) {
		return getData().getDistance(o.getData());
	}

	public double getDistanceInDimension(DimensionableEntry<D,C> dimObject, int dimension) {
		return getData().getDistanceInDimension(dimObject.getData(), dimension);
	}
}
