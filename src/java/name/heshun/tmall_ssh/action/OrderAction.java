package name.heshun.tmall_ssh.action;

import name.heshun.tmall_ssh.service.OrderService;
import name.heshun.tmall_ssh.util.Page;

import org.apache.struts2.convention.annotation.Action;

import java.util.Date;

/**
 * Created by Aware on 2017/9/8.
 */
public class OrderAction extends Action4Result {
    @Action("admin_order_list")
    public String list() {
        if (page == null) {
            page = new Page();
        }
        int total = orderService.total();
        page.setTotal(total);
        orders = orderService.listByPage(page);
        orderItemService.fill(orders);
        return "listOrder";
    }

    @Action("admin_order_delivery")
    public String delivery() {
        // 把order对象转换为持久化对象
        t2p(order);
        // 修改发货时间
        order.setDeliveryDate(new Date());
        // 设置发货状态
        order.setStatus(OrderService.WAIT_CONFIRM);
        orderService.update(order);
        return "listOrderPage";
    }
}
