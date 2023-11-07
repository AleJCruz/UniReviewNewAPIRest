package com.upc.unireview.service;
import com.upc.unireview.entities.User;
import com.upc.unireview.interfaceservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.upc.unireview.repository.UserRepository;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    //poner excepción register
    public User register(User user) {       //evitar que se repita el email, es unico, error
        return userRepository.save(user);
    }

    public User updateUser(User user) throws Exception{
        //controlar que no se repita el email al actualizar
        userRepository.findById(user.getId()).
                orElseThrow(()->new Exception("No se encontró la entidad"));
        return userRepository.save(user);
    }

    public User deleteUser(Long id) throws Exception{
        User user = userRepository.findById(id).orElseThrow(()->new Exception("No se encontro el usuario"));
        userRepository.delete(user);
        return user;
    }
    //Poner excepción listUser
    public List<User> listUser(){
        return userRepository.findAll();
    }

    public User getUser(String username){
        User user = userRepository.findByUsername(username);
        return user;
    }

    public User getUserbyID(Long ID){
        return userRepository.getUserById(ID);
    }
}
