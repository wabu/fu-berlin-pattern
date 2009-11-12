package de.berlin.fu.inf.pattern.tasks;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

public class Main5 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Logger logger = Logger.getRootLogger();
		
		String dir = "pics/";
		String imageFile = dir+"flowers.jpg";
		String maskFile = dir+"flowers-train.png";
		String outputFile = "flower-output.jpg";
		
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		BufferedImage img, mask;
		
		try {
			URL imgURL = cl.getResource(imageFile); 
			URL maskURL = cl.getResource(maskFile);
			
			logger.debug("locate image file: " + imgURL);
			logger.debug("locate mask file: " + maskURL);
			
			logger.info("read images");
			img = ImageIO.read(imgURL);
			mask = ImageIO.read(maskURL);
			
			int[] imagePixel = img.getRGB(
					0, 0, 
					img.getWidth(), img.getHeight(),
					null, 0, img.getWidth());
			
			int[] maskPixel = mask.getRGB(
					0, 0, 
					mask.getWidth(), mask.getHeight(),
					null, 0, mask.getWidth());
			
			logger.info("process images");
			RGBClassificationProcessor rgbProcessor = new RGBClassificationProcessor();			
			
			
			int[] modifiedPixel = rgbProcessor.colorBackground(imagePixel, maskPixel, 0xffffffff, 0xff000000, 0xffffffff);
			
			logger.info("writing output to "+outputFile);
			// create and init new buffered Image for storing data
			BufferedImage outputImg = new BufferedImage(
					img.getWidth(), 
					img.getHeight(),
					BufferedImage.TYPE_INT_RGB);

			outputImg.setRGB(
					0, 0, 
					outputImg.getWidth(), outputImg.getHeight(), 
					modifiedPixel, 0, outputImg.getWidth());

			ImageIO.write(outputImg, "jpeg", new FileOutputStream(outputFile));
			
			logger.info("done");
		} catch (IOException e) {
			logger.error(e);
		}
	}
}
