package de.berlin.fu.inf.pattern.task6;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import de.berlin.fu.inf.pattern.classificators.kmeans.KMeanClassificator;


public class Main6 {
	public static void main(String[] args) {
		Logger logger = Logger.getRootLogger();
		
		String srcImage = "0049a.jpg";
		String outputFile = "bin_" + srcImage;
		
		ClassLoader cl = ClassLoader.getSystemClassLoader();
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
			blacPixel = new MonochromePixel(0, 0, black);
			whitePixel = new MonochromePixel(0, 0, white);
			
			/** init classifier of two classes */
			KMeanClassificator<MonochromePixel> classifier = 
				new KMeanClassificator<MonochromePixel>(blacPixel, whitePixel);
			/** do classification */
			Collection<MonochromePixel>[] pixelSets = classifier.classify(pixels);
			
			if( pixelSets.length != 2 || pixelSets[0] == null || pixelSets[1] == null ) {
				logger.fatal("error in processing, no results retrieved");
				return;
			}
			
			logger.info("process results to output ("+pixelSets[0].size() + " classified black pixel)");
			// now we just need to place classified black pixel cause array is already white
			for(MonochromePixel pixel : pixelSets[0]) {
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
