package name.heshun.tmall_ssh.action;

import java.io.File;

/**
 * Created by Aware on 2017/9/8.
 * <p>
 * 处理图片上传.
 */
public class Action4Upload {
    protected File img;
    protected String imgFileName;
    protected String imgContentType;

    /**
     * 构造方法
     */
    public Action4Upload() {
    }

    @Override
    public String toString() {
        return "Action4Upload{" +
                "img=" + img +
                ", imgFileName='" + imgFileName + '\'' +
                ", imgContentType='" + imgContentType + '\'' +
                '}';
    }

    public File getImg() {
        return img;
    }

    public void setImg(File img) {
        this.img = img;
    }

    public String getImgFileName() {
        return imgFileName;
    }

    public void setImgFileName(String imgFileName) {
        this.imgFileName = imgFileName;
    }

    public String getImgContentType() {
        return imgContentType;
    }

    public void setImgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
    }
}
