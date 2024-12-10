package sg.nus.edu.iss.vttp5a_ssf_day19_task2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sg.nus.edu.iss.vttp5a_ssf_day19_task2.model.Product;
import sg.nus.edu.iss.vttp5a_ssf_day19_task2.service.ProductService;
import sg.nus.edu.iss.vttp5a_ssf_day19_task2.util.Constants;

@Controller
@RequestMapping("/products")
public class ProductsListController {
    @Autowired
    ProductService productService;
    
    @GetMapping()
    public ModelAndView getProductsList(){
        ModelAndView mav = new ModelAndView();
        List<Product> products = productService.getProductListFromRedis(Constants.REDISKEY);
        mav.addObject("products", products);
        mav.setViewName("productlist");
        return mav;
    }

    @GetMapping("/buy/{product-id}")
    public ModelAndView buyProduct(@PathVariable("product-id") String productID){
        ModelAndView mav = new ModelAndView();
        Product productToBuy = productService.getProductFromRedis(Constants.REDISKEY, productID);
        if(productToBuy.canProductBeBought()){
            productToBuy.setBuy(productToBuy.getBuy() + 1);
            productService.submitEntryToRedis(Constants.REDISKEY, productToBuy);
            mav.setViewName("redirect:/products/" + productID);
        } else {
            mav.setViewName("producterror");
        }
        return mav;
    }

    @GetMapping("/{product-id}")
    public ModelAndView displayBoughtProduct(@PathVariable("product-id") String productID){
        ModelAndView mav = new ModelAndView();
        Product boughtProduct = productService.getProductFromRedis(Constants.REDISKEY, productID);
        mav.addObject("product", boughtProduct);
        mav.setViewName("product");
        return mav;
    }

}
