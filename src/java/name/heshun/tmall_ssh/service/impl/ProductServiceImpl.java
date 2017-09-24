package name.heshun.tmall_ssh.service.impl;

import name.heshun.tmall_ssh.pojo.Category;
import name.heshun.tmall_ssh.pojo.Product;
import name.heshun.tmall_ssh.service.OrderItemService;
import name.heshun.tmall_ssh.service.ProductImageService;
import name.heshun.tmall_ssh.service.ProductService;
import name.heshun.tmall_ssh.service.ReviewService;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aware on 2017/9/8.
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl implements ProductService {
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ReviewService reviewService;

    @Override
    public void fill(Category category) {
        List<Product> products = listByParent(category);
        for (Product product : products) {
            productImageService.setFirstProdutImage(product);
        }
        category.setProducts(products);
    }

    @Override
    public void fill(List<Category> categorys) {
        for (Category category : categorys) {
            this.fill(category);
        }
    }

    @Override
    public void fillByRow(List<Category> categorys) {
        int productNumberEachRow = 8;
        for (Category category : categorys) {
            List<Product> products = category.getProducts();
            List<List<Product>> productsByRow = new ArrayList<>();
            for (int i = 0; i < products.size(); i += productNumberEachRow) {
                int size = i + productNumberEachRow;
                size = size > products.size() ? products.size() : size;
                List<Product> productsOfEachRow = products.subList(i, size);
                productsByRow.add(productsOfEachRow);
            }
            category.setProductsByRow(productsByRow);
        }
    }

    @Override
    public void setSaleAndReviewNumber(Product product) {
        int saleCount = orderItemService.total(product);
        product.setSaleCount(saleCount);
        int reviewCount = reviewService.total(product);
        product.setReviewCount(reviewCount);
    }

    @Override
    public void setSaleAndReviewNumber(List<Product> products) {
        for (Product product : products) {
            this.setSaleAndReviewNumber(product);
        }
    }

    @Override
    public List<Product> search(String keyword, int start, int count) {
        DetachedCriteria dc = DetachedCriteria.forClass(clazz);
        dc.add(Restrictions.like("name", "%" + keyword + "%"));
        return findByCriteria(dc, start, count);
    }
}