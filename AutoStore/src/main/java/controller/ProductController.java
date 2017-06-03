package controller;

import form.OrderConfirmationForm;
import form.ProductForm;
import model.*;
import model.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pojo.Confirmation;
import pojo.ProductCounter;
import service.OrderService;
import service.ProductInOrderService;
import service.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;



@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @Autowired
    ProductInOrderService productInOrderService;

    @RequestMapping(value = "/product")
    public String getProductPage(Model model, @RequestParam long id){
        Product currentProduct = new Product();
        int amount = 0;
        for(ProductsOnWarehouses pow : currentProduct.getProductsOnWarehouses()){
            if(pow.getProduct().getId() == currentProduct.getId())
                amount += pow.getAmount();
        }
        currentProduct = productService.findOneById(id);
        ProductForm form = new ProductForm(currentProduct.getName(), currentProduct.getPrice(),
                amount, currentProduct.getDescription(), currentProduct.getId());
        model.addAttribute("product", form);
        return "product/product";
    }


    @RequestMapping(value = "/product/add", method = RequestMethod.POST)
    @ResponseBody
    public String addProduct(@ModelAttribute("product")ProductForm form, BindingResult res, Model model, HttpServletRequest request){

        if(form.getAmount() > productService.getAllProductsAmountByProdId(form.getProdId())){
            return "Not enought produst on warehouse";
        }
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null){
            return "redirect:/login";
        }
        List<ProductCounter> cart;
        if(request.getSession().getAttribute("shoppingCart") == null){
            cart = new ArrayList<>();
        }else {
            cart = (List<ProductCounter>)request.getSession().getAttribute("shoppingCart");
        }

        cart.add(new ProductCounter(form.getAmount(), form.getProdId()));
        request.getSession().setAttribute("shoppingCart", cart);

        return "In your cart " + cart.size() + " products  <a href=\"/confirm_order\">Buy</a>";
    }

    @RequestMapping(value = "/confirm_order", method = RequestMethod.GET)
    public String getOrderConfirmationPage(Model model, HttpServletRequest request){
        OrderConfirmationForm form = new OrderConfirmationForm();
        ArrayList<ProductCounter> cart = (ArrayList<ProductCounter>)request.getSession().getAttribute("shoppingCart");
        ArrayList<Confirmation> confirmations = new ArrayList<>();
        for (ProductCounter pc : cart){
            Confirmation c = new Confirmation();
            c.setAmount(pc.getAmount());
            Product currentProduct = productService.findOneById(pc.getProdId());
            c.setCost(currentProduct.getPrice());
            c.setName(currentProduct.getName());
            c.setId(currentProduct.getId());
            confirmations.add(c);
        }
        form.setConfirmations(confirmations);
        model.addAttribute("c", confirmations);
        return "product/orderConfirm";
    }

    @RequestMapping(value = "/remove_product", method = RequestMethod.POST)
    public String removeProduct(@RequestParam long id, HttpServletRequest request){
        ArrayList<ProductCounter> cart = (ArrayList<ProductCounter>)request.getSession().getAttribute("shoppingCart");
        int deletedNum = 0;
        for(ProductCounter pc : cart){
            if(pc.getProdId() == id){
                break;
            }
            deletedNum++;
        }
        cart.remove(deletedNum);
        request.getSession().setAttribute("shoppingCart", cart);
        return "redirect:/confirm_order";
    }

    @RequestMapping(value = "/order_confirmed")
    public String confirm(HttpServletRequest request){
        ArrayList<ProductCounter> cart = (ArrayList<ProductCounter>)request.getSession().getAttribute("shoppingCart");
        User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Order order = new Order();
        order.setUser(currentUser);
        order.setStatus(OrderStatus.ORDER_DONE);
        order = orderService.saveNewOrder(order);
        for (ProductCounter pc : cart){
            ProductInOrder productInOrder = new ProductInOrder();
            productInOrder.setAmount(pc.getAmount());
            productInOrder.setOrder(order);
            productInOrder.setProduct(productService.findOneById(pc.getProdId()));
            productInOrderService.saveNewProductInOrder(productInOrder);
        }
        request.getSession().setAttribute("shoppingCart", null);
        return "redirect:/";
    }

    @RequestMapping(value = "/cart/change/"/*, method = RequestMethod.POST*/)
    @ResponseBody
    public String changeAmount(@RequestParam String action, @RequestParam long id, HttpServletRequest request){
        ArrayList<ProductCounter> cart = (ArrayList<ProductCounter>)request.getSession().getAttribute("shoppingCart");
        int maxAmount = productService.getAllProductsAmountByProdId(id);
        int count = 0;
        for(ProductCounter pc : cart){
            if(pc.getProdId() == id){
                count = pc.getAmount();
                if(action.equals("+") && count + 1 <= maxAmount){
                    count +=1;
                    pc.setAmount(count);
                }else {
                    if(action.equals("-") && pc.getAmount() > 1){
                        count -= 1;
                        pc.setAmount(count);
                    }
                }
            }
        }
        request.getSession().setAttribute("shoppingCart", cart);
        return Integer.toString(count);
    }


}
