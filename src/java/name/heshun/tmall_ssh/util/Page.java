package name.heshun.tmall_ssh.util;

/**
 * Created by Aware on 2017/9/8.
 * <p>
 * 这个类专门为分页提供必要信息
 */
public class Page {
    private int start; //开始位置(从0开始, 相对于total)
    private int count; //每页显示的数量
    private int total; //总数
    private String param; //参数
    private static final int defaultCount = 5; //默认每页显示5条

    /**
     * 获取总页数
     *
     * @return
     */
    public int getTotalPage() {
        int totalPage;
        if (0 == total % count) {
            // 假设总数是50，是能够被5整除的，那么就有10页
            totalPage = total / count;
        } else {
            // 假设总数是51，不能够被5整除的，那么就有11页
            totalPage = total / count + 1;
        }
        if (0 == totalPage) {
            totalPage = 1;
        }
        return totalPage;
    }

    /**
     * 获取最后一页显示的开始位置
     *
     * @return
     */
    public int getLast() {
        int last;
        if (0 == total % count) {
            // 假设总数是50，是能够被5整除的，那么最后一页的开始就是45
            last = total - count;
        } else {
            // 假设总数是51，不能够被5整除的，那么最后一页的开始就是50
            last = total - total % count;
        }
        last = last < 0 ? 0 : last;
        return last;
    }

    /**
     * 判断是否有前一页
     *
     * @return
     */
    public boolean isHasPreviouse() {
        return start == 0 ? false : true;
    }

    /**
     * 判断是否有后一页
     *
     * @return
     */
    public boolean isHasNext() {
        return start == getLast() ? false : true;
    }

    /**
     * 无参构造
     */
    public Page() {
        count = defaultCount;
    }

    /**
     * 有参构造
     *
     * @param start
     * @param count
     */
    public Page(int start, int count) {
        this.start = start;
        this.count = count;
    }

    @Override
    public String toString() {
        return "Page{" +
                "start=" + start +
                ", count=" + count +
                ", total=" + total +
                ", param='" + param + '\'' +
                '}';
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public static int getDefaultCount() {
        return defaultCount;
    }
}
