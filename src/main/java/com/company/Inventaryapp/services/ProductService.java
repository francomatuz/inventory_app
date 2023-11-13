package com.company.Inventaryapp.services;

import com.company.Inventaryapp.enums.ProductCategory;
import com.company.Inventaryapp.models.Product;
import com.company.Inventaryapp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

public Product createProduct(Product newProduct) {
    validateName(newProduct.getName());
    validatePrice(newProduct.getPrice());

    productRepository.save(newProduct);
    return newProduct;
}

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getProductByName(String name) {
        return productRepository.findProductsByName(name);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Optional<Product> existingProductOptional = productRepository.findById(id);

        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setCategory(updatedProduct.getCategory());
            // Puedes actualizar más campos según tus necesidades
            return productRepository.save(existingProduct);
        } else {
            // Manejo de error: Producto no encontrado
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
    }

    public void deleteProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            productRepository.deleteById(id);
        } else {
            // Manejo de error: Producto no encontrado
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
    }

    private void validatePrice(Double price) {
        if (price == null || price <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
    }
}
