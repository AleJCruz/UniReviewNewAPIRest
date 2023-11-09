package com.upc.unireview.service;

import com.upc.unireview.entities.*;
import com.upc.unireview.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RigurosityRepository rigurosityRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private UniversityRepository universityRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Transactional
    @Override
    public void run(String... args) throws Exception {
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        if (roleRepository.findAll().isEmpty()){
            roleRepository.save(new Role("Administrador"));
            roleRepository.save(new Role("Universitario"));
            roleRepository.save(new Role("PreUniversitario"));
        }
        if(rigurosityRepository.findAll().isEmpty()){
            rigurosityRepository.save(new Rigurosity("Baja"));
            rigurosityRepository.save(new Rigurosity("Media"));
            rigurosityRepository.save(new Rigurosity("Alta"));
            rigurosityRepository.save(new Rigurosity("Variable"));
            rigurosityRepository.save(new Rigurosity("Sin definir"));
        }
        if (userRepository.findAll().isEmpty()){
            Set<Role> roles = new HashSet<>(roleRepository.findAll());
            Image image = new Image(null, "user", readImageDataFromClasspath("/images/user.png"));
            imageRepository.save(image);
            String bcryptPassword = bcrypt.encode("123456");
            userRepository.save(new User(1L,"Pepe pipi", "mazinyer127@gmail.com", "wa123", bcryptPassword, "Rimac", 20, true, roles,image));
        }
        if (universityRepository.findAll().isEmpty()){
            Image image = new Image(null, "university", readImageDataFromClasspath("/images/pucp.jpg"));
            imageRepository.save(image);
            universityRepository.save(new com.upc.unireview.entities.University(null, "Pontificia Universidad Católica del Perú", "San Miguel", 1945,true,false,true,0,"https://www.upc.edu.pe/",image));
            Image image2 = new Image(null, "university", readImageDataFromClasspath("/images/upc-mo.jpg"));
            imageRepository.save(image2);
            universityRepository.save(new com.upc.unireview.entities.University(null, "Universidad Peruana de Ciencias Aplicadas", "San Borja", 2450,true,true,true,0,"https://www.upc.edu.pe/",image2));
            Image image3 = new Image(null, "university", readImageDataFromClasspath("/images/upc-si.jpg"));
            imageRepository.save(image3);
            universityRepository.save(new com.upc.unireview.entities.University(null, "Universidad Peruana de Ciencias Aplicadas", "San Isidro", 2450,true,true,false,0,"https://www.upc.edu.pe/",image3));
            Image image4 = new Image(null, "university", readImageDataFromClasspath("/images/upc-vi.jpg"));
            imageRepository.save(image4);
            universityRepository.save(new com.upc.unireview.entities.University(null, "Universidad Peruana de Ciencias Aplicadas", "Chorrillos", 2450,true,true,true,0,"https://www.upc.edu.pe/",image4));
            Image image5 = new Image(null, "university", readImageDataFromClasspath("/images/upc-sm.jpg"));
            imageRepository.save(image5);
            universityRepository.save(new com.upc.unireview.entities.University(null, "Universidad Peruana de Ciencias Aplicadas", "San Miguel", 2450,true,true,true,0,"https://www.upc.edu.pe/",image5));
        }
        if (teacherRepository.findAll().isEmpty()) {
            Image image = new Image(null, "teacher", readImageDataFromClasspath("/images/teacher2.jpg"));
            imageRepository.save(image);
            teacherRepository.save(new com.upc.unireview.entities.Teacher(null, "Esther Aliaga Cerna", 0,"Profesora un poco bajita", new ArrayList<Course>(), new Rigurosity(5L,"Sin Definir"),image));
        }
        if (courseRepository.findAll().isEmpty()) {
            courseRepository.save(new Course(null, "Arquitectura Empresarial"));
            courseRepository.save(new Course(null, "Matemática"));
            courseRepository.save(new Course(null, "Programación Orientada a Objetos"));
            // Obtén el profesor dentro de una transacción
            Teacher e = teacherRepository.getTeacherById(1L);

            // Inicializa la colección coursearray dentro de la transacción
            e.getCoursearray().add(courseRepository.findCourseById(1L));
            updateTeacher(e);
        }


    }
    private byte[] readImageDataFromClasspath(String classpathResource) throws IOException {
        InputStream in = new ClassPathResource(classpathResource).getInputStream();
        return StreamUtils.copyToByteArray(in);
    }
    public void updateTeacher(Teacher teacher) throws Exception{
        //controlar que no se repita el email al actualizar
        teacherRepository.findById(teacher.getId()).
                orElseThrow(()->new Exception("No se encontró la entidad"));
        teacherRepository.save(teacher);
    }
}