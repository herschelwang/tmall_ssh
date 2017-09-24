package name.heshun.tmall_ssh.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import name.heshun.tmall_ssh.pojo.OrderItem;
import name.heshun.tmall_ssh.pojo.User;
import name.heshun.tmall_ssh.service.OrderItemService;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.StrutsStatics;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Aware on 2017/9/8.
 * <p>
 * 拦截器--购物总数(显示购物车数量)
 */
public class CartTotalItemNumberInterceptor extends AbstractInterceptor {

    @Autowired
    private OrderItemService orderItemService;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {

        ActionContext ctx = invocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) ctx.get(StrutsStatics.HTTP_REQUEST);
        ServletContext servletContext = (ServletContext) ctx.get(StrutsStatics.SERVLET_CONTEXT);

        String contextPath = servletContext.getContextPath();
        String uri = request.getRequestURI();
        uri = StringUtils.remove(uri, contextPath);
        // 判断请求是否以/fore开头
        if (uri.startsWith("/fore")) {
            User user = (User) ActionContext.getContext().getSession().get("user");
            // 是否登录
            if (null != user) {
                int cartTotalItemNumber = 0;
                if (null != user) {
                    List<OrderItem> ois = orderItemService.list("user", user, "order", null);
                    for (OrderItem oi : ois)
                        cartTotalItemNumber += oi.getNumber();
                }
                // 如果登陆了, 就把当前用户的未设置订单的订单项取出来, 并累计其中的数量, 然后放在session的"cartTotalItemNumber" 上
                // 注: 未设置订单的订单项就是那些购物车中的数据, 否则就是订单里的数据了
                ctx.getSession().put("cartTotalItemNumber", cartTotalItemNumber);
            } else {
                // 如果未登陆, 则把session的cartTotalItemNumber设置为0
                ctx.getSession().put("cartTotalItemNumber", 0);
            }
        }
        return invocation.invoke();
    }
}
