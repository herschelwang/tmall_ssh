package name.heshun.tmall_ssh.action;

import org.apache.struts2.ServletActionContext;

import java.util.Arrays;

/**
 * Created by Aware on 2017/9/8.
 * <p>
 * 前台: 用于处理各种参数
 */
public class Action4Parameter extends Action4Result {
    /**
     * 错误信息
     */
    protected String msg;

    /**
     * 分类页面的排序变量
     */
    protected String sort;

    /**
     * 当前所处于的web应用名称(当前项目为: tmall_ssh)
     */
    protected String contextPath;

    /**
     * 查询关键字
     */
    protected String keyword;

    /**
     * 购买数量
     */
    protected int num;

    /**
     * 立即购买时生成的订单项ID
     */
    protected int oiid;

    /**
     * 购物车结算时选中的多个订单项ID
     */
    protected int[] oiids;

    /**
     * 结算页面显示的总金额
     */
    protected float total;

    /**
     * 在进行评论的页面时, 是否只显示评论记录, 而不提供输入
     */
    protected boolean showonly;

    /**
     * 构造方法
     */
    public Action4Parameter() {
        contextPath = ServletActionContext.getServletContext().getContextPath();
    }

    @Override
    public String toString() {
        return "Action4Parameter{" +
                "msg='" + msg + '\'' +
                ", sort='" + sort + '\'' +
                ", contextPath='" + contextPath + '\'' +
                ", keyword='" + keyword + '\'' +
                ", num=" + num +
                ", oiid=" + oiid +
                ", oiids=" + Arrays.toString(oiids) +
                ", total=" + total +
                ", showonly=" + showonly +
                '}';
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getOiid() {
        return oiid;
    }

    public void setOiid(int oiid) {
        this.oiid = oiid;
    }

    public int[] getOiids() {
        return oiids;
    }

    public void setOiids(int[] oiids) {
        this.oiids = oiids;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public boolean isShowonly() {
        return showonly;
    }

    public void setShowonly(boolean showonly) {
        this.showonly = showonly;
    }
}
