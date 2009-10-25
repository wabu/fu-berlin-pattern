package de.berlin.fu.inf.pattern.task5;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import de.berlin.fu.inf.pattern.classificators.Entry;
import de.berlin.fu.inf.pattern.classificators.kd.KDClassificator;
import de.berlin.fu.inf.pattern.types.Vector;


/**
 * 
 * @author alex
 */
public class RGBClassificationProcessor {
	public final Logger logger = Logger.getLogger(RGBClassificationProcessor.class);
	
	private final Integer backgroundClass = new Integer(1);
	private final Integer foregroundClass = new Integer(2);
	
	private final int redRange   = 0x0F00;
	private final int greenRange = 0x00F0;
	private final int blueRange  = 0x000F; 
	
	
	/**
	 * 
	 * @param image array of pixel in RGB-Color-Model
	 * @param mask 
	 * @param foregroundColor
	 * @param backgroundColor
	 * @param newBackgroundColor
	 * @return
	 */
	public int[] colorBackground(int[] image, int[] mask, int foregroundColor, int backgroundColor, int newBackgroundColor) {
		
		if( image.length != mask.length ) {
			
			logger.error("source image and layer definition image are no identical in size");
			return null;
		}
		
		// classify color pixel as an vector 
		KDClassificator<Vector, Integer> kdClassifier = new KDClassificator<Vector, Integer>();
		// training list for classifier
		List<Entry<Vector,Integer>> classifiedPixel = new ArrayList<Entry<Vector,Integer>>();
		
		
		// build training set
		for(int i=0; i<mask.length; i++) {
			if( logger.isTraceEnabled())
				logger.trace("pixel "+i+ " color: " + mask[i]);			
			// black color is background
			if( mask[i] == backgroundColor) {
				classifiedPixel.add(buildEntry(image[i],this.backgroundClass));
			} else if( mask[i] == foregroundColor ) {
				classifiedPixel.add(buildEntry(image[i],this.foregroundClass));
			}
		}
		
		kdClassifier.train(classifiedPixel);
		
		int[] output = new int[image.length];
		// all background pixel become white
		Vector vector = new Vector(3);
		
		for( int i=0; i<mask.length; i++) {
			
			// do not modify foreground pixel
			if( mask[i] == foregroundColor ) {
				output[i] = image[i];
			}
			if( mask[i] == backgroundColor ) {
				image[i] = newBackgroundColor;
			} else {
				vector.setVector(rgbAsArray(image[i]));
				
				if( kdClassifier.classify(vector).equals(backgroundClass)) {
					image[i] = newBackgroundColor;
				} else {
					output[i] = image[i];
				}
			}
		}
		
		return output;
	}
	
	private Entry<Vector,Integer> buildEntry(int rgbColor, Integer pixelClass ) {
		
		Vector vector = new Vector(rgbAsArray(rgbColor));
		Entry<Vector,Integer> entry = new Entry<Vector, Integer>(vector, pixelClass);		
		
		return entry;
	}
	
	
	/**
	 * 
	 * @param rgbColor
	 * @return index 0 = red, 1 = green, 2 = blue
	 */
	private int[] rgbAsArray(int rgbColor) {
		
		return new int[]{
				rgbColor & this.redRange,
				rgbColor & this.greenRange,
				rgbColor & this.blueRange};
	}

}
