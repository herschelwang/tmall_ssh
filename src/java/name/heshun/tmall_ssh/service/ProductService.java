package name.heshun.tmall_ssh.service;

import name.heshun.tmall_ssh.pojo.Category;
import name.heshun.tmall_ssh.pojo.Product;

import java.util.List;

/**
 * Created by Aware on 2017/9/8.
 */
public interface ProductService extends BaseService {
    /**
     * 前台: 为分类填充产品集合
     *
     * @param category
     */
    void fill(Category category);

    /**
     * 前台: 为多个分类填充产品集合
     *
     * @param categorys
     */
    void fill(List<Category> categorys);

    /**
     * 前台: 为多个分类填充推荐产品集合
     *
     * @param categorys
     */
    void fillByRow(List<Category> categorys);

    /**
     * 前台: 设置销售数量和评价数量
     *
     * @param product
     */
    void setSaleAndReviewNumber(Product product);

    /**
     * 前台: 设置销售数量和评价数量(多个产品)
     *
     * @param products
     */
    void setSaleAndReviewNumber(List<Product> products);

    /**
     * 前台: 根据关键字搜索产品(分页)
     *
     * @param keyword
     * @param start
     * @param count
     * @return
     */
    List<Product> search(String keyword, int start, int count);
}
