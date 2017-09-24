package name.heshun.tmall_ssh.action;

import name.heshun.tmall_ssh.pojo.*;

import java.util.List;

/**
 * Created by Aware on 2017/9/8.
 * <p>
 * 用于提供实体对象和实体对象集合的setter and getter.
 * setter用于接收注入.
 * getter用于提供数据到jsp(view)上.
 */
public class Action4Pojo extends Action4Pagination {
    protected Category category;
    protected Property property;
    protected Product product;
    protected ProductImage productImage;
    protected PropertyValue propertyValue;
    protected User user;
    protected Order order;
    protected Review review;
    protected OrderItem orderItem;

    protected List<Category> categorys;
    protected List<Property> propertys;
    protected List<Product> products;
    protected List<ProductImage> productSingleImages;
    protected List<ProductImage> productDetailImages;
    protected List<PropertyValue> propertyValues;
    protected List<User> users;
    protected List<Order> orders;
    protected List<Review> reviews;
    protected List<OrderItem> orderItems;

    /**
     * 构造方法
     */
    public Action4Pojo() {
    }

    @Override
    public String toString() {
        return "Action4Pojo{" +
                "category=" + category +
                ", property=" + property +
                ", product=" + product +
                ", productImage=" + productImage +
                ", propertyValue=" + propertyValue +
                ", user=" + user +
                ", order=" + order +
                ", review=" + review +
                ", orderItem=" + orderItem +
                ", categorys=" + categorys +
                ", propertys=" + propertys +
                ", products=" + products +
                ", productSingleImages=" + productSingleImages +
                ", productDetailImages=" + productDetailImages +
                ", propertyValues=" + propertyValues +
                ", users=" + users +
                ", orders=" + orders +
                ", reviews=" + reviews +
                ", orderItems=" + orderItems +
                '}';
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductImage getProductImage() {
        return productImage;
    }

    public void setProductImage(ProductImage productImage) {
        this.productImage = productImage;
    }

    public PropertyValue getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(PropertyValue propertyValue) {
        this.propertyValue = propertyValue;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public List<Category> getCategorys() {
        return categorys;
    }

    public void setCategorys(List<Category> categorys) {
        this.categorys = categorys;
    }

    public List<Property> getPropertys() {
        return propertys;
    }

    public void setPropertys(List<Property> propertys) {
        this.propertys = propertys;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<ProductImage> getProductSingleImages() {
        return productSingleImages;
    }

    public void setProductSingleImages(List<ProductImage> productSingleImages) {
        this.productSingleImages = productSingleImages;
    }

    public List<ProductImage> getProductDetailImages() {
        return productDetailImages;
    }

    public void setProductDetailImages(List<ProductImage> productDetailImages) {
        this.productDetailImages = productDetailImages;
    }

    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(List<PropertyValue> propertyValues) {
        this.propertyValues = propertyValues;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
