package model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import model.enums.OrderStatus;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Set;




@Entity
@Table(name = "orders")
@Proxy(lazy = false)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public OrderStatus getStatus() {
        return status;
    }

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderId")
    public Set<ProductInOrder> getProductInOrders() {
        return productInOrders;
    }

    public void setProductInOrders(Set<ProductInOrder> productInOrders) {
        this.productInOrders = productInOrders;
    }

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderId")
    private Set<ProductInOrder> productInOrders;

    @ManyToOne
    @JoinColumn(name = "userId")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)

    private OrderStatus status;
}
