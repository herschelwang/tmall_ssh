package name.heshun.tmall_ssh.action;

import com.opensymphony.xwork2.ActionContext;
import name.heshun.tmall_ssh.pojo.OrderItem;
import name.heshun.tmall_ssh.pojo.Product;
import name.heshun.tmall_ssh.pojo.User;
import name.heshun.tmall_ssh.service.OrderService;
import name.heshun.tmall_ssh.service.ProductImageService;

import org.apache.commons.lang.xwork.math.RandomUtils;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.web.util.HtmlUtils;

import javax.xml.ws.Service;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Aware on 2017/9/8.
 * <p>
 * 用来对应前台页面路径
 */
public class ForeAction extends Action4Parameter {
    /**
     * 提交评价
     *
     * @return
     */
    @Action("foredoreview")
    public String doreview() {
        t2p(order);
        t2p(product);
        // 修改订单状态
        order.setStatus(OrderService.FINISH);
        // 对评价信息进行转义
        String content = review.getContent();
        content = HtmlUtils.htmlEscape(content);
        review.setContent(content);
        // 获取当前用户
        User user = (User) ActionContext.getContext().getSession().get("user");
        // 设置
        review.setContent(content);
        review.setProduct(product);
        review.setCreateDate(new Date());
        review.setUser(user);
        // 保存
        reviewService.saveReviewAndUpdateOrderStatus(review, order);

        showonly = true;
        return "reviewPage";
    }

    /**
     * 评价产品
     *
     * @return
     */
    @Action("forereview")
    public String review() {
        t2p(order);
        // 填充订单项
        orderItemService.fill(order);
        // 获取第一个订单项对应的产品,因为在评价页面需要显示一个产品图片，那么就使用这第一个产品的图片了
        product = order.getOrderItems().get(0).getProduct();
        //  获取这个产品的评价集合
        reviews = reviewService.listByParent(product);
        // 为产品设置评价数量和销量
        productService.setSaleAndReviewNumber(product);
        return "review.jsp";
    }

    /**
     * 删除订单
     *
     * @return
     */
    @Action("foredeleteOrder")
    public String deleteOrder() {
        t2p(order);
        order.setStatus(OrderService.DELETE);
        orderService.update(order);
        return "success.jsp";
    }

    /**
     * 确认支付成功
     *
     * @return
     */
    @Action("foreorderConfirmed")
    public String orderConfirmed() {
        t2p(order);
        order.setStatus(OrderService.WAIT_REVIEW);
        // 修改确认支付时间
        order.setConfirmDate(new Date());
        orderService.update(order);
        return "orderConfirmed.jsp";
    }

    /**
     * 确认支付
     *
     * @return
     */
    @Action("foreconfirmPay")
    public String confirmPay() {
        t2p(order);
        orderItemService.fill(order);
        return "confirmPay.jsp";
    }

    /**
     * 我的订单
     *
     * @return
     */
    @Action("forebought")
    public String bought() {
        User user = (User) ActionContext.getContext().getSession().get("user");
        orders = orderService.listByUserWithoutDelete(user);
        // 为这些订单填充订单项
        orderItemService.fill(orders);
        return "bought.jsp";
    }

    /**
     * 支付成功
     *
     * @return
     */
    @Action("forepayed")
    public String payed() {
        t2p(order);
        order.setStatus(OrderService.WAIT_DELIVERY);
        order.setPayDate(new Date());
        orderService.update(order);
        return "payed.jsp";
    }

    /**
     * 确认支付
     *
     * @return
     */
    @Action("forealipay")
    public String forealipay() {
        return "alipay.jsp";
    }

    /**
     * 创建订单
     *
     * @return
     */
    @Action("forecreateOrder")
    public String createOrder() {
        // 1. 从session获取订单项集合. (在结算功能的ForeAction.buy()中, 订单项集合被放到了session中)
        List<OrderItem> ois = (List<OrderItem>) ActionContext.getContext().getSession().get("orderItems");
        // 2. 如果订单项集合是空，则跳转到登陆页面
        if (ois.isEmpty()) {
            return "login.jsp";
        }
        // 3. 从session中获取user对象
        User user = (User) ActionContext.getContext().getSession().get("user");
        // 4. 根据当前时间加上一个4位随机数生成订单号
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt();
        // 5. 根据上述参数，创建订单对象
        order.setOrderCode(orderCode);
        order.setCreateDate(new Date());
        order.setUser(user);
        // 6. 把订单状态设置为等待支付
        order.setStatus(OrderService.WAIT_PAY);
        // 7. 把订单插入到数据库
        total = orderService.createOrder(order, ois);
        // 8. 转发到确认支付
        return "alipayPage";
    }

    /**
     * 删除订单项
     *
     * @return
     */
    @Action("foredeleteOrderItem")
    public String deleteOrderItem() {
        orderItemService.delete(orderItem);
        return "success.jsp";
    }

    /**
     * 调整订单数量
     *
     * @return
     */
    @Action("forechangeOrderItem")
    public String changeOrderItem() {
        // 1. 获取当前用户
        User user = (User) ActionContext.getContext().getSession().get("user");
        List<OrderItem> ois = orderItemService.list("user", user, "order", null);
        // 2. 遍历出用户当前所有的未生成订单的OrderItem
        for (OrderItem oi : ois) {
            // 3. 根据product.id找到匹配的OrderItem, 并修改数量后更新到数据库
            if (oi.getProduct().getId() == product.getId()) {
                oi.setNumber(num);
                orderItemService.update(oi);
                break;
            }
        }
        // 4. 返回字符串"success"
        return "success.jsp";
    }

    /**
     * 查看购物车
     *
     * @return
     */
    @Action("forecart")
    public String cart() {
        // 1. 通过session获取当前用户(所以一定要登录才访问, 否则拿不到用户对象)
        User user = (User) ActionContext.getContext().getSession().get("user");
        // 2. 获取未这个用户关联的订单项集合 orderItems
        orderItems = orderItemService.list("user", user, "order", null);
        for (OrderItem orderItem : orderItems) {
            productImageService.setFirstProdutImage(orderItem.getProduct());
        }
        return "cart.jsp";
    }

    /**
     * 加入购物车
     *
     * @return
     */
    @Action("foreaddCart")
    public String addCart() {
        User user = (User) ActionContext.getContext().getSession().get("user");
        boolean found = false;
        List<OrderItem> ois = orderItemService.list("user", user, "order", null);
        for (OrderItem oi : ois) {
            if (oi.getProduct().getId() == product.getId()) {
                oi.setNumber(oi.getNumber() + num);
                orderItemService.update(oi);
                found = true;
                break;
            }
        }
        if (!found) {
            OrderItem oi = new OrderItem();
            oi.setUser(user);
            oi.setNumber(num);
            oi.setProduct(product);
            orderItemService.save(oi);
        }
        return "success.jsp";
    }

    /**
     * 结算
     *
     * @return
     */
    @Action("forebuy")
    public String buy() {
        // 1. 获取参数：数组 oiid(为了兼容从购物车页面跳转过来的需求, 使用int数组来接收参数)
        // 2. 让orderItems 指向一个新的ArrayList
        orderItems = new ArrayList<>();
        // 3. 根据前面步骤获取的oiids, 从数据库中取出OrderItem对象, 并放入orderItems 集合中
        for (int oiid : oiids) {
            // 4. 累计这些ois的价格总数，赋值在total上
            OrderItem oi = (OrderItem) orderItemService.get(oiid);
            total += oi.getProduct().getPromotePrice() * oi.getNumber();
            orderItems.add(oi);
            productImageService.setFirstProdutImage(oi.getProduct());
        }
        // 5. 把订单项集合放在session的属性 "orderItems"上(后续生成订单的时候还会用到)
        ActionContext.getContext().getSession().put("orderItems", orderItems);
        return "buy.jsp";
    }

    /**
     * 购买
     *
     * @return
     */
    @Action("forebuyone")
    public String buyone() {
        // 从session中获取用户对象user
        User user = (User) ActionContext.getContext().getSession().get("user");
        // 默认不存在订单项
        boolean found = false;
        // 遍历这个用户的订单项, 如果有相同的产品, 就进行数量追加, 然后获取这个订单项ID
        List<OrderItem> ois = orderItemService.list("user", user, "order", null);
        for (OrderItem oi : ois) {
            if (oi.getProduct().getId() == product.getId()) {
                oi.setNumber(oi.getNumber() + num);
                orderItemService.update(oi);
                found = true;
                oiid = oi.getId();
                break;
            }
        }
        // 如果项不存在, 则新建一个订单项
        if (!found) {
            OrderItem oi = new OrderItem();
            oi.setUser(user);
            oi.setNumber(num);
            oi.setProduct(product);
            orderItemService.save(oi);
            oiid = oi.getId();
        }
        return "buyPage";
    }

    /**
     * 退出登录
     *
     * @return
     */
    @Action("forelogout")
    public String logout() {
        ActionContext.getContext().getSession().remove("user");
        return "homePage";
    }

    /**
     * 搜索
     *
     * @return
     */
    @Action("foresearch")
    public String search() {
        // 1. 获取参数keyword
        // 2. 根据keyword进行模糊查询, 获取满足条件的前20个产品
        products = productService.search(keyword, 0, 20);
        // 3. 为这些产品设置销量和评价数量
        productService.setSaleAndReviewNumber(products);
        for (Product product : products) {
            productImageService.setFirstProdutImage(product);
        }
        // 4. 转发到searchResult.jsp
        return "searchResult.jsp";
    }

    @Action("forecategory")
    public String category() {
        // 1. category指向持久化对象
        t2p(category);
        // 2. 为category填充产品
        productService.fill(category);
        // 3. 为产品填充销量和评价数据
        productService.setSaleAndReviewNumber(category.getProducts());
        // 4. 获取参数sort: 根据sort的值排序或不排序(null)
        if (null != sort) {
            switch (sort) {
                // 按照评价数量排序
                case "review":

                    Collections.sort(category.getProducts(), new Comparator<Product>() {
                        @Override
                        public int compare(Product p1, Product p2) {
                            return p2.getReviewCount() - p1.getReviewCount();
                        }
                    });

                    // 使用Lambda表达式, 等价于上面
                    //category.getProducts().sort((p1, p2) -> p2.getReviewCount() - p1.getReviewCount());
                    break;
                // 按照日期排序
                case "date":

                    Collections.sort(category.getProducts(), new Comparator<Product>() {
                        @Override
                        public int compare(Product p1, Product p2) {
                            return p1.getCreateDate().compareTo(p2.getCreateDate());
                        }
                    });
                    //category.getProducts().sort((p1, p2) -> p1.getCreateDate().compareTo(p2.getCreateDate()));
                    break;
                // 按照销量排序
                case "saleCount":

                    Collections.sort(category.getProducts(), new Comparator<Product>() {
                        @Override
                        public int compare(Product p1, Product p2) {
                            return p2.getSaleCount() - p1.getSaleCount();
                        }
                    });
                    //category.getProducts().sort((p1, p2) -> p2.getSaleCount() - p1.getSaleCount());
                    break;
                // 按照价格排序
                case "price":

                    Collections.sort(category.getProducts(), new Comparator<Product>() {
                        @Override
                        public int compare(Product p1, Product p2) {
                            return (int) (p1.getPromotePrice() - p2.getPromotePrice());
                        }
                    });
                    //category.getProducts().sort((p1, p2) -> (int) (p1.getPromotePrice() - p2.getPromotePrice()));
                    break;
                // 按照 销量*评价排序
                case "all":

                    Collections.sort(category.getProducts(), new Comparator<Product>() {
                        @Override
                        public int compare(Product p1, Product p2) {
                            return p2.getReviewCount() * p2.getSaleCount() - p1.getReviewCount() * p1.getSaleCount();
                        }
                    });
                    //category.getProducts().sort((p1, p2) -> p2.getReviewCount() * p2.getSaleCount() - p1.getReviewCount() * p1.getSaleCount());
                    break;
            }
        }
        return "category.jsp";
    }

    @Action("foreloginAjax")
    public String loginAjax() {
        // 1. 将账号转义, 因为数据库里保存的是转义过后的
        user.setName(HtmlUtils.htmlEscape(user.getName()));
        // 2. 通过账号密码获取User对象
        User user_session = userService.get(user.getName(), user.getPassword());
        // 2.1 如果User对象为空, 那么就返回"fail"字符串。
        if (null == user_session) {
            return "fail.jsp";
        }
        // 2.2 如果User对象不为空, 那么就把User对象放在session中, 并返回"success"字符串
        ActionContext.getContext().getSession().put("user", user_session);
        return "success.jsp";
    }

    @Action("forecheckLogin")
    public String checkLogin() {
        // 获取session中的"user"对象
        User user = (User) ActionContext.getContext().getSession().get("user");
        // 为空则表示未登录
        if (null == user) {
            return "fail.jsp";
        }
        return "success.jsp";
    }

    @Action("foreproduct")
    public String product() {
        // 1.对象持久化
        t2p(product);
        // 2.设置首张图片
        productImageService.setFirstProdutImage(product);
        // 3.设置展示图片和详情图片集合
        productSingleImages = productImageService.list("product", product, "type", ProductImageService.TYPE_SINGLE);
        productDetailImages = productImageService.list("product", product, "type", ProductImageService.TYPE_DETAIL);
        product.setProductSingleImages(productSingleImages);
        product.setProductDetailImages(productDetailImages);
        // 4.获取本产品的属性值集合
        propertyValues = propertyValueService.listByParent(product);
        // 5.获取本产品的评价集合
        reviews = reviewService.listByParent(product);
        // 6.设置销售数量和评价数量
        productService.setSaleAndReviewNumber(product);
        // 转发至product.jsp
        return "product.jsp";
    }

    @Action("forelogin")
    public String login() {
        // 通过账号转义后获取User对象
        user.setName(HtmlUtils.htmlEscape(user.getName()));
        User user_session = userService.get(user.getName(), user.getPassword());
        // 如果对象为空, 则转发到login.jsp, 并带上错误提示信息
        if (null == user_session) {
            msg = "账号密码错误";
            return "login.jsp";
        }
        // 如果对象存在, 则将对象保存到session中, 并转发到首页
        ActionContext.getContext().getSession().put("user", user_session);
        return "homePage";
    }

    @Action("foreregister")
    public String register() {
        // 通过HtmlUtils.htmlEscape();把账号里的特殊符号进行转义(防止恶意注册)
        user.setName(HtmlUtils.htmlEscape(user.getName()));
        boolean exist = userService.isExist(user.getName());
        // 如果用户名已经存在, 则转发到reigster.jsp, 并带上错误提示信息
        if (exist) {
            msg = "用户名已经被使用,不能使用";
            return "register.jsp";
        }
        // 不存在则保存到数据库, 并转发到registerSuccess.jsp
        userService.save(user);
        return "registerSuccessPage";
    }

    @Action("forehome")
    public String home() {
        // 查询所有分类
        categorys = categoryService.list();
        // 为这些分类填充产品集合
        productService.fill(categorys);
        // 为这些分类填充推荐产品集合
        productService.fillByRow(categorys);
        // 转发到home.jsp
        return "home.jsp";
    }
}
