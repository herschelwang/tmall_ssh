package name.heshun.tmall_ssh.service.impl;

import name.heshun.tmall_ssh.pojo.Order;
import name.heshun.tmall_ssh.pojo.Review;
import name.heshun.tmall_ssh.service.OrderService;
import name.heshun.tmall_ssh.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Aware on 2017/9/8.
 */
@Service
public class ReviewServiceImpl extends BaseServiceImpl implements ReviewService {
    @Autowired
    private OrderService orderService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
    @Override
    public void saveReviewAndUpdateOrderStatus(Review review, Order order) {
        orderService.update(order);
        save(review);
    }
}
