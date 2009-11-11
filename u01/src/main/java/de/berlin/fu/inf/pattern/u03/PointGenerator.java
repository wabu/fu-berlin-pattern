package de.berlin.fu.inf.pattern.u03;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.berlin.fu.inf.pattern.data.Entry;

public class PointGenerator {
	private final Random rnd = new Random();
	private final double median;
	private final double deviation;
	
	public PointGenerator(double median, double deviation) {
		this.median = median;
		this.deviation = deviation;
	}
	
	public DoublePoint generate() {
		return new DoublePoint(rnd.nextGaussian()*deviation+median);
	}
	
	public List<DoublePoint> generatePoints(int n){
		List<DoublePoint> ls = new ArrayList<DoublePoint>(n);
		for(int i=0; i<n; i++){
			ls.add(generate());
		}
		return ls;
	}
	
	public <C> List<Entry<DoublePoint, C>> generateEntries(int n, C klass) {
		List<Entry<DoublePoint, C>> ls = new ArrayList<Entry<DoublePoint, C>>(n);
		for(int i=0; i<n; i++){
			ls.add( new Entry<DoublePoint, C>(generate(), klass));
		}
		return ls;
	}
}
