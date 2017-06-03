package service;

import model.Product;
import model.ProductInOrder;

import java.util.List;

public interface ProductInOrderService {
    void saveNewProductInOrder(ProductInOrder productInOrder);
    List<ProductInOrder> findAllProductsInOrder(long orderId);
}
