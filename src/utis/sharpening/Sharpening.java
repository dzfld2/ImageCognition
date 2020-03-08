package utis.sharpening;

import utis.ImageDecorator;
import utis.ImageDigital;

import java.awt.image.BufferedImage;

/**
 * 图像的锐化
 * @author Administrator
 *
 */
public abstract class Sharpening extends ImageDecorator {

	@Override
	public int[] processing(int[] pix, int w, int h) {
		return super.processing(pix, w, h);
	}
	/**
	 * 图像的锐化
	 * 
	 * @param srcPath
	 *            图片的存储位置
	 * @param destPath
	 *            图像要保存的存储位置
	 * @param formatName
	 *            图像要保存的存储位置
	 */
	public void processing(String srcPath, String destPath,
                           String formatName) {
		BufferedImage img = ImageDigital.readImg(srcPath);
		int w = img.getWidth();
		int h = img.getHeight();
		int pix[] = new int[w * h];
		pix = img.getRGB(0, 0, w, h, pix, 0, w);
		for(int i=0; i<w*h; i++) {
			pix[i] = pix[i] & 0xff;
		}
		pix = processing(pix, w, h);
		int d = findMaxInt(pix)-findMinInt(pix);
		int p = 0;
		for(int i=0; i<w*h; i++) {
			p = (int)(255.0*pix[i]/d);
			pix[i] = 255<<24 | p<<16 | p<<8 | p;
		}
		img.setRGB(0, 0, w, h, pix, 0, w);
		ImageDigital.writeImg(img, formatName, destPath);
	}
	protected int findMaxInt(int[] a) {
		int max = a[0];
		for (int i = 0; i < a.length; i++) {
			if (a[i] > max) {
				max = a[i];
			}
		}
		return Math.abs(max);
	}
	/**
	 * 找到图像的最小灰度值
	 * @param a
	 * @return 最小的一个负数
	 */
	protected int findMinInt(int[] a) {
		int min = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] < min && a[i] < 0) {
				min = a[i];
			}
		}
		return min;
	}

}
