package controller;

import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.OrderService;
import service.ProductsOnWarehouseService;
import service.UserService;
import service.WarehouseService;

import java.util.ArrayList;
import java.util.List;

import static model.enums.OrderStatus.ORDER_DONE;
import static model.enums.OrderStatus.ORDER_SENT;


@RestController
//@RequestMapping(value = "/rest/")
public class ShopRestController {

//    @Autowired
//    UserService userService;
    @Autowired
    OrderService orderService;

    @Autowired
    WarehouseService warehouseService;

    @Autowired
    ProductsOnWarehouseService productsOnWarehouseService;

    @RequestMapping(value = "/rest/warehouses", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Warehouse> getAllWarehouses(){
        return warehouseService.getAllWarehouses();
    }

    @RequestMapping(value = "/rest/warehouse/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getProductsOnWarehouse(@PathVariable("id") Long id){
        Warehouse warehouse = warehouseService.getOneById(id);
        List<Product> products = new ArrayList<>();
        for(ProductsOnWarehouses pow : warehouse.getProductsOnWarehouses()){
            products.add(pow.getProduct());
        }
        return products;
    }

    @RequestMapping(value = "/rest/warehousep/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ProductsOnWarehouses> getProductsOnWarehousePageble(@PathVariable("id") Long id, Pageable pageable){
        Warehouse warehouse = warehouseService.getOneById(id);
        List<Product> products = new ArrayList<>();
        Page<ProductsOnWarehouses> p = productsOnWarehouseService.findAllProductsOnWarehouse(id, pageable);
        for(ProductsOnWarehouses pow : warehouse.getProductsOnWarehouses()){
            products.add(pow.getProduct());
        }
        return p;
    }

    @RequestMapping(value = "/rest/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateAmount( @RequestParam long warehouse, @RequestParam long prod, @RequestParam int amount){
        productsOnWarehouseService.updateAmount(prod, warehouse, amount);
        return "success";
    }

    @RequestMapping(value = "/rest/moveProd", produces = MediaType.APPLICATION_JSON_VALUE)
    public String moveProduct( @RequestParam long from, @RequestParam long to, @RequestParam long prod, @RequestParam int amount){
        productsOnWarehouseService.replaseProduct(from, to, prod, amount);
        return "success";
    }

    @RequestMapping(value = "/rest/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> getAllOrders(){
        List<Order> orders = orderService.getAllOrders();
        return orderService.getAllOrders();
    }

    @RequestMapping(value = "/rest/changeStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    public void changeOrderStatus(@RequestParam long id, @RequestParam String status){
        switch (status) {
            case "ORDER_SENT":
                orderService.changeStatus(id, ORDER_SENT);
                break;
            case "ORDER_DONE":
                orderService.changeStatus(id, ORDER_DONE);
                break;
        }
    }

    @RequestMapping(value = "/rest/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public String checkLogin(){
        return "success";
    }
}
