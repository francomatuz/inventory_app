
package com.company.Inventaryapp.controllers;

import com.company.Inventaryapp.DTO.ProductRequest;
import com.company.Inventaryapp.models.Product;
import com.company.Inventaryapp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")

public class ProductController {
    
    
    private final ProductService productService;
    
    @Autowired

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    // Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAll();
        return ResponseEntity.ok(products);
    }

    // Obtener un producto por su ID
    @GetMapping("/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    // Crear un nuevo producto
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest productRequest) {
        Product createdProduct = productService.create(productRequest.getName(), productRequest.getPrice());
        return ResponseEntity.ok(createdProduct);
    }

    // Actualizar un producto existente
    @PutMapping("/{id}")
  public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        Product updatedProduct = productService.update(id, new Product(productRequest.getName(), productRequest.getPrice()));
        return ResponseEntity.ok(updatedProduct);
    }

    // Eliminar un producto por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    
    

}
