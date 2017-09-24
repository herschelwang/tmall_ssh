package name.heshun.tmall_ssh.dao;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by Aware on 2017/9/8.
 * <p>
 * HibernateTemplate: 提供了各种各样的CRUD方法, 满足各种数据库操作的需要.
 */
@Repository("dao")
public class DAOImpl extends HibernateTemplate {
    /**
     * 用于注入SessionFactory
     *
     * @param sessionFactory
     */
    @Resource(name = "sf")
    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
}
