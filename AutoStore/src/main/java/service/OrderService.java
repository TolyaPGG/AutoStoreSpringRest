package service;

import model.Order;
import model.enums.OrderStatus;

import java.util.List;


public interface OrderService {
    Order saveNewOrder(Order order);
    List<Order> getAllOrdersByUser(long userid);
    List<Order> getAllOrders();
    Order changeStatus(long id, OrderStatus status);
}
