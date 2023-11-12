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
            universityRepository.save(new com.upc.unireview.entities.University(null, "Pontificia Universidad Católica del Perú", "San Miguel", 1945,true,false,true,0,"https://www.pucp.edu.pe/admision/admision-pregrado/modalidades-de-admision/",image));
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
            Image image6 = new Image(null, "university", readImageDataFromClasspath("/images/UAP.jpg"));
            imageRepository.save(image6);
            universityRepository.save(new com.upc.unireview.entities.University(null, "Universidad Autonóma del Perú", "Villa El Salvador", 2450,true,true,true,0,"https://www.autonoma.pe/evaluacion-preferente/?utm_source=pg_goog%20lm_afin4&utm_medium=cpc&utm_campaign=search%20lm_afines4&gclid=EAIaIQobChMIlq6P7qO6ggMVG1hIAB1bpwh9EAAYAiAAEgJfSPD_BwE",image6));
            Image image7 = new Image(null, "university", readImageDataFromClasspath("/images/UARM.jpg"));
            imageRepository.save(image7);
            universityRepository.save(new com.upc.unireview.entities.University(null, "Universidad Antonio Ruiz de Montoya", "Pueblo Libre", 2450,true,false,true,0,"https://www.educaedu.com.pe/centros/universidad-antonio-ruiz-de-montoya-uni1788",image7));
            Image image8 = new Image(null, "university", readImageDataFromClasspath("/images/ucs.jpg"));
            imageRepository.save(image8);
            universityRepository.save(new com.upc.unireview.entities.University(null, "Universidad Cientifica del Sur", "Miraflores", 2450,true,false,true,0,"https://www.cientifica.edu.pe/admision/calendario-admision",image8));
            Image image9 = new Image(null, "university", readImageDataFromClasspath("/images/uni.jpg"));
            imageRepository.save(image9);
            universityRepository.save(new com.upc.unireview.entities.University(null, "Universidad Nacional de Ingienería", "Independencia", 2450,true,false,true,0,"https://admision.uni.edu.pe/",image9));
            Image image10 = new Image(null, "university", readImageDataFromClasspath("/images/unmsm.png"));
            imageRepository.save(image10);
            universityRepository.save(new com.upc.unireview.entities.University(null, "Universidad Nacional Mayor de San Marcos", "Lima", 2450,true,false,true,0,"https://www.unmsm.edu.pe/",image10));
            Image image11 = new Image(null, "university", readImageDataFromClasspath("/images/up.jpg"));
            imageRepository.save(image11);
            universityRepository.save(new com.upc.unireview.entities.University(null, "Universidad del Pacifico", "Jesús María", 2450,true,false,true,0,"https://www.up.edu.pe/admision",image11));
            Image image12 = new Image(null, "university", readImageDataFromClasspath("/images/upch.jpg"));
            imageRepository.save(image12);
            universityRepository.save(new com.upc.unireview.entities.University(null, "Universidad Ricardo Palma", "Jesús María", 2450,true,false,true,0,"https://www.urp.edu.pe/",image12));


        }
        if (teacherRepository.findAll().isEmpty()) {
            Image image = new Image(null, "teacher", readImageDataFromClasspath("/images/teacher2.jpg"));
            imageRepository.save(image);
            teacherRepository.save(new com.upc.unireview.entities.Teacher(null, "Esther Aliaga Cerna", 0,"Profesora que enseña generalmente en San Miguel", new ArrayList<Course>(), new Rigurosity(5L,"Sin Definir"),image));
        }
        if (courseRepository.findAll().isEmpty()) {
            courseRepository.save(new Course(null, "Arquitectura Empresarial"));
            courseRepository.save(new Course(null, "Matemática"));
            courseRepository.save(new Course(null, "Programación Orientada a Objetos"));
            courseRepository.save(new Course(null, "IHC"));
            courseRepository.save(new Course(null, "Arquitectura de negocios"));
            courseRepository.save(new Course(null, "Fundamentos de la programacion"));
            courseRepository.save(new Course(null, "Taller de creatividad"));
            courseRepository.save(new Course(null, "Psicologia"));
            courseRepository.save(new Course(null, "Calculo I"));
            courseRepository.save(new Course(null, "Calculo II"));
            courseRepository.save(new Course(null, "Comprension y produccion de lenguaje I"));
            courseRepository.save(new Course(null, "Comprension y produccion de lenguaje II"));
            courseRepository.save(new Course(null, "Redes y comunicaciones de datos"));
            courseRepository.save(new Course(null, "Estadistica Aplicada"));



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