
package com.company.Inventaryapp.repositories;
import com.company.Inventaryapp.models.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ProductRepository extends JpaRepository<Product, Long>{
    // Utiliza una consulta JPQL para encontrar productos con un precio específico
    @Query("SELECT p FROM Product p WHERE p.price = :price")
    List<Product> findProductsByPrice(@Param("price") double price);
    
    // Otra opción para encontrar productos por nombre (usando JPQL)
    @Query("SELECT p FROM Product p WHERE p.name = :name")
    List<Product> findProductsByName(@Param("name") String name);
    
     // Encuentra un producto por su ID usando JPQL
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Optional<Product> findProductById(@Param("id") Long id);

}
