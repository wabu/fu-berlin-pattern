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
public class Task26 implements Runnable {
    private final Logger logger = Logger.getLogger(Task26.class);
    private final String resource = "car.data";

    @Override
    public void run() {
        CarDataReader data = new CarDataReader();
        logger.info("reading data from "+resource);
        try {
            data.addDataFromResource(resource);
        } catch (Exception ex) {
            logger.error("reading "+resource, ex);
            return;
        }
        logger.info("read "+data.getData().size()+" entries");
    }
}
