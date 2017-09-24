package name.heshun.tmall_ssh.service;

import name.heshun.tmall_ssh.pojo.Order;
import name.heshun.tmall_ssh.pojo.OrderItem;
import name.heshun.tmall_ssh.pojo.User;

import java.util.List;

/**
 * Created by Aware on 2017/9/8.
 */
public interface OrderService extends BaseService {
    // 状态常量字符串
    String WAIT_PAY = "waitPay";
    String WAIT_DELIVERY = "waitDelivery";
    String WAIT_CONFIRM = "waitConfirm";
    String WAIT_REVIEW = "waitReview";
    String FINISH = "finish";
    String DELETE = "delete";

    /**
     * 前台: 生成订单
     *
     * @param order
     * @param ois
     * @return
     */
    float createOrder(Order order, List<OrderItem> ois);

    /**
     * 前台: 我的订单
     *
     * @param user
     * @return
     */
    List<Order> listByUserWithoutDelete(User user);
}
