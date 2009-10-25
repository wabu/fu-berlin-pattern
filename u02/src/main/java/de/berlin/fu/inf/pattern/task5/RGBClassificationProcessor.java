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
	
	private final int backgroundClass = 1;
	private final int foregroundClass = 2;
	
	private final int redRange   = 0x00ff0000;
	private final int greenRange = 0x0000ff00;
	private final int blueRange  = 0x000000ff; 
	
	
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
		
		logger.debug("prozessing image of size "+image.length);
		
		// classify color pixel as an vector 
		KDClassificator<Vector, Integer> kdClassifier = new KDClassificator<Vector, Integer>();
		// training list for classifier
		List<Entry<Vector,Integer>> classifiedPixel = new ArrayList<Entry<Vector,Integer>>();
		
		
		// build training set
		for(int i=0; i<mask.length; i++) {
			// black color is background
			if( mask[i] == backgroundColor) {
				if( logger.isTraceEnabled())
					logger.trace(String.format("pixel %6d has background mask %8x", i, mask[i]));
				classifiedPixel.add(buildEntry(image[i],this.backgroundClass));
			} else if( mask[i] == foregroundColor ) {
				if( logger.isTraceEnabled())
					logger.trace(String.format("pixel %6d has forground mask %8x", i, mask[i]));
				classifiedPixel.add(buildEntry(image[i],this.foregroundClass));
			} else {
				if( logger.isTraceEnabled())
					logger.trace(String.format("pixel %6d has unknown mask %8x", i, mask[i]));
			}
		}
		
		kdClassifier.train(classifiedPixel);
		
		int[] output = new int[image.length];
		// all background pixel become white
		Vector vector = new Vector(3);
		
		int fg=0, bg=0;
		for( int i=0; i<mask.length; i++) {
			if(i%50000 == 0) {
				logger.debug("processed "+i+"/"+mask.length+": "+fg+"fg, "+bg+"bg");
			}
			
			vector.setVector(rgbAsArray(image[i]));
			int klass = kdClassifier.classify(vector);
			if( klass == backgroundClass) {
				bg++;
				output[i] = newBackgroundColor;
				
				if(mask[i] == foregroundColor) {
					output[i] = 0xffff0000;
				}
			} else {
				fg++;
				output[i] = image[i];
				
				if(mask[i] == backgroundColor) {
					output[i] = 0xff00ff00;
				}
			}
			
		}
		
		return output;
	}
	
	private Entry<Vector,Integer> buildEntry(int rgbColor, int pixelClass ) {
		
		Vector vector = new Vector(rgbAsArray(rgbColor));
		
		if( logger.isTraceEnabled())
			logger.trace("adding "+vector+ " to class " + pixelClass);			
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
