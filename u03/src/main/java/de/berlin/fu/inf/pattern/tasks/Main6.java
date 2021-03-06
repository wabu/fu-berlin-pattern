package de.berlin.fu.inf.pattern.tasks;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import de.berlin.fu.inf.pattern.impl.kmean.KMeanClassifier;


public class Main6 {
	public static void main(String[] args) {
		Logger logger = Logger.getRootLogger();
		
		String srcImage = "0049a.jpg";
		String outputFile = "bin_" + srcImage;
		
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		BufferedImage img;
		
		int white = 0xFFFFFFFF;
		int black = 0xFF000000;
		
		try {
			URL imgURL = cl.getResource(srcImage); 
			
			logger.debug("locate image file: " + imgURL);
			
			logger.info("read image");
			img = ImageIO.read(imgURL);
			
			int width = img.getWidth();
			
			int[] imagePixel = img.getRGB(
					0, 0, 
					img.getWidth(), img.getHeight(),
					null, 0, img.getWidth());
			
			List<MonochromePixel> pixels = new ArrayList<MonochromePixel>(imagePixel.length);
			
			for(int i = 0; i < imagePixel.length; i++) {
				// we just expect a monochrome input image, so all RGB channel have same value
				MonochromePixel monoPixel = new MonochromePixel(i%width, i/width, imagePixel[i] & 0xFF );
				pixels.add(monoPixel);
				// set any pixel to white color, so we save one run over array
				imagePixel[i] = white;
			}
			
			logger.info("process " + imagePixel.length + " pixel");
			
			MonochromePixel blacPixel, whitePixel;
			blacPixel = new MonochromePixel(0, 0, 0x00);
			whitePixel = new MonochromePixel(0, 0, 0xFF);
			
			/** init classifier of two classes */
			KMeanClassifier<MonochromePixel> classifier = new KMeanClassifier<MonochromePixel>(blacPixel, whitePixel);
			
			HashMap<Integer, LinkedList<MonochromePixel>> pixelSets = new HashMap<Integer, LinkedList<MonochromePixel>>(2);
			pixelSets.put(0, new LinkedList<MonochromePixel>());
			pixelSets.put(1, new LinkedList<MonochromePixel>());
			
			/** do classification */
			classifier.train(pixels);
			for(MonochromePixel p : pixels) {
				pixelSets.get(classifier.classify(p)).add(p);
			}
			
			
			if(pixelSets.isEmpty()) {
				logger.fatal("error in processing, no results retrieved");
				return;
			}
			
			logger.info("process results to output ("+pixelSets.get(0).size() + " classified black pixel)");
			// now we just need to place classified black pixel cause array is already white
			for(MonochromePixel pixel : pixelSets.get(0)) {
				imagePixel[pixel.getY()*width+pixel.getX()] = black;
			}

			logger.info("writing output to "+outputFile);
			// create and init new buffered Image for storing data
			BufferedImage outputImg = new BufferedImage(
					img.getWidth(), 
					img.getHeight(),
					BufferedImage.TYPE_INT_RGB);

			outputImg.setRGB(
					0, 0, 
					outputImg.getWidth(), outputImg.getHeight(), 
					imagePixel, 0, outputImg.getWidth());

			ImageIO.write(outputImg, "jpeg", new FileOutputStream(outputFile));
			
			logger.info("done");
		} catch (IOException e) {
			logger.error(e);
		}
	}
}
