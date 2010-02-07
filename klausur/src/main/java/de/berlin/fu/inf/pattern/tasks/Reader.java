/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.vector.Float64Vector;
import org.jscience.mathematics.vector.Vector;

/**
 *
 * @author wabu
 */
public class Reader {
    private final List<Vector<Float64>> data;

    public Reader() {
        data = new ArrayList<Vector<Float64>>();
    }

    protected Vector<Float64> parseLine(String line) {
        return Float64Vector.valueOf(
            Lists.transform(Lists.newArrayList(line.split(", *")),
                new Function<String, Float64>() {
            @Override
            public Float64 apply(String from) {
                return Float64.valueOf(Double.parseDouble(from));
            }
        }));
    }

    public void addDataFromResource(String resourceName) 
            throws IOException, IllegalArgumentException, IndexOutOfBoundsException {
        InputStream strm =
                Thread.currentThread().getContextClassLoader().
                getResourceAsStream(resourceName);
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(strm));

        String line;
        while((line = reader.readLine()) != null) {
            if(line.charAt(0) == '#') {
                continue;
            }
            data.add(parseLine(line));
        }
    }

    public List<Vector<Float64>> getData() {
        return Collections.unmodifiableList(data);
    }
}
