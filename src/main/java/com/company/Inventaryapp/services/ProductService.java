package com.company.Inventaryapp.services;

import com.company.Inventaryapp.repositories.ProductRepository;
import com.company.inventaryapp.models.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
//Obtiene todos los productos

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
//Obtiene todos los productos por id

    public Optional<Product> getProductById(Long id) {
        return productRepository.findProductById(id);
    }
//Obtiene todos los productos por su nombre

    public List<Product> getProductsByName(String name) {
        return productRepository.findProductsByName(name);
    }
//Guarda un nuevo producto

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
//Actualiza un producto    

    public Product updateProduct(Long id, Product updatedProduct) {
        Optional<Product> existingProductOptional = productRepository.findById(id);

        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setPrice(updatedProduct.getPrice());
            // Puedes actualizar más campos según tus necesidades
            return productRepository.save(existingProduct);
        } else {
            // Manejo de error: Producto no encontrado
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
    }
//Elimina un producto     

    public void deleteProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            productRepository.deleteById(id);
        } else {
            // Manejo de error: Producto no encontrado
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
    }
//Elimina un producto por su nombre     

    public void deleteProductByName(String name) {
        List<Product> productsToDelete = productRepository.findProductsByName(name);

        if (!productsToDelete.isEmpty()) {
            productRepository.deleteAll(productsToDelete);
        } else {
            // Manejo de error: No se encontraron productos con el nombre dado
            throw new IllegalArgumentException("No products found with name: " + name);
        }
    }
}
