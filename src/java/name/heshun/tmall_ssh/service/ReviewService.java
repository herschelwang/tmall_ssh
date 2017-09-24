package name.heshun.tmall_ssh.service;

import name.heshun.tmall_ssh.pojo.Order;
import name.heshun.tmall_ssh.pojo.Review;

/**
 * Created by Aware on 2017/9/8.
 */
public interface ReviewService extends BaseService {
    /**
     * 设置评论并更新订单状态
     *
     * @param review
     * @param order
     */
    void saveReviewAndUpdateOrderStatus(Review review, Order order);
}
