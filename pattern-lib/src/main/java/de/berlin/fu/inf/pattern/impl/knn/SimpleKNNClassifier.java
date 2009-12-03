package de.berlin.fu.inf.pattern.impl.knn;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.berlin.fu.inf.pattern.data.Database;
import de.berlin.fu.inf.pattern.data.Entry;
import de.berlin.fu.inf.pattern.data.SimpleDatabase;
import de.berlin.fu.inf.pattern.iface.SupervisedClassifier;
import de.berlin.fu.inf.pattern.util.types.Messurable;

/**
 * Simple K-NN classificator
 * @author wabu
 *
 * @param <D> type of objects that will be classified
 * @param <C> type of classes
 */
public class SimpleKNNClassifier<D extends Messurable<D>, C> implements SupervisedClassifier<D, C> {
	private final Database<D, C> database;
	private final int k;

	public SimpleKNNClassifier(int k, Database<D, C> database) {
		this.database = database;
		this.k = k;
	}

    public SimpleKNNClassifier() {
        this(1, new SimpleDatabase<D,C>());
    }
	
	public double train(Collection<Entry<D, C>> trainingData) {
		this.database.addAll(trainingData);
        return Double.NaN;
	}

	public C classify(D data){
		// get the k neareast neighbours out of the database
		KList<D,C> nearestNeighbours = new KList<D, C>(k, data); 
		for(Entry<D,C> e : database){
			nearestNeighbours.add(e.getData(), e.getClassification());
		}
		
		// get class with best hits
		Map<C,Integer> classes = new HashMap<C, Integer>(k);
		int bestCount = 0;
		C bestClass = null;
		for(C c : nearestNeighbours.getValues()){
			int count = 1;
			if(classes.containsKey(c)) {
				count += classes.get(c);
			}
			classes.put(c, count+1);
			
			if(bestCount < count){
				bestCount = count;
				bestClass = c;
			}
		}
		
		return bestClass;
	}

}
