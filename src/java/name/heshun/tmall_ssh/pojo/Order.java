package name.heshun.tmall_ssh.pojo;

import name.heshun.tmall_ssh.service.OrderService;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Aware on 2017/9/8.
 */
@Entity
@Table(name = "order_")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;

    @Column(name = "orderCode")
    private String orderCode;
    @Column(name = "address")
    private String address;
    @Column(name = "post")
    private String post;
    @Column(name = "receiver")
    private String receiver;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "userMessage")
    private String userMessage;
    @Column(name = "createDate")
    private Date createDate;
    @Column(name = "payDate")
    private Date payDate;
    @Column(name = "deliveryDate")
    private Date deliveryDate;
    @Column(name = "confirmDate")
    private Date confirmDate;
    @Column(name = "status")
    private String status;

    @Transient
    private List<OrderItem> orderItems;
    @Transient
    private float total;
    @Transient
    private int totalNumber;

    public String getStatusDesc() {
        String desc = "未知";
        // jdk1.7
        switch (status) {
            case OrderService.WAIT_PAY:
                desc = "待付款";
                break;
            case OrderService.WAIT_DELIVERY:
                desc = "待发货";
                break;
            case OrderService.WAIT_CONFIRM:
                desc = "待收货";
                break;
            case OrderService.WAIT_REVIEW:
                desc = "等评价";
                break;
            case OrderService.FINISH:
                desc = "完成";
                break;
            case OrderService.DELETE:
                desc = "刪除";
                break;
            default:
                desc = "未知";
        }
        return desc;
    }

    /**
     * 无参构造
     */
    public Order() {
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", orderCode='" + orderCode + '\'' +
                ", address='" + address + '\'' +
                ", post='" + post + '\'' +
                ", receiver='" + receiver + '\'' +
                ", mobile='" + mobile + '\'' +
                ", userMessage='" + userMessage + '\'' +
                ", createDate=" + createDate +
                ", payDate=" + payDate +
                ", deliveryDate=" + deliveryDate +
                ", confirmDate=" + confirmDate +
                ", status='" + status + '\'' +
                ", orderItems=" + orderItems +
                ", total=" + total +
                ", totalNumber=" + totalNumber +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }
}
