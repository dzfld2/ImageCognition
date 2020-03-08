package app;

import confnition.CoordBean;
import confnition.ImageCognition;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;

public class Image {
    public static void main(String[] args) throws Exception {
        findImage4FullScreen(ImageCognition.SIM_ACCURATE_VERY);
    }

    public static void findImage4FullScreen(int sim) throws Exception {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = (int) screenSize.getWidth();
        int h = 200;

        Robot robot = new Robot();
        BufferedImage screenImg = robot.createScreenCapture(new Rectangle(0, 0,
                w, h));
        OutputStream out = new FileOutputStream("data/images/screen.png");
        ImageIO.write(screenImg, "png", out);//���ص���BufferedImageд������

        InputStream in = new FileInputStream("data/images/search.jpg");
        BufferedImage searchImg = ImageIO.read(in);//��Ҫ���ҵı���ͼ����BufferedImage

        //ͼƬʶ�𹤾���
        ImageCognition ic = new ImageCognition();

        List<CoordBean> list = ic.imageSearch(screenImg, searchImg, sim);
        for (CoordBean coordBean : list) {
            System.out.println("�ҵ�ͼƬ,������" + coordBean.getX() + "," + coordBean.getY());

            //��ע�ҵ���ͼ��λ��
            Graphics g = screenImg.getGraphics();
            g.setColor(Color.BLACK);
            g.drawRect(coordBean.getX(), coordBean.getY(),
                    searchImg.getWidth(), searchImg.getHeight());
            g.setFont(new Font(null, Font.BOLD, 20));
            g.drawString("���ҵ���ͼƬ������", coordBean.getX() + searchImg.getWidth() + 5,
                    coordBean.getY() + 10 + searchImg.getHeight() / 2);
            out = new FileOutputStream("data/images/result.png");
            ImageIO.write(screenImg, "png", out);
        }
    }
}
