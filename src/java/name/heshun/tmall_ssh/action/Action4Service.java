package name.heshun.tmall_ssh.action;

import name.heshun.tmall_ssh.service.*;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

/**
 * Created by Aware on 2017/9/8.
 * <p>
 * 提供service的注入.
 */
public class Action4Service extends Action4Pojo {
    @Autowired
    protected CategoryService categoryService;
    @Autowired
    protected PropertyService propertyService;
    @Autowired
    protected ProductService productService;
    @Autowired
    protected ProductImageService productImageService;
    @Autowired
    protected PropertyValueService propertyValueService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected OrderService orderService;
    @Autowired
    protected OrderItemService orderItemService;
    @Autowired
    protected ReviewService reviewService;

    /**
     * transient to persistent
     * 瞬时对象转换为持久对象.
     * 用于处理需求: 根据浏览器传递过来的分类id, 去获取一个分类对象.
     *
     * @param obj
     */
    public void t2p(Object obj) {
        try {
            // 1.获取瞬时对象的类对象.
            Class clazz = obj.getClass();
            // 2.通过反射, 调用这个瞬时对象的getId方法获取id.
            int id = (Integer) clazz.getMethod("getId").invoke(obj);
            // 3.根据id, 获取持久化对象.
            Object persistentBean = categoryService.get(clazz, id);
            // 4. 因为Action4Service继承了Action4Pojo, 所以提供了改持久对象对应的setXXX(如:setCategory)方法,
            // 通过反射, 把该持久对象设置在XXX(如:category)引用上.
            String pojoName = clazz.getSimpleName();
            Method setMethod = getClass().getMethod("set" + WordUtils.capitalize(pojoName), clazz);
            setMethod.invoke(this, persistentBean);
            // 5.这样就实现了父类Action4Pojo中声明的XXX(category)本身是指向瞬时对象的, 现在指向了持久对象.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
