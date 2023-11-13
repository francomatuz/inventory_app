
package com.company.Inventaryapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.company.Inventaryapp.models.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, String>{

}
