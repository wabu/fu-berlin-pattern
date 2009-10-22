/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.u02.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
        System.out.println("üarse: " + line);

        StringTokenizer tokenizer = new StringTokenizer(line);




        if(tokenizer.countTokens() <= 2*Digit.POINT_NUMBER) {
            throw new IllegalStateException("line length...");
        }

        DigitPoint point;
        Digit digit = new Digit();

        for(int i=0; i<2*Digit.POINT_NUMBER; i+=2) {
            point = new DigitPoint(
                    Integer.parseInt(tokenizer.nextToken()),
                    Integer.parseInt(tokenizer.nextToken()));

            digit.addPoint(point);
        }

        if(tokenizer.hasMoreTokens()) {
            digit.setGroup(Integer.parseInt(tokenizer.nextToken()));
        }
        
        return digit;
    }

    public Collection<Digit> readDigitsFromFile(String filename) {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            String line = null;
            List<Digit> readDigits = new ArrayList<Digit>();
            int i = 0;
            while( (line = reader.readLine()) != null) {
                System.out.println("Read line: " + i++);
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
