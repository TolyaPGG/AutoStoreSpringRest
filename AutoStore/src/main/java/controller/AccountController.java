package controller;

import form.UserLoginForm;
import form.UserRegistrationForm;
import model.Order;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import service.OrderService;
import service.ProductInOrderService;
import service.UserService;
import util.EMailer;

import javax.validation.Valid;
import java.util.List;


@Controller
public class AccountController {

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Autowired
    ProductInOrderService productInOrderService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    @PreAuthorize("isAnonymous()")
    public String getRegister(Model model){
        model.addAttribute("userform", new UserRegistrationForm());
        return "security/register_form";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @PreAuthorize("isAnonymous()")
    public String register(@ModelAttribute("userform") @Valid UserRegistrationForm form, BindingResult res){
        if(!res.hasErrors()){
            User user = userService.saveNewUser(form);
            ApplicationContext context =
                    new ClassPathXmlApplicationContext("Spring-Mail.xml");
            EMailer eMailer = (EMailer) context.getBean("EMailer");

            eMailer.sendMail("semestrovkaspring@gmail.com", form.getEmail(),
                    "AutoStory",
                    "Confirm account: http://localhost:8080/acc_confirm/?id=" + user.getId() + "&token=" + user.getPassword());
            return "redirect:/";
        }else {

            return "redirect:/register";
        }
    }

    @RequestMapping(value = "/acc_confirm")
    public  String confirm(@RequestParam long id, @RequestParam String token){
        userService.confirmUser(id, token);
        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @PreAuthorize("isAnonymous()")
    public String getLoginForm(@RequestParam(value = "error", required = false) Boolean error, Model model){
        if (Boolean.TRUE.equals(error)) {
            model.addAttribute("error", error);
        }
        model.addAttribute("userform", new UserLoginForm());
        return "security/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @PreAuthorize("isAnonymous()")
    public String login(@ModelAttribute("userform") UserLoginForm form){
        return "";
    }

    @RequestMapping(value = "/myOrders", method = RequestMethod.GET)
    public String getAllOrdersPage(Model model){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Order> orders = orderService.getAllOrdersByUser(currentUser.getId());

        model.addAttribute("orders", orders);
        return "Account/myOrders";
    }
}
