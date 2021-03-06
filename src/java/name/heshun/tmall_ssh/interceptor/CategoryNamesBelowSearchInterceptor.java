package name.heshun.tmall_ssh.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import name.heshun.tmall_ssh.pojo.Category;
import name.heshun.tmall_ssh.service.CategoryService;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.StrutsStatics;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Aware on 2017/9/8.
 * <p>
 * 拦截器--搜索栏(搜索栏下需要出现分类名称)
 */
public class CategoryNamesBelowSearchInterceptor extends AbstractInterceptor {

    @Autowired
    private CategoryService categoryService;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        String result = "";
        ActionContext ctx = invocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) ctx.get(StrutsStatics.HTTP_REQUEST);
        ServletContext servletContext = (ServletContext) ctx.get(StrutsStatics.SERVLET_CONTEXT);

        String contextPath = servletContext.getContextPath();
        String uri = request.getRequestURI();
        uri = StringUtils.remove(uri, contextPath);
        // 1. 如果发现是以/fore开头的访问就进行处理
        if (uri.startsWith("/fore")) {
            // 2. 取出所有的分类对象
            List<Category> cs = categoryService.list();
            // 3. 放在session的"cs"中
            ctx.getSession().put("cs", cs);
        }
        // 4. 在search.jsp，simpleSearch.jsp中进行显示
        result = invocation.invoke();
        return result;
    }
}
