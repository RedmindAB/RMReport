package se.redmind.rmtest.web.route.api.screenshot.byfilename;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import se.redmind.rmtest.web.route.api.screenshot.ScreenshotFolderDAO;

public class ScreenshotHandler {

	ScreenshotFolderDAO screenshotFolderDAO;
	
	public ScreenshotHandler() {
		screenshotFolderDAO = new ScreenshotFolderDAO();
	}
	
	public byte[] getImageAsByteArray(String timestamp, String filename){
		File immageFile = screenshotFolderDAO.getScreenshot(timestamp, filename);
		BufferedImage image = FileToImage(immageFile);
		ByteArrayOutputStream imgBAO = new ByteArrayOutputStream();
		writeImageToStream(image, imgBAO);
		return imgBAO.toByteArray();
	}

	private void writeImageToStream(BufferedImage image, ByteArrayOutputStream imgBAO) {
		try {
			ImageIO.write(image, "png", imgBAO);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private BufferedImage FileToImage(File immageFile) {
		try {
			return ImageIO.read(immageFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}
