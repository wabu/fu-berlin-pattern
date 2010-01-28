/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wabu
 */
public class CarDataReader {
    private final List<CarData> data;

    public CarDataReader() {
        data = new ArrayList<CarData>();
    }

    public void addDataFromResource(String resourceName) 
            throws IOException, IllegalArgumentException, IndexOutOfBoundsException {
        InputStream strm =
                Thread.currentThread().getContextClassLoader().
                getResourceAsStream(resourceName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(strm));

        String line;
        while((line = reader.readLine()) != null) {
            data.add(new CarData(line));
        }
    }
}
