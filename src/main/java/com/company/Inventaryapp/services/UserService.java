package com.company.Inventaryapp.services;

import com.company.Inventaryapp.enums.Rol;
import com.company.Inventaryapp.exceptions.MiException;
import com.company.Inventaryapp.models.Image;
import com.company.Inventaryapp.repositories.UserRepository;
import com.company.Inventaryapp.models.User;
import static com.sun.jmx.snmp.SnmpStatusException.readOnly;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageService imageService;

    @Transactional
    public void register(MultipartFile file, String name, String email, String password, String password2) throws MiException {

        validar(name, email, password, password2);

        User user = new User();

        user.setName(name);
        user.setEmail(email);

        user.setPassword(new BCryptPasswordEncoder().encode(password));

        user.setRol(Rol.USER);

        Image image = imageService.save(file);

        user.setImage(image);

        userRepository.save(user);
    }

    @Transactional
    public void update(MultipartFile file, String idUser, String name, String email, String password, String password2) throws MiException {

        validar(name, email, password, password2);

        Optional<User> respuesta = userRepository.findById(idUser);
        if (respuesta.isPresent()) {

            User user = respuesta.get();
            user.setName(name);
            user.setEmail(email);

            user.setPassword(new BCryptPasswordEncoder().encode(password));

            user.setRol(Rol.USER);

            String idImage = null;

            if (user.getImage() != null) {
                idImage = user.getImage().getId();
            }

            Image image = imageService.update(file, idImage);

            user.setImage(image);

            userRepository.save(user);
        }

    }

    public User getOne(String id) {
        return userRepository.getOne(id);
    }

    @Transactional(readOnly = true)
    public List<User> ListUsers() {

        List<User> users = new ArrayList();

        users = userRepository.findAll();

        return users;
    }

    @Transactional
    public void changeRol(String id) {
        Optional<User> respuesta = userRepository.findById(id);

        if (respuesta.isPresent()) {

            User user = respuesta.get();

            if (user.getRol().equals(Rol.USER)) {

                user.setRol(Rol.ADMIN);

            } else if (user.getRol().equals(Rol.ADMIN)) {
                user.setRol(Rol.USER);
            }
        }
    }

    private void validar(String name, String email, String password, String password2) throws MiException {

        if (name.isEmpty() || name == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacío");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("el email no puede ser nulo o estar vacio");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }

        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User usuario = userRepository.findByEmail(email);

        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", usuario);

            return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getPassword(),
                    permisos);
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
        }

    }
}
