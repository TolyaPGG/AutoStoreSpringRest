package service;

import form.AddProductForm;
import model.Product;

import java.util.List;


public interface ProductService {

    long saveNewProduct(AddProductForm form);
    List<Product> getAllProducts();
    Product findOneById(long id);
    int getAllProductsAmountByProdId(long id);
}
