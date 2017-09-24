package name.heshun.tmall_ssh.action;

import name.heshun.tmall_ssh.service.ProductImageService;
import name.heshun.tmall_ssh.util.ImageUtil;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Aware on 2017/9/8.
 */

public class ProductImageAction extends Action4Result {
    @Action("admin_productImage_list")
    public String list() {
        // 根据product和类型TYPE_SINGLE获取productSingleImages集合
        productSingleImages = productImageService.list("product", product, "type", ProductImageService.TYPE_SINGLE);
        // 根据product和类型TYPE_DETAIL 获取productDetailImages集合
        productDetailImages = productImageService.list("product", product, "type", ProductImageService.TYPE_DETAIL);
        // 对象持久化
        t2p(product);
        return "listProductImage";
    }

    @Action("admin_productImage_add")
    public String add() {
        productImageService.save(productImage);
        // 根据productImage的type字段值, 确定文件存放的目录名称
        String folder = "img/";
        if (ProductImageService.TYPE_SINGLE.equals(productImage.getType())) {
            folder += "productSingle";
        } else {
            folder += "productDetail";
        }
        // 根据ServletActionContext.getServletContext().getRealPath(folder) 定位硬盘上的真正位置(绝对路径)
        File imageFolder = new File(ServletActionContext.getServletContext().getRealPath(folder));
        // 确定文件名称
        File file = new File(imageFolder, productImage.getId() + ".jpg");
        // 获取文件名
        String fileName = file.getName();
        try {
            FileUtils.copyFile(img, file);
            // 格式转换
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 如果图片的类型是type_single,借助ImageUtil.resizeImage把正常大小的图片，改变大小之后，分别复制到productSingle_middle和productSingle_small目录下
        if (ProductImageService.TYPE_SINGLE.equals(productImage.getType())) {
            String imageFolder_small = ServletActionContext.getServletContext().getRealPath("img/productSingle_small");
            String imageFolder_middle = ServletActionContext.getServletContext().getRealPath("img/productSingle_middle");
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            f_small.getParentFile().mkdirs();
            f_middle.getParentFile().mkdirs();
            ImageUtil.resizeImage(file, 56, 56, f_small);
            ImageUtil.resizeImage(file, 217, 190, f_middle);
        }
        return "listProductImagePage";
    }

    @Action("admin_productImage_delete")
    public String delete() {
        t2p(productImage);
        productService.delete(productImage);
        return "listProductImagePage";
    }
}
