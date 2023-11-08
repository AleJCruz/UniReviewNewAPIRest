package com.upc.unireview.controller;

import com.upc.unireview.dto.ImageDTO;
import com.upc.unireview.dto.UserDTO;
import com.upc.unireview.entities.Image;
import com.upc.unireview.entities.User;
import com.upc.unireview.interfaceservice.ImageService;
import com.upc.unireview.interfaceservice.UserService;
import com.upc.unireview.security.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private ImageService imageService;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/user/register")
    public <Mono>ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO){
        User user;
        UserDTO dto;
        try{
            user = convertToEntity(userDTO);
            String bcryptPassword = bcrypt.encode(user.getPassword());
            user.setPassword(bcryptPassword);
            byte[] imageData = readImageDataFromClasspath("/images/user.png");
            Image image = new Image(null, "imagen perfil", imageData);
            image = imageService.uploadImage(image);

            user.setImage(image);

            userService.register(user);

            dto = convertToDTO(user);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha podido registrar");
        }
        return new ResponseEntity<UserDTO>(dto, HttpStatus.OK);
    }
    @GetMapping("/user")
    public <Flux>ResponseEntity<List<UserDTO>> list(){
        List<User> list;
        List<UserDTO> listDTO=null;
        try {
            list = userService.listUser();
            listDTO = convertToListDTO(list);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista no disponible");
        }
        return new ResponseEntity<>(listDTO,HttpStatus.OK);
    }
    @GetMapping("/user/me")
    public <Mono>ResponseEntity<UserDTO> getAuthenticatedUser(HttpServletRequest request) {
        // Extrae el token del encabezado de la solicitud.
        String tokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        // Si el encabezado del token está presente y tiene el prefijo "Bearer "
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            jwtToken = tokenHeader.substring(7);
            try {
                // Extrae el nombre de usuario (o ID de usuario) del token.
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "JWT Token has expired");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "JWT Token does not begin with Bearer String");
        }

        // Con el nombre de usuario, consulta la base de datos o el repositorio de usuarios para obtener los detalles del usuario.
        User user = userService.getUser(username);


        // Convertir la entidad de usuario a un DTO para ocultar la información que no se debería exponer.
        UserDTO userDto = convertToDTO(user);

        // Retorna el DTO.
        return ResponseEntity.ok(userDto);
    }
    @PutMapping("/user")
    public <Mono>ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO){
        User user;
        UserDTO dto;
        try{
            user = convertToEntity(userDTO);
            user = userService.updateUser(user);
            dto = convertToDTO(user);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se puede Actualizar");
        }
        return new ResponseEntity<UserDTO>(dto,HttpStatus.OK);
    }

    //editar por id de usuario la imagen
    @PutMapping("/user/{id}")
    public <Mono>ResponseEntity<ImageDTO> updateUserImage(@PathVariable(value="id") Long id,@RequestBody ImageDTO imageDTO){
        Image image;
        ImageDTO dto;
        try{
            image = convertImageToEntity(imageDTO);
            image = imageService.updateImage(image);
            dto = convertImageToDTO(image);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se puede Actualizar");
        }
        return new ResponseEntity<ImageDTO>(dto,HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public <Mono>ResponseEntity<UserDTO> delete(@PathVariable(value="id") Long id){
        User user;
        UserDTO dto;
        try {
            user = userService.deleteUser(id);
            dto = convertToDTO(user);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontro el usuario");
        }
        return new ResponseEntity<UserDTO>(dto, HttpStatus.OK);
    }

    private User convertToEntity(UserDTO userDTO){
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userDTO, User.class);
        return user;
    }
    @Transactional
    public UserDTO convertToDTO(User user){
        ModelMapper modelMapper =new ModelMapper();
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        if (user.getImage() != null) { // Null check to avoid NullPointerException
            ImageDTO imageDTO = modelMapper.map(user.getImage(), ImageDTO.class);
            userDTO.setImage(imageDTO);
        }

        return userDTO;
    }
    public Image convertImageToEntity(ImageDTO imageDTO){
        ModelMapper modelMapper = new ModelMapper();
        Image image = modelMapper.map(imageDTO,Image.class);
        return image;
    }
    public ImageDTO convertImageToDTO(Image image){
        ModelMapper modelMapper = new ModelMapper();
        ImageDTO imageDTO = modelMapper.map(image,ImageDTO.class);
        return imageDTO;
    }


    private List<UserDTO> convertToListDTO(List<User> list){
        return list.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    private byte[] readImageDataFromClasspath(String classpathResource) throws IOException {
        InputStream in = new ClassPathResource(classpathResource).getInputStream();
        return StreamUtils.copyToByteArray(in);
    }

}