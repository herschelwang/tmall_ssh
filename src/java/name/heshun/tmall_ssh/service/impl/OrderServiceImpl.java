package name.heshun.tmall_ssh.service.impl;

import name.heshun.tmall_ssh.pojo.Order;
import name.heshun.tmall_ssh.pojo.OrderItem;
import name.heshun.tmall_ssh.pojo.User;
import name.heshun.tmall_ssh.service.OrderItemService;
import name.heshun.tmall_ssh.service.OrderService;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Aware on 2017/9/8.
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl implements OrderService {
    @Autowired
    private OrderItemService orderItemService;

    // 因为插入订单和修改订单项应该是要么都成功，要么都失败，所以在createOrder前加上了事务注解：
    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
    @Override
    public float createOrder(Order order, List<OrderItem> ois) {
        // 1. 把订单插入到数据库中
        save(order);
        float total = 0;
        // 2. 为每个OrderItem设置其订单
        for (OrderItem oi : ois) {
            oi.setOrder(order);
            orderItemService.update(oi);
            // 3. 累计金额并返回
            total += oi.getProduct().getPromotePrice() * oi.getNumber();
        }
        return total;
    }

    @Override
    public List<Order> listByUserWithoutDelete(User user) {
        // 1. 创建DetachedCriteria 对象
        DetachedCriteria dc = DetachedCriteria.forClass(clazz);
        // 2. 条件加上user
        dc.add(Restrictions.eq("user", user));
        // 3. 条件加上 status不等于delete
        dc.add(Restrictions.ne("status", OrderService.DELETE));
        // 4. 返回查询结果
        return findByCriteria(dc);
        /*
        这里要用到非条件, 所以不能用BaseService.list(Object ...pairParms)的多条件查询
         */
    }
}
