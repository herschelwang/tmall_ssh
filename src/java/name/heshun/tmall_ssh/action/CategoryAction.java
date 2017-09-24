package name.heshun.tmall_ssh.action;

import name.heshun.tmall_ssh.util.ImageUtil;
import name.heshun.tmall_ssh.util.Page;

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
public class CategoryAction extends Action4Result {
    @Action("admin_category_list")
    public String list() {
        if (page == null) {
            page = new Page();
        }
        int total = categoryService.total();
        page.setTotal(total);
        categorys = categoryService.listByPage(page);
        return "listCategory";
    }

    @Action("admin_category_edit")
    public String edit() {
        t2p(category);
        return "editCategory";
    }

    @Action("admin_category_add")
    public String add() {
        categoryService.save(category);
        File imageFolder = new File(ServletActionContext.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, category.getId() + ".jpg");
        try {
            FileUtils.copyFile(img, file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "listCategoryPage";
    }

    @Action("admin_category_delete")
    public String delete() {
        categoryService.delete(category);
        // 删除分类对应的图片
        File imgFolder = new File(ServletActionContext.getServletContext().getRealPath("img/category"));
        File file = new File(imgFolder, category.getId() + ".jpg");
        file.delete();
        return "listCategoryPage";
    }

    @Action("admin_category_update")
    public String update() {
        categoryService.update(category);
        if (null != img) {
            File imageFolder = new File(ServletActionContext.getServletContext().getRealPath("img/category"));
            File file = new File(imageFolder, category.getId() + ".jpg");
            try {
                FileUtils.copyFile(img, file);
                BufferedImage img = ImageUtil.change2jpg(file);
                ImageIO.write(img, "jpg", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "listCategoryPage";
    }
}
