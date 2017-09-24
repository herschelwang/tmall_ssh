package name.heshun.tmall_ssh.service.impl;

import name.heshun.tmall_ssh.pojo.Product;
import name.heshun.tmall_ssh.pojo.ProductImage;
import name.heshun.tmall_ssh.service.ProductImageService;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Aware on 2017/9/8.
 */
@Service
public class ProductImageServiceImpl extends BaseServiceImpl implements ProductImageService {
    @Override
    public void setFirstProdutImage(Product product) {
        if (null != product.getFirstProductImage()) {
            return;
        }
        List<ProductImage> pis = list("product", product, "type", ProductImageService.TYPE_SINGLE);
        if (!pis.isEmpty()) {
            product.setFirstProductImage(pis.get(0));
        }
    }
}
