package name.heshun.tmall_ssh.service;

import name.heshun.tmall_ssh.pojo.Order;

import java.util.List;

/**
 * Created by Aware on 2017/9/8.
 */
public interface OrderItemService extends BaseService {
    /**
     * 为订单对象填充其orderItems字段, 并且计算出订单总金额, 订单总购买数量
     *
     * @param order
     */
    void fill(Order order);

    /**
     * 为多个订单对象填充其orderItems字段, 并且计算出订单总金额, 订单总购买数量
     *
     * @param orders
     */
    void fill(List<Order> orders);
}
