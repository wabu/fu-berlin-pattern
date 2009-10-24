package de.berlin.fu.inf.pattern.classificators.kd;

import java.util.Collection;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import de.berlin.fu.inf.pattern.classificators.Classifier;
import de.berlin.fu.inf.pattern.classificators.DimensionableEntry;
import de.berlin.fu.inf.pattern.classificators.Entry;
import de.berlin.fu.inf.pattern.data.kdtree.Dimensionable;
import de.berlin.fu.inf.pattern.data.kdtree.KDTree;
import de.berlin.fu.inf.pattern.data.kdtree.KDTreeImpl;

public class KDClassificator<D extends Dimensionable<D>, C> implements Classifier<D, C> {

	private final KDTree<DimensionableEntry<D, C>> kdTree;
	
	public KDClassificator(){
		this.kdTree = new KDTreeImpl<DimensionableEntry<D, C>>();
	}
	
	@SuppressWarnings("unchecked") /* can't create generic array */
	public void train(Collection<Entry<D, C>> trainingData) {
		DimensionableEntry<D, C>[] kdEntries = (DimensionableEntry<D, C>[]) 
			Collections2.transform(trainingData, new Function<Entry<D,C>, DimensionableEntry<D, C>>() {
				public DimensionableEntry<D, C> apply(Entry<D, C> e) {
					return new DimensionableEntry<D, C>(e.getData(), e.getClassification());
				}
			}).toArray(new DimensionableEntry<?, ?>[trainingData.size()]);
		kdTree.buildTree(kdEntries);
	}

	public C classify(D data) {
		return kdTree.findKnearestValues(new DimensionableEntry<D, C>(data, null)).getClassification();
	}


}
