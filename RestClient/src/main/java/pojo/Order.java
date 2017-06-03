package pojo;

import java.util.List;


public class Order {
    private List<Product> products;
    private OrderStatuses status;
    private long userId;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public OrderStatuses getStatus() {
        return status;
    }

    public void setStatus(String status) {

        switch (status){
            case "ORDER_SENT":
                this.status = OrderStatuses.ORDER_SENT;
                break;
            case "ORDER_SET":
                this.status = OrderStatuses.ORDER_SET;
                break;
            case "ORDER_DONE":
                this.status = OrderStatuses.ORDER_DONE;
                break;
            case "ORDER_CHOOSE":
                this.status = OrderStatuses.ORDER_CHOOSE;
        }
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
