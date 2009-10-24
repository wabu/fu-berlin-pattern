package de.berlin.fu.inf.pattern.data.kdtree;

import java.util.Comparator;



public class DimensionComparator<D extends Dimensionable<D>> implements Comparator<D> {

	private int currentDimension;

	public int compare(D o1, D o2) {
		return o1.compareInDimension(o2, currentDimension);
	}

	
	public int getCurrentDimension() {
		return currentDimension;
	}

	public void setCurrentDimension(int currentDimension) {
		this.currentDimension = currentDimension;
	}
	
	
	
	
}
