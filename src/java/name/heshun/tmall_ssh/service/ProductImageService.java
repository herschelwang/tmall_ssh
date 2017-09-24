package name.heshun.tmall_ssh.service;

import name.heshun.tmall_ssh.pojo.Product;

/**
 * Created by Aware on 2017/9/8.
 */
public interface ProductImageService extends BaseService {
    /**
     * 展示图片
     */
    String TYPE_SINGLE = "type_single";
    /**
     * 详情图片
     */
    String TYPE_DETAIL = "type_detail";

    /**
     * 设置产品图片
     * 1. 查询出某个产品下的类型是type_single的图片集合
     * 2. 如果这个集合不为空，那么取出其中的第一个图片，作为这个产品的图片：firstProdutImage.
     *
     * @param product
     */
    void setFirstProdutImage(Product product);
}
