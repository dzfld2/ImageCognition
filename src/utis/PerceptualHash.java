package utis;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PerceptualHash {

    /**
     * ����֪��ϣ�㷨����Perceptual hash algorithm��
     * �����Ƕ�ÿ��ͼƬ����һ����ָ�ơ���fingerprint���ַ�����Ȼ��Ƚϲ�ͬͼƬ��ָ�ơ�
     * ���Խ�ӽ�����˵��ͼƬԽ���ơ�
     * @param src1
     * @param src2
     * @return
     */
    public static boolean perceptualHashSimilarity(BufferedImage src1, BufferedImage src2) {
        String code1 = perceptualHashSimilarity(src1);
        String code2 = perceptualHashSimilarity(src2);
        char[] ch1 = code1.toCharArray();
        char[] ch2 = code2.toCharArray();
        int diffCount = 0;
        for (int i = 0; i < 64; i++) {
            if (ch1[i] != ch2[i]) {
                diffCount++;
            }
        }
        return diffCount <= 5;
    }

    /**
     * �����ϣֵ
     * @param src
     * @return
     */
    private static String perceptualHashSimilarity(BufferedImage src) {
        int width = 8;
        int height = 8;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics graphics = image.createGraphics();
        graphics.drawImage(src, 0, 0, 8, 8, null);
        int total = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = image.getRGB(j, i);
                int gray = gray(pixel);
                total = total + gray;
            }
        }
        StringBuffer res = new StringBuffer();
        // ��������64�����صĻҶ�ƽ��ֵ��
        int grayAvg = total / (width * height);
        // ��ֵ��
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = image.getRGB(j, i);
                int gray = gray(pixel);
                if (gray >= grayAvg) {
                    res.append("1");
                } else {
                    res.append("0");
                }
            }
        }
        return res.toString();
    }

    /**
     * ��ɫ�ʡ��������ص��ܹ�ֻ��64����ɫ��
     * @param rgb
     * @return
     */
    private static int gray(int rgb) {
        int a = rgb & 0xff000000;//�����λ��24-31������Ϣ��alphaͨ�����洢��a����
        int r = (rgb >> 16) & 0xff;//ȡ���θ�λ��16-23����ɫ��������Ϣ
        int g = (rgb >> 8) & 0xff;//ȡ����λ��8-15����ɫ��������Ϣ
        int b = rgb & 0xff;//ȡ����λ��0-7����ɫ��������Ϣ
        rgb = (r * 77 + g * 151 + b * 28) >> 8;    // NTSC luma������Ҷ�ֵ
        return a | (rgb << 16) | (rgb << 8) | rgb;//���Ҷ�ֵ���������ɫ����
    }
}
