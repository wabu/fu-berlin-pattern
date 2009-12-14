/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks;

import de.berlin.fu.inf.pattern.util.Threads;
import org.apache.log4j.Logger;

/**
 *
 * @author wabu
 */
public class Main {
    private final static Logger logger = Logger.getLogger(Main.class);
    private static Runnable[] tasks = {
        new Task17(),
    };

    public static void main(String args[]) throws InterruptedException {
        try{
            for (Runnable t : tasks) {
                logger.info("running "+t.getClass().getSimpleName());
                try {
                    t.run();
                } catch(Exception e) {
                    logger.error("execution of "+t.getClass()+" failed", e);
                }
            }
        } finally {
            Threads.shutdown();
        }
    }
}
