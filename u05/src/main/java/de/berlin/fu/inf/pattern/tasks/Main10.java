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
import java.io.PrintStream;

/**
 * K-NN vs. Fishers Discriminant
 * 
 * 7 Runs for each 
 * 
 * 
 * @author covin, wabu
 */
public class Main10 {

    private Logger logger = Logger.getLogger(Main10.class);

    private final int[] N = {1,5,10, 15, 20, 30, 50, 100, 250, 500, 1000, 2000, 3000, 5000, 7500, 10000};

    private final int maxDimension = 10;
    // we use more runs to avoid high error for "bad" or "good" distingushable distributions
    private final int runs = 7;
    private final int tests = 5000;

    private String knnFile = "knnData.gp";
    private String fisherFile = "fisherData.gp";
    private PrintStream knnOutput = null;
    private PrintStream fisherOutput = null;

    public void run() throws FileNotFoundException, InterruptedException {
        logger.info("======= creating output file "+knnFile);
        knnOutput = new PrintStream(knnFile);
        logger.info("======= creating output file "+fisherFile);
        fisherOutput = new PrintStream(fisherFile);
        Thread.sleep(5000);

        for (int dimension = 2; dimension <= maxDimension; dimension++) {
            for (int number : N) {
                if (number >= maxDimension) { // avoid fisher sigularity
                    this.run(dimension, number);
                }
            }
            knnOutput.println();
            fisherOutput.println();
        }
        knnOutput.close();
        fisherOutput.close();
    }

    /**
     * concrete run for specified size/parameters
     * @param dim
     * @param elements
     */
    @SuppressWarnings({"unchecked"})
    private void run(int dim, int elements) {
        double classificationRateKNN = 0.0d;
        double classificationRateFisher = 0.0d;
        double rate;
        logger.info("======= DIM=" + dim + " ELEMTENTS=" + elements);

        for (int run = 1; run < this.runs; run++) {
            logger.debug("run: " + run);
            // generate random distribution
            Generator<DoubleVector> gen1 = new MultiNormalGenerator(dim);
            Generator<DoubleVector> gen2 = new MultiNormalGenerator(dim);

            Classifier<DoubleVector, Integer> knn = trainKNN(elements, gen1, gen2);
            rate = runTest(tests, knn, gen1, gen2);
            logger.debug("KNN classified " + rate);

            classificationRateKNN += rate;

            Classifier<DoubleVector, Integer> fisher = trainFisher(elements, dim, gen1, gen2);
            rate = runTest(tests, fisher, gen1, gen2);
            logger.debug("Fisher classified " + rate);
            classificationRateFisher += rate;
        } // end for(run)
        logger.info("======= KNN-rate = " + classificationRateKNN / runs
                + "\t for " + elements + " in " + dim + "d");
        logger.info("======= Fishrate = " + classificationRateFisher / runs
                + "\t for " + elements + " in " + dim + "d");

        knnOutput.println(dim + " " + elements + " " + classificationRateKNN
                / runs);
        fisherOutput.println(dim + " " + elements + " "
                + classificationRateFisher / runs);
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
        logger.debug("running "+num+" tests on "+c);
        IntClassifierTest<DoubleVector> test = new IntClassifierTest<DoubleVector>(c);
        return test.runTest(num, gens);
    }

    public static void main(String[] argv) throws FileNotFoundException, InterruptedException {
        new Main10().run();
    }
}
