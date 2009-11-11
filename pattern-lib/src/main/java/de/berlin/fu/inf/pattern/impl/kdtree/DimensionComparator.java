package de.berlin.fu.inf.pattern.impl.kdtree;

import java.util.Comparator;

import de.berlin.fu.inf.pattern.util.types.Dimensionable;
import edu.umd.cs.findbugs.annotations.SuppressWarnings;


// XXX we could make this a immutable/functionalstyle object

@SuppressWarnings("SE_COMPARATOR_SHOULD_BE_SERIALIZABLE")
public class DimensionComparator<D extends Dimensionable<D>> implements Comparator<D>{
	final private int maxDimension;
	private int currentDimension;

	public DimensionComparator(int maxDimension) {
		this.maxDimension = maxDimension;
		this.currentDimension = 0;
	}

	public int compare(D o1, D o2) {
		return o1.compareInDimension(o2, currentDimension);
	}

	
	public int getCurrentDimension() {
		return currentDimension;
	}

	public void setCurrentDimension(int currentDimension) {
		this.currentDimension = currentDimension;
	}
	
	public DimensionComparator<D> nextDimension() {
		this.currentDimension = (this.currentDimension + 1)%maxDimension;
		return this;
	}
	
	public DimensionComparator<D> previousDimension() {
		this.currentDimension = (this.currentDimension + maxDimension - 1)%maxDimension;
		return this;
	}
}
