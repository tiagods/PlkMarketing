package br.com.tiagods.modeldao;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

public class CapturarTela{
	private File file;
	public boolean gerarFoto(){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		Rectangle screenRect = new Rectangle(screenSize);
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		BufferedImage screenCapturedImage = robot.createScreenCapture(screenRect);
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		try {
			String tmp = System.getProperty("java.io.tmpdir");
			file = new File(tmp+"Print"+sdf.format(new Date())+".gif");
			ImageIO.write(screenCapturedImage, "gif", file);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public File getFile(){
		return this.file;
	}
}