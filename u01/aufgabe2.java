 //  Aufgabe 2  //
// // // // // //

/**
 * collect values of the k best keys
 * @author wabu
 *
 * @param <K> key type
 * @param <V> value type
 */
class KList<K,V> {
  final NavigableMap<K,V> map;

  /**
   * use distance to a reference point as definition of best
   * @param k
   * @param refPoint
   */
  public KList(int k, Messurable<K> refPoint) {
    this(k, new DistanceComperator<K>(refPoint));
  }

  /**
   * add value to collection and remove value of worst key, 
   * iff the key is one of the k best keys 
   * @param key
   * @param value
   */
  public void add(final K key, V value){
    // put data point into map
    map.put(key,value);
    if(map.size() > k){
      // remove worst data point
      V removed = map.remove(map.lastKey());
      assert removed != null;
    }
  }
}

/**
 * Simple K-NN classificator
 * @author wabu
 *
 * @param <D> type of objects that will be classified
 * @param <C> type of classes
 */
public class SimpleKNNClassifier<D extends Messurable<D>, C> {
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

