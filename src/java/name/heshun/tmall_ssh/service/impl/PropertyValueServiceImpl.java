package name.heshun.tmall_ssh.service.impl;

import name.heshun.tmall_ssh.pojo.Product;
import name.heshun.tmall_ssh.pojo.Property;
import name.heshun.tmall_ssh.pojo.PropertyValue;
import name.heshun.tmall_ssh.service.PropertyService;
import name.heshun.tmall_ssh.service.PropertyValueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Aware on 2017/9/8.
 */
@Service
public class PropertyValueServiceImpl extends BaseServiceImpl implements PropertyValueService {

    @Autowired
    private PropertyService propertyService;

    @Override
    public void init(Product product) {
        // 根据产品获取分类, 获取这个分类下的所有属性集合
        List<Property> propertys = propertyService.listByParent(product.getCategory());
        for (Property property : propertys) {
            // 根据属性和产品查询属性值, 如果不存在, 就创建一个, 并设置其属性和产品
            PropertyValue propertyValue = get(property, product);
            if (null == propertyValue) {
                propertyValue = new PropertyValue();
                propertyValue.setProduct(product);
                propertyValue.setProperty(property);
                save(propertyValue);
            }
        }
    }

    private PropertyValue get(Property property, Product product) {
        List<PropertyValue> result = this.list("property", property, "product", product);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }
}
