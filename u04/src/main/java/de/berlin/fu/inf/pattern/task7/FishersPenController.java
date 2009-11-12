package de.berlin.fu.inf.pattern.task7;

import java.net.URL;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;

import de.berlin.fu.inf.pattern.impl.fisher.FisherLinearDiscriminant;
import de.berlin.fu.inf.pattern.u02.data.Digit;
import de.berlin.fu.inf.pattern.u02.data.DigitPoint;
import de.berlin.fu.inf.pattern.u02.data.DigitReader;
import de.berlin.fu.inf.pattern.util.data.DoubleVector;

public class FishersPenController {
	private class Dig2Vec implements Function<Digit, DoubleVector> {
		public DoubleVector apply(Digit dig) {
			List<DigitPoint> points = dig.getPoints();
			double[] vals = new double[points.size()*2];
			int i = 0;
			for(DigitPoint p : points) {
				vals[i++] = p.getX();
				vals[i++] = p.getY();
			}
			return new DoubleVector(vals);
		}
	}
	private class NumFilter implements Predicate<Digit> {
		private final int num;
		public NumFilter(int num) {
			this.num = num;
		}
		public boolean apply(Digit input) {
			return input.getGroup() == num;
		}
		public Predicate<Digit> not() {
			return Predicates.not(this);
		}
	}
	private final Dig2Vec dig2vec = new Dig2Vec();
	public Dig2Vec getDig2Vec(){
		return dig2vec;
	}
	public NumFilter getNumFilter(int n){
		return new NumFilter(n);
	}
	
	public Collection<Digit> readDigits(URL url){
		DigitReader reader = new DigitReader();
		return reader.readDigits(url.getFile());
	}
	
	
	public FisherLinearDiscriminant<DoubleVector> trainSingleClassification(int num, Collection<Digit> digits) {
		if(digits.isEmpty()) {
			throw new IllegalArgumentException("Can't train Fisher on empty collection");
		}
		
		int dim = digits.iterator().next().getPoints().size()*2;
		FisherLinearDiscriminant<DoubleVector> fisher = new FisherLinearDiscriminant<DoubleVector>(dim);
		
		Collection<DoubleVector> inClass = 
			Collections2.transform(
					Collections2.filter(digits, getNumFilter(num)),
					getDig2Vec()); 
		Collection<DoubleVector> beyondClass = 
			Collections2.transform(
					Collections2.filter(digits, getNumFilter(num).not()),
					getDig2Vec()); 
		
		fisher.train(inClass, beyondClass);
		return fisher;
	}
	
	public float runSingleClassification(int num, FisherLinearDiscriminant<DoubleVector> fisher, Collection<Digit> digits) {
		int hits=0, total=0;
		for(Digit d : digits) {
			DoubleVector vec = getDig2Vec().apply(d);
			int c = fisher.classify(vec);
			
			if((d.getGroup() == num) == (c==0)){
				hits++;
			}
			total++;
		}
		return (float)hits/(float)total;
	}
}
