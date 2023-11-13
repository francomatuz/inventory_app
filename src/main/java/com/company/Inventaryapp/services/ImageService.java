
package com.company.Inventaryapp.services;

import com.company.Inventaryapp.exceptions.MiException;
import com.company.Inventaryapp.repositories.ImageRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.company.Inventaryapp.models.Image;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;
    
    public Image save(MultipartFile archivo) throws MiException{
        if (archivo != null) {
            try {
                
                Image imagen = new Image();
                
                imagen.setMime(archivo.getContentType());
                
                imagen.setNombre(archivo.getName());
                
                imagen.setContenido(archivo.getBytes());
                
                return imageRepository.save(imagen);
                
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
    
    public Image update(MultipartFile archivo, String idImagen) throws MiException{
         if (archivo != null) {
            try {
                
                Image image = new Image();
                
                if (idImagen != null) {
                    Optional<Image> respuesta = imageRepository.findById(idImagen);
                    
                    if (respuesta.isPresent()) {
                        image = respuesta.get();
                    }
                }
                
                image.setMime(archivo.getContentType());
                
                image.setNombre(archivo.getName());
                
                image.setContenido(archivo.getBytes());
                
                return imageRepository.save(image);
                
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
        
    }
    
    @Transactional(readOnly = true)
	public List<Image> listarTodos() {
		return imageRepository.findAll();
	}

}
