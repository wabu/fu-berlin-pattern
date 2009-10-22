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

/**
 *
 * @author Alexander Münn
 */
public class DigitReader {

    public Digit readDigitFromLine(String line) {
        String[] fields = line.split(" ");


        if(fields.length <= 16) {
            throw new IllegalStateException("line length...");
        }

        DigitPoint point;
        Digit digit = new Digit();
        for(int i=0; i<Digit.POINT_NUMBER; i*=2) {

            point = new DigitPoint(
                    Integer.parseInt(fields[i]),
                    Integer.parseInt(fields[i+1]));

            digit.addPoint(point);
        }
        
        return new Digit();
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
