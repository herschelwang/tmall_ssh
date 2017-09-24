package name.heshun.tmall_ssh.service.impl;

import name.heshun.tmall_ssh.pojo.Order;
import name.heshun.tmall_ssh.pojo.OrderItem;
import name.heshun.tmall_ssh.service.OrderItemService;
import name.heshun.tmall_ssh.service.ProductImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Aware on 2017/9/8.
 */
@Service
public class OrderItemServiceImpl extends BaseServiceImpl implements OrderItemService {
    @Autowired
    private ProductImageService productImageService;

    @Override
    public void fill(Order order) {
        List<OrderItem> orderItems = this.listByParent(order);
        order.setOrderItems(orderItems);

        float total = 0;
        int totalNumber = 0;
        for (OrderItem oi : orderItems) {
            total += oi.getNumber() * oi.getProduct().getPromotePrice();
            totalNumber += oi.getNumber();
            productImageService.setFirstProdutImage(oi.getProduct());
        }
        order.setTotal(total);
        order.setOrderItems(orderItems);
        order.setTotalNumber(totalNumber);
    }

    @Override
    public void fill(List<Order> orders) {
        for (Order order : orders)
            fill(order);
    }
}
