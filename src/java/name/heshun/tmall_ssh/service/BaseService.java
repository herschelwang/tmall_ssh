package name.heshun.tmall_ssh.service;

import name.heshun.tmall_ssh.util.Page;

import java.util.List;

/**
 * Created by Aware on 2017/9/8.
 * <p>
 * 业务层公共接口
 */
public interface BaseService {
    // CURD
    Integer save(Object object);

    void update(Object object);

    void delete(Object object);

    Object get(int id);

    Object get(Class clazz, int id);

    /**
     * 获取所有对象
     *
     * @return
     */
    List list();

    /**
     * 获取所有对象(分页)
     *
     * @param page
     * @return
     */
    List listByPage(Page page);

    /**
     * 获取总数
     *
     * @return
     */
    int total();

    /**
     * 查询某个分类下的所有属性
     *
     * @param parent
     * @return
     */
    List listByParent(Object parent);

    /**
     * 根据父类查询子类对象(分页)
     *
     * @param page
     * @param parent
     * @return
     */
    List list(Page page, Object parent);

    /**
     * 根据父类查询其子类对象集合总数
     *
     * @param parentObject
     * @return
     */
    int total(Object parentObject);

    /**
     * 多条件查询
     * 注意: 调用这个方法的时候, 应该提供偶数个参数(key--value), 否则会报错
     *
     * @param pairParms
     * @return
     */
    List list(Object... pairParms);
}
