package name.heshun.tmall_ssh.util;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Aware on 2017/9/5.
 */
public class ImageUtil {
    /**
     * 传入图片文件(png, gif, bmp等), 将格式转换为jpg格式
     * 仅仅通过ImageIO.write(img, "jpg", file); 不足以保证转换出来的jpg文件显示正常
     *
     * @param file
     * @return
     */
    public static BufferedImage change2jpg(File file) {
        try {
            Image i = Toolkit.getDefaultToolkit().createImage(file.getAbsolutePath());
            PixelGrabber pg = new PixelGrabber(i, 0, 0, -1, -1, true);
            pg.grabPixels();
            int width = pg.getWidth(), height = pg.getHeight();
            final int[] RGB_MASKS = {0xFF0000, 0xFF00, 0xFF};
            final ColorModel RGB_OPAQUE = new DirectColorModel(32, RGB_MASKS[0], RGB_MASKS[1], RGB_MASKS[2]);
            DataBuffer buffer = new DataBufferInt((int[]) pg.getPixels(), pg.getWidth() * pg.getHeight());
            WritableRaster raster = Raster.createPackedRaster(buffer, width, height, width, RGB_MASKS, null);
            BufferedImage img = new BufferedImage(RGB_OPAQUE, raster, false, null);
            return img;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 改变图片大小
     *
     * @param srcFile
     * @param width
     * @param height
     * @param destFile
     */
    public static void resizeImage(File srcFile, int width, int height, File destFile) {
        try {
            Image i = ImageIO.read(srcFile);
            i = resizeImage(i, width, height);
            ImageIO.write((RenderedImage) i, "jpg", destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 改变图片大小
     *
     * @param srcImage
     * @param width
     * @param height
     * @return
     */
    public static Image resizeImage(Image srcImage, int width, int height) {
        try {
            BufferedImage buffImg = null;
            buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            buffImg.getGraphics().drawImage(srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            return buffImg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
