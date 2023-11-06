package com.upc.unireview.dto;
import com.upc.unireview.entities.Image;
import com.upc.unireview.entities.Role;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String type;
    private String username;
    private String password;
    private String district;
    private int age;
    private boolean enabled;
    private Set<Role> roles = new HashSet<>();
    private ImageDTO image;
}
