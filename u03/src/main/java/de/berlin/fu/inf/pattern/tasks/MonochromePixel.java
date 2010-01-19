package de.berlin.fu.inf.pattern.tasks;
import de.berlin.fu.inf.pattern.util.types.Vectorable;

public class MonochromePixel implements Vectorable {
	private final int x;
	private final int y;
	
	private int color;
	private double[] val;
	
	
	public MonochromePixel(int x, int y, int color) {
		super();
		this.x = x;
		this.y = y;
		this.color = color;
		this.val = new double[]{(double)color/255d};
	}

	
	public double[] getVectorData() {
		return val;
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

    public int getDimension() {
        return val.length;
    }

    public double get(int i) {
        return val[i];
    }
}
