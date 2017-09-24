package name.heshun.tmall_ssh.action;


import name.heshun.tmall_ssh.util.Page;

/**
 * Created by Aware on 2017/9/8.
 * <p>
 * 提供分页实现
 */
public class Action4Pagination extends Action4Upload {
    protected Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}