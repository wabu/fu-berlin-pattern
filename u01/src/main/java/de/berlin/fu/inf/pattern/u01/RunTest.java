package de.berlin.fu.inf.pattern.u01;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.berlin.fu.inf.pattern.classificators.Classifer;
import de.berlin.fu.inf.pattern.classificators.knn.SimpleKNNClassifier;
import de.berlin.fu.inf.pattern.data.Database;
import de.berlin.fu.inf.pattern.data.SimpleDatabase;

public class RunTest {
	private final int n;
	private final int k;
	
	private final Map<Integer, PointGenerator> gens;
	
	public RunTest(int numRep, int k) {
		this.n = numRep;
		this.k = k;
		this.gens = new HashMap<Integer, PointGenerator>();
	}
	
	public void addKlass(Integer name, double median, double deviation) {
		gens.put(name, new PointGenerator(median, deviation));
	}
	
	
	public boolean runTest(Integer klass){
		Database<DoublePoint, Integer> data = new SimpleDatabase<DoublePoint, Integer>();
		for(Map.Entry<Integer, PointGenerator> e : gens.entrySet()) {
			data.addAll(e.getValue().generateEntries(n, e.getKey()));
		}
		
		Classifer<DoublePoint, Integer> classifier = new SimpleKNNClassifier<DoublePoint, Integer>(k, data);
		
		PointGenerator gen = gens.get(klass);
		DoublePoint p = gen.generate(); 
		
		return klass.equals(classifier.classify(p));
	}
	
	public float runTests(int num) {
		Random rnd = new Random();
		int succ = 0;
		
		for(int i=0; i<num; i++) {
			if(runTest(rnd.nextInt(2)+1)) {
				succ++;
			}
		}
		return (float)succ/(float)num;
	}
	
	public static void main(String[] args) {
		for(int k=1; k<8; k++){
			for(int n=0; n<21; n++){
				
				int num = 5+n*10;
				
				RunTest e = new RunTest(num, k);
				e.addKlass(1, 1, 1);
				e.addKlass(2, 2, 2);
				System.out.println(k+"\t"+num+"\t"+e.runTests(10000));
			}
			System.out.println();
		}
	}
}
