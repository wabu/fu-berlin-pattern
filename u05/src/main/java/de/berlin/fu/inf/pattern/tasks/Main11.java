package de.berlin.fu.inf.pattern.tasks;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.io.FileNotFoundException;
import java.util.Collection;

import org.apache.log4j.Logger;

import de.berlin.fu.inf.pattern.data.Entry;
import de.berlin.fu.inf.pattern.iface.Classifier;
import de.berlin.fu.inf.pattern.iface.DiscriminatingClassifier;
import de.berlin.fu.inf.pattern.iface.SupervisedClassifier;
import de.berlin.fu.inf.pattern.impl.fisher.FisherLinearDiscriminant;
import de.berlin.fu.inf.pattern.impl.kdtree.KDClassificator;
import de.berlin.fu.inf.pattern.util.data.DoubleVector;
import de.berlin.fu.inf.pattern.util.gen.Generator;
import de.berlin.fu.inf.pattern.util.gen.MultiNormalGenerator;
import de.berlin.fu.inf.pattern.util.test.IntClassifierTest;

/**
 * K-NN vs. Fishers Discriminant
 * 
 * 7 Runs for each 
 * 
 * 
 * @author covin, wabu
 */
public class Main11 {

    private Logger logger = Logger.getLogger(Main11.class);

    private final int maxDimension = 10;

    private final int dim = 2;
    private final int runs = 17;
    private final int elements = 10000;
    private final int tries = 10000;

    private final int tests = 100000;

    private final int steps[] = {100, 1000, 10000, 100000};
    private final double delta[] = {0.1, 0.0, -0.01, -0.005};

    @SuppressWarnings("unchecked")
    public void run() {
        double classificationRateFisher = 0.0d;
        double rate;
        RandomFish fish;

        for (int run = 1; run < this.runs; run++) {
            int faild = 0;
            logger.debug("run: " + run);
            // generate random distribution
            Generator<DoubleVector> gen1 = new MultiNormalGenerator(dim, 2d);
            Generator<DoubleVector> gen2 = new MultiNormalGenerator(dim, 2d);

            logger.debug("distribution 1 is "+gen1);
            logger.debug("distribution 2 is "+gen2);

            Classifier<DoubleVector, Integer> fisher = trainFisher(elements, dim, gen1, gen2);
            rate = runTest(tests, fisher, gen1, gen2);
            logger.debug("fisher classified " + rate);

            outer:
            for(int i=0; i<tries; i++) {
                if((i+1)%1000 == 0){
                    logger.info("tested "+(i+1)+" random prjections");
                }
                fish = RandomFish.generate2dFish();
                double r=0;
                for(int k=0; k<steps.length; k++){
                    r = runTest(steps[k], fish, gen1, gen2);
                    if(r+delta[k] < rate ) {
                        continue outer;
                    }
                }
                logger.info(i+": found better projection as fisher ("+r+" vs "+rate+"):");
                logger.info("     fisher: "+fisher);
                logger.info("     random: "+fish);
                faild++;
                if(faild>5){
                    logger.warn("fisher "+fisher+" is very bad for this data");
                    break;
                }
            }
        }
    }

    private Classifier<DoubleVector, Integer> trainKNN(int size, Generator<DoubleVector> gen1, Generator<DoubleVector> gen2) {
        /// init KD-Classifier
        SupervisedClassifier<DoubleVector, Integer> kdClassifier =
                new KDClassificator<DoubleVector, Integer>();
        logger.debug("trainging "+kdClassifier+" with "+size+" datapoints");

        // We need to transform our data to entry-sets
        Collection<Entry<DoubleVector, Integer>> classes =
            Lists.newArrayList( Iterables.concat(
               gen1.getEntryGenerator(0, size),
               gen2.getEntryGenerator(1, size))
            );
        // now train
        kdClassifier.train(classes);
        return kdClassifier;
    }

    private Classifier<DoubleVector, Integer> trainFisher(int size, int dim, Generator<DoubleVector> gen1, Generator<DoubleVector> gen2) {
        DiscriminatingClassifier<DoubleVector> fisher =
                new FisherLinearDiscriminant<DoubleVector>(dim);
        logger.debug("trainging "+fisher+" with "+size+" datapoints");
        fisher.train(
                Lists.newArrayList(gen1.getGenerator(size)),
                Lists.newArrayList(gen2.getGenerator(size)));
        return fisher;
    }

    public double runTest(int num, Classifier<DoubleVector, Integer> c, Generator<DoubleVector>... gens) {
        logger.trace("running "+num+" tests on "+c);
        IntClassifierTest<DoubleVector> test = new IntClassifierTest<DoubleVector>(c);
        return test.runTest(num, gens);
    }

    public static void main(String[] argv) throws FileNotFoundException, InterruptedException {
        new Main11().run();
    }
}
