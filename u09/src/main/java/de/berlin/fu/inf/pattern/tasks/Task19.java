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
public class Task19 implements Runnable {
    private final Logger logger = Logger.getLogger(Task19.class);

    public void run() {
        logger.warn("this task is not implemented yet");
    }

    public static void main(String args[]) {
        try {
            Threads.run(new Task19());
        } finally {
            Threads.shutdown();
        }
    }
}
