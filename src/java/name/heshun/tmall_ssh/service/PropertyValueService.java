package name.heshun.tmall_ssh.service;

import name.heshun.tmall_ssh.pojo.Product;

/**
 * Created by Aware on 2017/9/8.
 */
public interface PropertyValueService extends BaseService{
    /**
     * 初始化PropertyValue
     * 对于PropertyValue的管理, 没有增加, 只有修改
     * 所以需要通过初始化来进行自动地增加, 以便于后面的修改
     *
     * @param product
     */
    void init(Product product);
}
