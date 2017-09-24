package name.heshun.tmall_ssh.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import name.heshun.tmall_ssh.pojo.User;
import name.heshun.tmall_ssh.service.OrderItemService;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.StrutsStatics;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Arrays;

/**
 * Created by Aware on 2017/9/8.
 * <p>
 * 拦截器--登录状态(未登录部分页面不能访问)
 */
public class AuthInterceptor extends AbstractInterceptor {

    @Autowired
    private OrderItemService orderItemService;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        // 1. 存放不需要登录可以访问的路径
        String[] noNeedAuthPage = new String[]{
                "home",
                "checkLogin",
                "register",
                "loginAjax",
                "login",
                "product",
                "category",
                "search"};
        ActionContext ctx = invocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) ctx.get(StrutsStatics.HTTP_REQUEST);
        HttpServletResponse response = (HttpServletResponse) ctx.get(StrutsStatics.HTTP_RESPONSE);
        ServletContext servletContext = (ServletContext) ctx.get(StrutsStatics.SERVLET_CONTEXT);
        // 2. 获取contextPath：tmall_ssh
        String contextPath = servletContext.getContextPath();
        // 3. 获取uri
        String uri = request.getRequestURI();
        // 4. 去掉前缀/tmall_ssh
        uri = StringUtils.remove(uri, contextPath);
        // 5. 如果访问的地址是/fore开头
        if (uri.startsWith("/fore")) {
            // 5.1 取出fore后面的字符串(如: 是forecart, 那么就取出cart)
            String method = StringUtils.substringAfterLast(uri, "/fore");
            // 5.2 判断cart是否是在noNeedAuthPage 集合中
            // 5.3 如果不在, 那么就需要进行是否登录验证
            if (!Arrays.asList(noNeedAuthPage).contains(method)) {
                // 5.4 从session中取出"user"对象
                User user = (User) ctx.getSession().get("user");
                // 5.5 如果user对象不存在, 就客户端跳转到login.jsp
                if (null == user) {
                    response.sendRedirect("login.jsp");
                    return null;
                }
            }
        }
        // 5.6 否则就正常执行
        return invocation.invoke();
    }
}
