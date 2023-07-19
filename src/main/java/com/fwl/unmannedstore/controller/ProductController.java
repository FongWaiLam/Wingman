package com.fwl.unmannedstore.controller;

import com.fwl.unmannedstore.model.Product;
import com.fwl.unmannedstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/add_product")
    public String addProduct() {
        return "add_product";
    }

    @PostMapping("/save_product")
    public String addProduct(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/products";
    }

    @GetMapping("/products")
    public ModelAndView getProducts() {
        List<Product> allProducts = productService.getAllProducts();
        return new ModelAndView("products", "allProducts", allProducts);
    }

    @GetMapping("/product/{prodId}")
    public ModelAndView getProduct(int prodId) {
        Product product = productService.getProductById(prodId);
        return new ModelAndView("product", "product", product);
    }

    @RequestMapping("/updateProduct/{prodId}")
    public String editBookById(@PathVariable int prodId, Model model) {
        Product product = productService.getProductById(prodId);
        model.addAttribute("product", product);
        return"update_product";
    }

    @GetMapping("/deactivate_Product/{prod_id}")
    public String deleteBookById(@PathVariable int prod_id) {
        productService.deactivateById(prod_id);
        return "redirect:/products_inventory";
    }

}
