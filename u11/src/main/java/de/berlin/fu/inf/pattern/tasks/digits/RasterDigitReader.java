/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.tasks.digits;

import com.google.common.collect.Lists;
import de.berlin.fu.inf.pattern.util.collect.String2DoubleTransformator;
import de.berlin.fu.inf.pattern.util.matrix.Vectors;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;
import org.jscience.mathematics.vector.Float64Vector;

/**
 *
 * @author alex
 */
public class RasterDigitReader {
    private final Logger logger = Logger.getLogger(RasterDigitReader.class);
    private final int describtionVectorLength = 10;
    private final static String WHITESPACE = " ";
    private final String2DoubleTransformator trans = new String2DoubleTransformator();

    public Map<RasterDigit, Integer> readRasterDigitsFromStream(InputStream stream)
        throws IOException
    {
        Map<RasterDigit, Integer> readDigits = new HashMap<RasterDigit, Integer>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line = null;
        StringTokenizer tokenizer = null;
        int cols = 0;
        int rows = 0;
        int lineCounter = 0;
        final ArrayList<Double> data = new ArrayList<Double>();

        while( (line = reader.readLine()) != null ) {
            lineCounter++;

            if( line.length() == 0 ) {
                logger.trace("skip line " + lineCounter);
                continue;
            }

            String[] lines = line.split(WHITESPACE);
            List<Double> doubles = Lists.transform(
                    Lists.newArrayList(lines), trans);
            int tokens = lines.length;
            // set new expected number tokens
            if( cols == 0 ) {
                cols = tokens;
                if( tokens == describtionVectorLength)
                    logger.warn("expected tokens are initialized to length of expected description vector");
            }


            if( tokens == describtionVectorLength ) {
                int number = doubles.indexOf(1.0d);
                logger.debug("create Vector with " + cols + "x"+rows + " elements of class " + number);
                double[] completeData = new double[data.size()];
                // do some stupid conversion
                int i=0;
                for( double d : data) completeData[i++] = d;
                
                Float64Vector vec = Vectors.valueOf(completeData);

                readDigits.put(new RasterDigit(cols, rows, vec), number);
                // reset all needed counter stuff
                cols = 0;
                rows = 0;
                data.clear();
            } else {
                if( tokens != cols ) {
                    logger.error("in line " + lineCounter + ": less elements than expected (" +tokens +"/"+cols+")");
                    logger.error("line " + lineCounter + " will be left");
                }
                data.addAll(doubles);
                rows++;
            }
        }


        return readDigits;
    }



}
