package de.berlin.fu.inf.pattern.data.kdtree;

import java.util.Comparator;


// XXX we could make this a immutable/functionalstyle object

public class DimensionComparator<D extends Dimensionable<D>> implements Comparator<D> {
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
