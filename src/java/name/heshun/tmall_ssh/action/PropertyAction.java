package name.heshun.tmall_ssh.action;

import name.heshun.tmall_ssh.util.Page;
import org.apache.struts2.convention.annotation.Action;

/**
 * Created by Aware on 2017/9/8.
 */
public class PropertyAction extends Action4Result {
    @Action("admin_property_list")
    public String list() {
        // 判断是否有分页对象, 如果没有, 则创建一个新的.
        if (page == null) {
            page = new Page();
        }
        // 获取当前分类下数据总数.
        int total = propertyService.total(category);
        // 设置总数
        page.setTotal(total);
        // 设置参数
        page.setParam("&category.id=" + category.getId());
        // 根据分页对象和分类对象获取属性集合
        propertys = propertyService.list(page, category);
        // 让category引用从指向瞬时对象，变为指向持久对象。
        // 因为在后面步骤的 其他-面包屑导航 需要显示分类的名称
        t2p(category);
        return "listProperty";
    }

    @Action("admin_property_edit")
    public String edit() {
        t2p(property);
        return "editProperty";
    }

    @Action("admin_property_add")
    public String add() {
        propertyService.save(property);
        return "listPropertyPage";
    }

    @Action("admin_property_delete")
    public String delete() {
        t2p(property);
        propertyService.delete(property);
        return "listPropertyPage";
    }

    @Action("admin_property_update")
    public String update() {
        propertyService.update(property);
        return "listPropertyPage";
    }
}
