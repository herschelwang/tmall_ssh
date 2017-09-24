package name.heshun.tmall_ssh.pojo;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Aware on 2017/9/8.
 */
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "name")
    String name;

    /**
     * 前台: 一个分类下有对个产品
     */
    @Transient
    List<Product> products;
    /**
     * 前台: 推荐产品列表(一个分类对应多行产品, 一行产品对应多个产品记录)
     */
    @Transient
    List<List<Product>> productsByRow;

    /**
     * 无参构造
     */
    public Category() {
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", products=" + products +
                ", productsByRow=" + productsByRow +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<List<Product>> getProductsByRow() {
        return productsByRow;
    }

    public void setProductsByRow(List<List<Product>> productsByRow) {
        this.productsByRow = productsByRow;
    }
}
