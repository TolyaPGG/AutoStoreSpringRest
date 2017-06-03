package service.impl;

import model.Product;
import model.ProductInOrder;
import model.ProductsOnWarehouses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ProductInOrderRepository;
import repository.ProductOnWarehousesRepository;
import repository.ProductsRepository;
import service.ProductInOrderService;

import java.util.ArrayList;
import java.util.List;



@Service
public class ProductInOrderServiceImpl implements ProductInOrderService {

    @Autowired
    ProductInOrderRepository productInOrderRepository;

    @Autowired
    ProductOnWarehousesRepository productOnWarehousesRepository;

    @Autowired
    ProductsRepository productsRepository;

    @Override
    public void saveNewProductInOrder(ProductInOrder productInOrder) {

        ProductsOnWarehouses p = productOnWarehousesRepository.findOneByProductId(productInOrder.getProduct().getId());
        p.setAmount(p.getAmount() - productInOrder.getAmount());
        productOnWarehousesRepository.save(p);
        productInOrderRepository.save(productInOrder);
    }

    @Override
    public List<ProductInOrder> findAllProductsInOrder(long orderId) {
        List<ProductInOrder> productInOrders = productInOrderRepository.findAllByOrderId(orderId);
        List<Product> products = new ArrayList<Product>();
        for(ProductInOrder pio : productInOrders){
            products.add(pio.getProduct());
        }
        return productInOrders;
    }
}
