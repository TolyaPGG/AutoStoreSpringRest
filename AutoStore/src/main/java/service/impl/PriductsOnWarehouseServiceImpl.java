package service.impl;

import model.ProductsOnWarehouses;
import model.Warehouse;
import org.eclipse.core.internal.runtime.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import repository.ProductOnWarehousesRepository;
import repository.ProductsRepository;
import repository.WarehouseRepository;
import service.ProductsOnWarehouseService;


@Service
public class PriductsOnWarehouseServiceImpl implements ProductsOnWarehouseService {

    @Autowired
    ProductOnWarehousesRepository productOnWarehousesRepository;
    @Autowired
    ProductsRepository pr;
    @Autowired
    WarehouseRepository w;

    @Override
    public void replaseProduct(long from, long to, long prod, int amount) {
        ProductsOnWarehouses powFrom = productOnWarehousesRepository.findOneByProductIdAndWarehouseId(prod, from);
        ProductsOnWarehouses powTo = productOnWarehousesRepository.findOneByProductIdAndWarehouseId(prod, to);
        if(powTo != null){
            if(powFrom.getAmount() - amount > 0){
                powFrom.setAmount(powFrom.getAmount() - amount);
                productOnWarehousesRepository.save(powFrom);
            }
            else
                productOnWarehousesRepository.delete(powFrom);
            powTo.setAmount(powTo.getAmount() + amount);
            productOnWarehousesRepository.save(powTo);
        }else {
            ProductsOnWarehouses newPow = new ProductsOnWarehouses();
            newPow.setAmount(amount);
            newPow.setProduct(pr.findOneById(prod));
            newPow.setWarehouse(w.findOneById(to));
            productOnWarehousesRepository.save(newPow);
        }
    }

    @Override
    public void updateAmount(long prodId, long warehouseId, int newAmount) {
        ProductsOnWarehouses pow = productOnWarehousesRepository.findOneByProductIdAndWarehouseId(prodId, warehouseId);
        pow.setAmount(newAmount);
        productOnWarehousesRepository.save(pow);
    }

    @Override
    public Page<ProductsOnWarehouses> findAllProductsOnWarehouse(long id, Pageable pageable) {
        return productOnWarehousesRepository.findAllByWarehouseId(id, pageable);
    }
}
