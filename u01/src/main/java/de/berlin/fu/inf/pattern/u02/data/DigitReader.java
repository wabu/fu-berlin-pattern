/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.u02.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author Alexander Münn
 */
public class DigitReader {

    public Digit readDigitFromLine(String line) {
    	StringTokenizer t = new StringTokenizer(line);

        if(t.countTokens() <= 16) {
            throw new IllegalStateException("line length...");
        }

        DigitPoint point;
        Digit digit = new Digit();
        
        for(int i=0; i<Digit.POINT_NUMBER; i++) {
            point = new DigitPoint(
                    Integer.parseInt(t.nextToken()),
                    Integer.parseInt(t.nextToken()));
            digit.addPoint(point);
        }
        digit.setGroup(Integer.parseInt(t.nextToken()));
        
        return digit;
    }

    public Collection<Digit> readDigitsFromFile(String filename) {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            String line = null;
            List<Digit> readDigits = new LinkedList<Digit>();

            while( (line = reader.readLine()) != null) {
                readDigits.add(this.readDigitFromLine(line));
            }

            return readDigits;

        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            return null;
        }
    }

    public Collection<Digit> readDigitsFromStream(InputStream ioStream) {



        return null;
    }
}
