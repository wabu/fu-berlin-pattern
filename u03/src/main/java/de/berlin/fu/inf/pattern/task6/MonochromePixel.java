package de.berlin.fu.inf.pattern.task6;

import de.berlin.fu.inf.pattern.classificators.kmeans.Vectorable;

public class MonochromePixel implements Vectorable {

	private final int x;
	private final int y;
	
	private int color;
	
	
	
	public MonochromePixel(int x, int y, int color) {
		super();
		this.x = x;
		this.y = y;
		this.color = color;
	}

	
	public double[] getVectorData() {
		return new double[]{color};
	}
		
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}


	public int getX() {
		return x;
	}


	public int getY() {
		return y;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + color;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MonochromePixel other = (MonochromePixel) obj;
		if (color != other.color)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "MonochromPixel [color=" + color + ", x=" + x + ", y=" + y + "]";
	}

}
