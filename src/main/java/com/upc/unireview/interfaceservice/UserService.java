package com.upc.unireview.interfaceservice;

import com.upc.unireview.entities.User;
import java.util.List;
public interface UserService {
    public User updateUser(User user) throws Exception;
    public User register(User user) throws Exception;
    public User deleteUser(Long id) throws Exception;
    public List<User> listUser();
    public User getUser(String username);
    public User getUserbyID(Long ID);
}
