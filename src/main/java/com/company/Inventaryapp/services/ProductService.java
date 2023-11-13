package com.company.Inventaryapp.services;

import com.company.Inventaryapp.repositories.ProductRepository;

import com.company.Inventaryapp.models.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Creamos el producto
    public Product create(String name, Double price) {
        // Validar los datos de entrada
        validateName(name);
        validatePrice(price);

        // Crear un nuevo objeto Product
        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setPrice(price);

        // Guardar el nuevo producto en la base de datos
        productRepository.save(newProduct);
        return newProduct;
    }

    // Obtiene todos los productos
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    // Obtiene un producto por ID
    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    // Obtiene todos los productos por su nombre
    public List<Product> getByName(String name) {
        return productRepository.findProductsByName(name);
    }

    // Actualiza un producto
    public Product update(Long id, Product updatedProduct) {
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

    // Elimina un producto
    public void delete(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            productRepository.deleteById(id);
        } else {
            // Manejo de error: Producto no encontrado
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
    }

    // Métodos de validación
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