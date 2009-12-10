/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks;

import org.apache.log4j.Logger;

/**
 *
 * @author wabu
 */
public class TaskX implements Runnable {
    private final Logger logger = Logger.getLogger(TaskX.class);

    public void run() {
        logger.warn("this task is not implemented yet");
    }
}
