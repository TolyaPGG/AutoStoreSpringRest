package service;

import model.ProductsOnWarehouses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductsOnWarehouseService {

    void replaseProduct(long from, long to, long prod, int amount);
    void updateAmount(long prodId, long warehouseId, int newAmount);
    Page<ProductsOnWarehouses> findAllProductsOnWarehouse(long id, Pageable pageable);
}
