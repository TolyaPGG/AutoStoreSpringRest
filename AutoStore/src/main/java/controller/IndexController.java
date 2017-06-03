package controller;

import model.Product;
import model.ProductsOnWarehouses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import repository.ProductOnWarehousesRepository;
import repository.ProductsRepository;
import repository.WarehouseRepository;
import service.ProductService;
import service.ProductsOnWarehouseService;

import java.util.Random;


@Controller
public class IndexController {

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/")
    public String getIndexPage(Model model){
        model.addAttribute("products", productService.getAllProducts());
        return "index/index";
    }
}
