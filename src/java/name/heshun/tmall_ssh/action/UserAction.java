package name.heshun.tmall_ssh.action;

import name.heshun.tmall_ssh.util.Page;
import org.apache.struts2.convention.annotation.Action;

/**
 * Created by Aware on 2017/9/8.
 * <p>
 * 用户管理
 * 1. 用户的增加, 是交由前台注册行为产生的, 后台不需要自己进行增加
 * 2. 用户信息不能删除. 用户信息是最重要的业务信息, 不可以删除
 * 3. 用户资料的修改, 也应该由前台用户自己进行，而不是后台操作
 */
public class UserAction extends Action4Result {
    @Action("admin_user_list")
    public String list() {
        if (page == null) {
            page = new Page();
        }
        int total = userService.total();
        page.setTotal(total);
        users = userService.listByPage(page);
        return "listUser";
    }
}
