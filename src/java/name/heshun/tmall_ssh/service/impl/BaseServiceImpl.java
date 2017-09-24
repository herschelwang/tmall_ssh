package name.heshun.tmall_ssh.service.impl;

import name.heshun.tmall_ssh.service.BaseService;
import name.heshun.tmall_ssh.util.Page;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by Aware on 2017/9/8.
 * <p>
 * 业务层公共接口实现类
 */
@Service
public class BaseServiceImpl extends ServiceDelegateDAO implements BaseService {
    /**
     * 对象类型
     */
    protected Class clazz;

    /**
     * 构造方法, 借助异常处理和反射, 做到哪个类继承了BaseServiceImpl, clazz就对应哪个类对象.
     */
    public BaseServiceImpl() {
        /*
        实例化子类, 父类的构造方法一定会被调用.
        在父类BaseServiceImpl里故意抛出一个异常, 然后手动捕捉住它, 在其对应的StackTrace里的第二个(下标是1)栈跟踪元素StackTraceElement, 即对应子类.
        这样我们就拿到了子类的类名.
         */
        try {
            throw new Exception();
        } catch (Exception e) {
            StackTraceElement stes[] = e.getStackTrace();
            String serviceImpleClassName = stes[1].getClassName();
            /*
            拿到子类类名后, 通过字符串替换, 拼接和反射, 就得到了对应的实体类的类对象.
            注意: 这样做的前提是实现类放在xxx.service.impl包下, 实体类放在xxx.pojo包下.
             */
            try {
                Class serviceImplClazz = Class.forName(serviceImpleClassName);
                String serviceImpleClassSimpleName = serviceImplClazz.getSimpleName();
                String pojoSimpleName = serviceImpleClassSimpleName.replaceAll("ServiceImpl", "");
                String pojoPackageName = serviceImplClazz.getPackage().getName().replaceAll(".service.impl", ".pojo");
                String pojoFullName = pojoPackageName + "." + pojoSimpleName;
                clazz = Class.forName(pojoFullName);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

    /*
    作为委派模式, ServiceDelegateDAO提供的save方法返回类型是Serializable(自增长id).
    按照重写原则, 返回类型只能是Serializable 或者Serializable 的子类, 这里选择的是Integer.
    注: 所有的封装类都继承了Number这个父类, 而Number实现了Serializable接口.
     */
    @Override
    public Integer save(Object object) {
        return (Integer) dao.save(object);
    }

    /*
    因为继承了ServiceDelegateDAO，所以就继承了update和delete方法
    @Override
    public void update(Object object) {
        dao.update(object);
    }

    @Override
    public void delete(Object object) {
        dao.delete(object);
    }
    */

    @Override
    public Object get(int id) {
        return dao.get(clazz, id);
    }

    @Override
    public Object get(Class clazz, int id) {
        return dao.get(clazz, id);
    }

    @Override
    public List list() {
        DetachedCriteria dc = DetachedCriteria.forClass(clazz);
        dc.addOrder(Order.desc("id"));
        return dao.findByCriteria(dc);
    }

    @Override
    public List listByPage(Page page) {
        DetachedCriteria dc = DetachedCriteria.forClass(clazz);
        dc.addOrder(Order.desc("id"));
        return dao.findByCriteria(dc, page.getStart(), page.getCount());
    }

    @Override
    public int total() {
        String hsql = "select count(*) from " + clazz.getName();
        List<Long> list = (List<Long>) dao.find(hsql);
        if (list.isEmpty()) {
            return 0;
        }
        Long result = list.get(0);
        return result.intValue();
    }

    @Override
    public List listByParent(Object parent) {
        // 通过反射获取父类的类名
        String parentName = parent.getClass().getSimpleName();
        // 将第一个字母变成小写
        String parentNameWithFirstLetterLower = StringUtils.uncapitalize(parentName);
        //
        DetachedCriteria dc = DetachedCriteria.forClass(clazz);
        dc.add(Restrictions.eq(parentNameWithFirstLetterLower, parent));
        dc.addOrder(Order.desc("id"));
        return findByCriteria(dc);
    }

    @Override
    public List list(Page page, Object parent) {
        String parentName = parent.getClass().getSimpleName();
        String parentNameWithFirstLetterLower = StringUtils.uncapitalize(parentName);
        DetachedCriteria dc = DetachedCriteria.forClass(clazz);
        dc.add(Restrictions.eq(parentNameWithFirstLetterLower, parent));
        dc.addOrder(Order.desc("id"));
        return findByCriteria(dc, page.getStart(), page.getCount());
    }

    @Override
    public int total(Object parentObject) {
        String parentName = parentObject.getClass().getSimpleName();
        String parentNameWithFirstLetterLower = StringUtils.uncapitalize(parentName);

        String sqlFormat = "select count(*) from %s bean where bean.%s = ?";
        String hql = String.format(sqlFormat, clazz.getName(), parentNameWithFirstLetterLower);

        List<Long> list = this.find(hql, parentObject);
        if (list.isEmpty()) {
            return 0;
        }
        Long result = list.get(0);
        return result.intValue();
    }

    @Override
    public List list(Object... pairParms) {
        // 1.将可变数量的参数, 按照 key--value的预判读取出来, 并放进map中
        HashMap<String, Object> map = new HashMap<>();
        for (int i = 0; i < pairParms.length; i = i + 2) {
            map.put(pairParms[i].toString(), pairParms[i + 1]);
        }
        // 2.遍历这个map, 借助DetachedCriteria, 按照 key--value的方式设置查询条件
        DetachedCriteria dc = DetachedCriteria.forClass(clazz);
        Set<String> keys = map.keySet();
        for (String key : keys) {
            // 当value是null的时候, 需要使用dc.add(Restrictions.isNull(key)); 这样风格的代码进行查询
            if (null == map.get(key)) {
                dc.add(Restrictions.isNull(key));
            } else {
                dc.add(Restrictions.eq(key, map.get(key)));
            }
        }
        // 按照id反向排序
        dc.addOrder(Order.desc("id"));
        // 返回查询结果
        return this.findByCriteria(dc);
    }
}
