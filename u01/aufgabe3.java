 //  Aufgabe 3  //
// // // // // //


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
    // generate data
    Database<DoublePoint, Integer> data 
        = new SimpleDatabase<DoublePoint, Integer>();
    for(Map.Entry<Integer, PointGenerator> e : gens.entrySet()) {
      data.addAll(e.getValue().generateEntries(n, e.getKey()));
    }

    // create classifier with generated data
    Classifer<DoublePoint, Integer> classifier 
       = new SimpleKNNClassifier<DoublePoint, Integer>(k, data);
    
    // classify a random point
    PointGenerator gen = gens.get(klass);
    DoublePoint p = gen.generate(); 
    
    return klass.equals(classifier.classify(p));
  }
  
  public float runTests(int num) {
    // run num indipendent tests
    Random rnd = new Random();
    int succ = 0;
    
    for(int i=0; i<num; i++) {
      if(runTest(rnd.nextInt(2)+1)) {
        succ++;
      }
    }
    return (float)succ/(float)num;
  }
}

