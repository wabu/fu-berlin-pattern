/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.berlin.fu.inf.pattern.u02.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.filechooser.FileSystemView;

import org.apache.log4j.Logger;

/**
 *
 * @author Alexander Münn
 */
public class DigitReader {
	private final Logger logger = Logger.getLogger(DigitReader.class);  

    public Digit readDigitFromLine(String line) {
        logger.trace("readDigitFromLine: " + line);

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
    
    public Collection<Digit> readDigits(String name) {
        try{
           return readDigitsFromStream(new FileReader(name));
        } catch (IOException ioEx) {
        	logger.error("error while opening file "+name, ioEx);
	        return readDigitsFromStream(new InputStreamReader(ClassLoader.getSystemResourceAsStream("name")));
        }
    }

    public Collection<Digit> readDigitsFromFile(String filename) {
        try{
           return readDigitsFromStream(new FileReader(filename));
        } catch (IOException ioEx) {
        	logger.error("error while opening file "+filename, ioEx);
        	throw new RuntimeException(ioEx);
        }
    }
    
    public Collection<Digit> readDigitsFromStream(InputStreamReader streamreader) {
        try{
            BufferedReader reader = new BufferedReader(streamreader);

            String line = null;
            List<Digit> readDigits = new ArrayList<Digit>();
            int i = 0;
            while( (line = reader.readLine()) != null) {
                logger.trace("Read line: " + i++);
                readDigits.add(this.readDigitFromLine(line));

            }

            return readDigits;

        } catch (IOException ioEx) {
        	logger.error("error while opening data stream", ioEx);
        	throw new RuntimeException(ioEx);
        }
    }
}
