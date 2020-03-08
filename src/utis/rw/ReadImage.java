package utis.rw;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * ��ȡͼƬ
 * @author luoweifu
 *
 */
public abstract class ReadImage {
	protected int width; // BMPͼ��Ŀ���
	protected int height; // BMPͼ��ĸ߶�
	protected DataInputStream in = null;
	protected BufferedImage img = null;
	
	public ReadImage(String srcPath) {
		try {
			in = new DataInputStream(new FileInputStream(srcPath));
			img = ImageIO.read(new File(srcPath));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public abstract BufferedImage readImage();
	public int getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @param args
	 */
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub

	}*/

}
