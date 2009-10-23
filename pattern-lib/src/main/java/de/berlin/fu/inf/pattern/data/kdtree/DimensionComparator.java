package de.berlin.fu.inf.pattern.data.kdtree;

import java.util.Comparator;



public class DimensionComparator implements Comparator<Dimensionable> {

	private int currentDimension;

	public int compare(Dimensionable o1, Dimensionable o2) {
		return o1.compareInDimension(o2, currentDimension);
	}

	
	public int getCurrentDimension() {
		return currentDimension;
	}

	public void setCurrentDimension(int currentDimension) {
		this.currentDimension = currentDimension;
	}
	
	
	
	
}
