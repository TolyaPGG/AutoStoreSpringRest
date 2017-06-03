package service.impl;

import form.AddProductForm;
import model.Product;
import model.ProductsOnWarehouses;
import model.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import repository.ProductOnWarehousesRepository;
import repository.ProductsRepository;
import repository.WarehouseRepository;
import service.ProductService;

import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    ProductOnWarehousesRepository productOnWarehousesRepository;

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public long saveNewProduct(AddProductForm form) {
        Product product = new Product();
        product.setName(form.getProductName());
        product.setDescription(form.getDescription());
        product.setPrice(form.getCost());

        Product savedProduct = productsRepository.save(product);
        Warehouse warehouse = warehouseRepository.findOneById(form.getWarehouse());

        ProductsOnWarehouses productsOnWarehouses = new ProductsOnWarehouses();
        productsOnWarehouses.setProduct(savedProduct);
        productsOnWarehouses.setWarehouse(warehouse);
        productsOnWarehouses.setAmount(form.getAmount());
//        warehouse.getProductsOnWarehouses().add(productsOnWarehouses);
//        product.getProductsOnWarehouses().add(productsOnWarehouses);
        productOnWarehousesRepository.save(productsOnWarehouses);
        return 0;
    }

    @Override
    public List<Product> getAllProducts() {
        return productsRepository.findAll();
    }

    @Override
    public Product findOneById(long id) {
        return productsRepository.findOneById(id);
    }

    @Override
    public int getAllProductsAmountByProdId(long id) {
        Product p = productsRepository.getOne(id);
        int amount = 0;
        for (ProductsOnWarehouses piv: p.getProductsOnWarehouses()) {
            amount += piv.getAmount();
        }
        return amount;
    }
}
