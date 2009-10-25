package de.berlin.fu.inf.pattern.classificators;

import javax.annotation.Nullable;

import com.google.common.base.Function;

/**
 * Object to store a classified data entry
 * @author wabu
 *
 * @param <D> data of classified object
 * @param <C> class of the object
 */
public class Entry<D,C> {
	final D data;
	@Nullable final C classification;
	
	public Entry(D data, @Nullable C klass) {
		this.data = data;
		this.classification = klass;
	}
	
	public D getData() {
		return data;
	}
	
	@Nullable
	public C getClassification() {
		return classification;
	}
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Entry<?,?>)) {
			return false;
		}
		Entry<?,?> entry = (Entry<?, ?>) obj;
		return data.equals(entry.data) && classification.equals(entry.classification) ;
	}
	
	@Override
	public int hashCode() {
		return data.hashCode() ^ classification.hashCode();
	}

	private static class DataFunction<D,C> implements Function<Entry<D,C>, D> {
		public D apply(Entry<D, C> entry) {
			return entry.data;
		}
	}	
	
	public static <D,C> Function<Entry<D,C>, D> getDataFunction(){
		return new DataFunction<D, C>();
	}
	
	@Override
	public String toString() {
		String cs;
		if(classification == null){
			cs = "??";
		} else {
			cs = classification.toString();
		}
		return data.toString() +":" + cs;
	}
}
