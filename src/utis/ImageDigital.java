package utis;

import utis.rw.BMP;
import utis.rw.PGM;
import utis.rw.PPM;
import utis.rw.ReadImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.*;

/**
 * ����ͼ����ģ����ã�������
 *
 * @author luoweifu
 */
public class ImageDigital {
    private static BufferedImage img = null;

    /**
     * ��ȡͼƬ
     *
     * @param srcPath ͼƬ�Ĵ洢λ��
     * @return ����ͼƬ��BufferedImage����
     */
    public static BufferedImage readImg(String srcPath) {
		/*try {
			File file = new File(srcPath);
			String filePath = file.getPath(); 
			img = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;*/
        ReadImage readImg = null;
        DataInputStream in = null;
        try {
            in = new DataInputStream(new FileInputStream(srcPath));
            char ch1 = (char) in.read();
            char ch2 = (char) in.read();
            if (ch1 == 'B' && ch2 == 'M') {
                //System.out.println("BMP");
                readImg = new BMP(srcPath);
                img = readImg.readImage();
            } else if (ch1 == 'P' && ch2 == '5') {
                //System.out.println("PGM");
                readImg = new PGM(srcPath);
                img = readImg.readImage();
            } else if (ch1 == 'P' && ch2 == '6') {
                readImg = new PPM(srcPath);
                img = readImg.readImage();
            } else if (ch1 == 'R' && ch2 == ' ') {
                //readImg = new PPM(srcPath);
            } else {
                img = ImageIO.read(new File(srcPath));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    /**
     * ��ȡͼƬ
     *
     * @param srcPath ͼƬ�Ĵ洢λ��
     * @return ����ͼ��ľ�������
     */
    public static int[] readImg2(String srcPath) {
        img = ImageDigital.readImg(srcPath);
        int w = img.getWidth();
        int h = img.getHeight();
        int pix[] = new int[w * h];
        img.getRGB(0, 0, w, h, pix, 0, w);
        return pix;
    }

    /**
     * ��ͼƬд�����
     *
     * @param img        ͼ���BufferedImage����
     * @param formatName �洢���ļ���ʽ
     * @param destPath   ͼ��Ҫ����Ĵ洢λ��
     */
    public static void writeImg(BufferedImage img, String formatName, String destPath) {
        OutputStream out = null;
        try {
            // int imgType = img.getType();
            // System.out.println("w:" + img.getWidth() + "  h:" +
            // img.getHeight());
            out = new FileOutputStream(destPath);
            ImageIO.write(img, formatName, out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ��ͼƬд�����
     *
     * @param pix        ͼ��ľ�������
     * @param w          ͼ��Ŀ�
     * @param h          ͼ��ĸ�
     * @param formatName �洢���ļ���ʽ
     * @param destPath   ͼ��Ҫ����Ĵ洢λ��
     */
    public static void writeImg(int pix[], int w, int h, String formatName,
                                String destPath) {
        //img = new BufferedImage(w, h,ColorModel.getRGBdefault());
        img.setRGB(0, 0, w, h, pix, 0, w);
        writeImg(img, formatName, destPath);
    }

    /**
     * ��ͼƬת���ɺڰ׻Ҷ�ͼƬ,���ص���һά�����ؾ��󣬶���������ֵ
     *
     * @param pix ����ͼƬ����
     * @param w   ��ά���ؾ���Ŀ�
     * @param h   ��ά���ؾ���ĸ�
     * @return �ҶȾ���,
     */
    public static int[] grayImage(int pix[], int w, int h) {
        //int[] newPix = new int[w*h];
        ColorModel cm = ColorModel.getRGBdefault();
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                //0.299 * c.getRed() + 0.587 * c.getGreen() + 0.114 * c.getBlue()
                pix[i * w + j] = (int) (0.299 * cm.getRed(pix[i * w + j]) + 0.587 * cm.getGreen(pix[i * w + j]) + 0.114 * cm.getBlue(pix[i * w + j]));

            }
        }
        return pix;
    }

    /**
     * ��ͼƬת���ɺڰ׻Ҷ�ͼƬ
     *
     * @param srcPath  Դͼ���·��
     * @param format   ͼ��ĸ�ʽ
     * @param destPath ͼ��Ҫ����Ĵ洢λ��
     */
    public static void grayImage(String srcPath, String format, String destPath) {
        img = readImg(srcPath);
        int w = img.getWidth();
        int h = img.getHeight();
        int pix[] = new int[w * h];
        img.getRGB(0, 0, w, h, pix, 0, w);
        int[] newPix = new int[w * h];
        ColorModel cm = ColorModel.getRGBdefault();
        int c = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                //0.299 * c.getRed() + 0.587 * c.getGreen() + 0.114 * c.getBlue()
                c = (int) (0.299 * cm.getRed(pix[i * w + j]) + 0.587 * cm.getGreen(pix[i * w + j]) + 0.114 * cm.getBlue(pix[i * w + j]));
                newPix[i * w + j] = 255 << 24 | c << 16 | c << 8 | c;
            }
        }
        img.setRGB(0, 0, w, h, newPix, 0, w);
        writeImg(img, format, destPath);
    }

    /**
     * ��ͼƬ�������ɫ�ҶȾ���
     *
     * @param pix ����ͼƬ����
     * @param w   ��ά���ؾ���Ŀ�
     * @param h   ��ά���ؾ���ĸ�
     * @return ��ɫ�ҶȾ���
     */
    public static int[] splitRed(int[] pix, int w, int h) {
        int rPix[] = new int[w * h];
        ColorModel cm = ColorModel.getRGBdefault();
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                rPix[i * w + j] = cm.getRed(pix[i * w + j]);
            }
        }
        return rPix;
    }

    /**
     * ��ͼƬת���ɺ�ɫ�Ҷ�ͼƬ
     *
     * @param srcPath  Դͼ���·��
     * @param format   ͼ��ĸ�ʽ
     * @param destPath ͼ��Ҫ����Ĵ洢λ��
     */
    public static void splitRed(String srcPath, String format, String destPath) {
        img = readImg(srcPath);
        int w = img.getWidth();
        int h = img.getHeight();
        int pix[] = new int[w * h];
        img.getRGB(0, 0, w, h, pix, 0, w);
        int[] rPix = new int[w * h];
        ColorModel cm = ColorModel.getRGBdefault();
        int c = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                c = cm.getRed(pix[i * w + j]);
                rPix[i * w + j] = 255 << 24 | c << 16 | 0 << 8 | 0;
            }
        }
        img.setRGB(0, 0, w, h, rPix, 0, w);
        writeImg(img, format, destPath);
    }
	/*
	public static void main(String args[]) {
		Integer w = new Integer(0);
		Integer h = new Integer(0);
		readImg2("F:\\image processing\\����ͼƬ\\person.jpg", w, h);
		System.out.println("������w:" + w + "   h:" + h);		
	}
	*/

    /**
     * ��ͼƬ�������ɫ�ҶȾ���
     *
     * @param pix ����ͼƬ����
     * @param w   ��ά���ؾ���Ŀ�
     * @param h   ��ά���ؾ���ĸ�
     * @return ��ɫ�ҶȾ���
     */
    public static int[] splitGreen(int[] pix, int w, int h) {
        int gPix[] = new int[w * h];
        ColorModel cm = ColorModel.getRGBdefault();
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                gPix[i * w + j] = cm.getGreen(pix[i * w + j]);
            }
        }
        return gPix;
    }

    /**
     * ��ͼƬת������ɫ�Ҷ�ͼƬ
     *
     * @param srcPath  Դͼ���·��
     * @param format   ͼ��ĸ�ʽ
     * @param destPath ͼ��Ҫ����Ĵ洢λ��
     */
    public static void splitGreen(String srcPath, String format, String destPath) {
        img = readImg(srcPath);
        int w = img.getWidth();
        int h = img.getHeight();
        int pix[] = new int[w * h];
        img.getRGB(0, 0, w, h, pix, 0, w);
        int[] gPix = new int[w * h];
        ColorModel cm = ColorModel.getRGBdefault();
        int c = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                c = cm.getGreen(pix[i * w + j]);
                gPix[i * w + j] = 255 << 24 | 0 << 16 | c << 8 | 0;
            }
        }
        img.setRGB(0, 0, w, h, gPix, 0, w);
        writeImg(img, format, destPath);
    }

    /**
     * ��ͼƬ�������ɫ�ҶȾ���
     *
     * @param pix ����ͼƬ����
     * @param w   ��ά���ؾ���Ŀ�
     * @param h   ��ά���ؾ���ĸ�
     * @return ��ɫ�ҶȾ���
     */
    public static int[] splitBlue(int[] pix, int w, int h) {
        int gPix[] = new int[w * h];
        ColorModel cm = ColorModel.getRGBdefault();
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                gPix[i * w + j] = cm.getBlue(pix[i * w + j]);
            }
        }
        return gPix;
    }

    /**
     * ��ͼƬת������ɫ�Ҷ�ͼƬ
     *
     * @param srcPath  Դͼ���·��
     * @param format   ͼ��ĸ�ʽ
     * @param destPath ͼ��Ҫ����Ĵ洢λ��
     */
    public static void splitBlue(String srcPath, String format, String destPath) {
        img = readImg(srcPath);
        int w = img.getWidth();
        int h = img.getHeight();
        int pix[] = new int[w * h];
        img.getRGB(0, 0, w, h, pix, 0, w);
        int[] gPix = new int[w * h];
        ColorModel cm = ColorModel.getRGBdefault();
        int c = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                c = cm.getBlue(pix[i * w + j]);
                gPix[i * w + j] = 255 << 24 | 0 << 16 | 0 << 8 | c;
            }
        }
        img.setRGB(0, 0, w, h, gPix, 0, w);
        writeImg(img, format, destPath);
    }
}
