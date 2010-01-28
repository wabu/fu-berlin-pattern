/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks;

import java.io.IOException;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author wabu
 */
public class Task26 implements Runnable {
    private final Logger logger = Logger.getLogger(Task26.class);

    public void run() {
        CarDataReader data = new CarDataReader();
        try {
            data.addDataFromResource("car.data");
        } catch (IOException ex) {
            logger.error("reading car.data", ex);
            return;
        } catch (IllegalArgumentException ex) {
            logger.error("reading car.data", ex);
            return;
        } catch (IndexOutOfBoundsException ex) {
            logger.error("reading car.data", ex);
            return;
        }
    }
}
