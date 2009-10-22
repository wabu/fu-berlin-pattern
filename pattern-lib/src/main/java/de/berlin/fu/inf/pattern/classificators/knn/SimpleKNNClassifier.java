package de.berlin.fu.inf.pattern.classificators.knn;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import de.berlin.fu.inf.pattern.classificators.Classifer;
import de.berlin.fu.inf.pattern.data.Database;
import de.berlin.fu.inf.pattern.data.Entry;
import de.berlin.fu.inf.pattern.types.Messurable;

/**
 * Simple K-NN classificator
 * @author wabu
 *
 * @param <D> type of objects that will be classified
 * @param <C> type of classes
 */
public class SimpleKNNClassifier<D extends Messurable<D>, C> implements Classifer<D, C> {
	private final Database<D, C> database;
	private final int k;

	@Inject
	public SimpleKNNClassifier(@Named("k-nn") int k, Database<D, C> database) {
		this.database = database;
		this.k = k;
	}

	public boolean add(Entry<D, C> e) {
		return database.add(e);
	}

	public boolean addAll(Collection<? extends Entry<D, C>> c) {
		return database.addAll(c);
	}
	
	public C classify(D data){
		// get k nearest neighbors
		KList<D,C> nN = new KList<D, C>(k, data); 
		for(Entry<D,C> e : database){
			nN.add(e.getData(), e.getClassification());
		}
		
		// get class count
		Map<C,Integer> classes = new HashMap<C, Integer>(k);
		int bestCount = 0;
		C bestClass = null;
		for(C c : nN.getValues()){
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
