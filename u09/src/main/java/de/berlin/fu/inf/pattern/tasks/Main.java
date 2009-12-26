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
public final class Main {
    private Main() {}

    private final static Logger LOG = Logger.getLogger(Main.class);
    private static Runnable[] tasks = {
        new TaskX(),
    };

    public static void main(String args[]) throws InterruptedException {
        try {
            for (Runnable t : tasks) {
                LOG.info("running "+t.getClass().getSimpleName());
                try {
                    t.run();
                } catch(Exception e) {
                    LOG.error("execution of "+t.getClass()+" failed", e);
                }
            }
        } finally {
            Threads.shutdown();
        }
    }
}
