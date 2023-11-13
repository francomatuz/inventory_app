package com.company.Inventaryapp.controllers;

import com.company.Inventaryapp.models.Product;
import com.company.Inventaryapp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productService.getAllProduct();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/{id}")
    public String showProductDetails(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + id));
        model.addAttribute("product", product);
        return "product-details";
    }

    @GetMapping("/create")
    public String showCreateProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "create-product";
    }

@PostMapping("/create")
public String createProduct(@ModelAttribute Product product) {
    productService.createProduct(product);
    return "redirect:/products";
}

    @GetMapping("/{id}/update")
    public String showUpdateProductForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + id));
        model.addAttribute("product", product);
        return "update-product";
    }

    @PostMapping("/{id}/update")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product updatedProduct) {
        productService.updateProduct(id, updatedProduct);
        return "redirect:/products";
    }

    @GetMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}