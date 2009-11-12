package de.berlin.fu.inf.pattern.tasks;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;

import de.berlin.fu.inf.pattern.impl.fisher.FisherLinearDiscriminant;
import de.berlin.fu.inf.pattern.tasks.u02.data.Digit;
import de.berlin.fu.inf.pattern.tasks.u02.data.DigitPoint;
import de.berlin.fu.inf.pattern.tasks.u02.data.DigitReader;
import de.berlin.fu.inf.pattern.util.data.DoubleVector;

public class FishersPenController {
	Logger log = Logger.getLogger(FishersPenController.class);
	
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
	
	public Collection<Digit> readDigits(String training) throws IOException{
		DigitReader reader = new DigitReader();
		return reader.readDigits(training);
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
	
	public List<FisherLinearDiscriminant<DoubleVector>> trainIncrementalClassification(Collection<Digit> digits){
		List<FisherLinearDiscriminant<DoubleVector>> classifiers = new LinkedList<FisherLinearDiscriminant<DoubleVector>>();
		Collection<Digit> rest = digits;
		log.trace("size of rest is "+rest.size());
		
		// only need 9 clasifiers, rest is 9
		for(int i=0; i<9; i++) {
			log.debug("creating fisher for "+i);
			
			FisherLinearDiscriminant<DoubleVector> fisher = trainSingleClassification(i, rest);
			classifiers.add(fisher);
			
			rest = Collections2.filter(rest, getNumFilter(i).not());
			log.trace("size of rest is "+rest.size());
		}
		
		assert classifiers.size() == 9;
		
		return classifiers;
	}
	
	public float runIncrementalClassification(List<FisherLinearDiscriminant<DoubleVector>> fishers, final Collection<Digit> digits) {
		Collection<Digit> rest = digits;
		List<Collection<Digit>> classified = new LinkedList<Collection<Digit>>();
		
		int i = 0;
		for(final FisherLinearDiscriminant<DoubleVector> fisher : fishers) {
			Collection<Digit> inClass = Collections2.filter(rest, new RunSingleClassifierFilter(fisher));
			log.debug("classifed "+inClass.size()+" digits as "+i++);
			classified.add(inClass);
			
			rest = Collections2.filter(rest, new RunSingleClassifierFilter(fisher).not());
		}
		classified.add(rest);
		
		int hits=0, total=0;
		i = 0;
		for(Collection<Digit> cs : classified) {
			int h = 0, t=0;
			for(Digit d : cs){
				if(d.getGroup()==i) {
					h++;
				}
				t++;
			}
			log.debug("success rate for "+i+" is "+((float)h/(float)t));
			i++;
			hits += h;
			total += t;
		}
		return (float)hits/(float)total;
	}
	
	private class RunSingleClassifierFilter implements Predicate<Digit> {
		private FisherLinearDiscriminant<DoubleVector> fisher;
		
		public RunSingleClassifierFilter(FisherLinearDiscriminant<DoubleVector> fisher) {
			this.fisher = fisher;
		}

		public boolean apply(Digit digit) {
			return fisher.classify(getDig2Vec().apply(digit))==0;
		}
		public Predicate<Digit> not() {
			return Predicates.not(this);
		}
	}
}
