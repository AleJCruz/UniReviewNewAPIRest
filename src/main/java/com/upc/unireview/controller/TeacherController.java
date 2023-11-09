package com.upc.unireview.controller;

import com.upc.unireview.dto.CourseDTO;
import com.upc.unireview.dto.ImageDTO;
import com.upc.unireview.dto.RigurosityDTO;
import com.upc.unireview.dto.TeacherDTO;
import com.upc.unireview.entities.Image;
import com.upc.unireview.entities.Teacher;
import com.upc.unireview.interfaceservice.ImageService;
import com.upc.unireview.interfaceservice.TeacherService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    Logger logger = LoggerFactory.getLogger(TeacherController.class);
    @Autowired
    private ImageService imageService;
    @PostMapping("/teacher/{imageId}")
    @Transactional
    public <Mono>ResponseEntity<TeacherDTO> register(@RequestBody TeacherDTO teacherDTO, @PathVariable(value="imageId") Long imageId){
        Teacher teacher;
        TeacherDTO dto;
        try{
            teacher = convertToEntity(teacherDTO);
            Image image = imageService.getImage(imageId);
            teacher.setImage(image);
            System.out.println("Id de la iamgen:" + teacher.getImage().getId());
            teacher = teacherService.register(teacher);
            dto = convertToDTO(teacher);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha podido registrar");
        }
        return new ResponseEntity<TeacherDTO>(dto, HttpStatus.OK);
    }
//    @PostMapping("/teacher")
//    public <Mono>ResponseEntity<TeacherDTO> register(@RequestBody TeacherDTO teacherDTO){
//        Teacher teacher;
//        TeacherDTO dto;
//        try{
//            teacher = convertToEntity(teacherDTO);
//            byte[] imageData = readImageDataFromClasspath("/images/teacher.png");
//            Image image = new Image(null, "imagen profesor", imageData);
//            image = imageService.uploadImage(image);
//            teacher.setImage(image);
//            teacher = teacherService.register(teacher);
//            dto = convertToDTO(teacher);
//        }
//        catch (Exception e){
//            logger.error(e.toString());
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha podido registrar");
//        }
//        return new ResponseEntity<TeacherDTO>(dto, HttpStatus.OK);
//    }
    @GetMapping("/teacher")
    public <Flux>ResponseEntity<List<TeacherDTO>> list(){
        List<Teacher> list;
        List<TeacherDTO> listDTO=null;
        try {
            list = teacherService.listTeacher();
            listDTO = convertToListDTO(list);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista no disponible");
        }
        return new ResponseEntity<>(listDTO,HttpStatus.OK);
    }

    @GetMapping("/teacher/{id}")
    public <Mono>ResponseEntity<TeacherDTO> getById(@PathVariable(value="id") Long id){
        Teacher teacher;
        TeacherDTO dto;
        try{
            teacher = teacherService.getTeacherByID(id);
            dto = convertToDTO(teacher);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe profesor con ese ID");
        }
        return new ResponseEntity<TeacherDTO>(dto,HttpStatus.OK);
    }

    @PutMapping("/teacher")
    public <Mono>ResponseEntity<TeacherDTO> updateTeacher(@RequestBody TeacherDTO teacherDTO){
        Teacher teacher;
        TeacherDTO dto;
        try{
            teacher = convertToEntity(teacherDTO);
            teacher = teacherService.updateTeacher(teacher);
            dto = convertToDTO(teacher);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se puede Actualizar");
        }
        return new ResponseEntity<TeacherDTO>(dto,HttpStatus.OK);
    }
    @DeleteMapping("/teacher/{id}")
    public <Mono>ResponseEntity<TeacherDTO> delete(@PathVariable(value="id") Long id){
        Teacher teacher;
        TeacherDTO dto;
        try {
            teacher = teacherService.deleteTeacher(id);
            dto = convertToDTO(teacher);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontro el profesor");
        }
        return new ResponseEntity<TeacherDTO>(dto, HttpStatus.OK);
    }

    @PutMapping("/teacher/{teacherId}/courses/{courseId}")
    public <Mono>ResponseEntity<TeacherDTO> addCourse(@PathVariable(value="teacherId") Long teacherId, @PathVariable(value="courseId") Long courseId)throws Exception{
        Teacher teacher;
        TeacherDTO dto;
        try {
            teacher = teacherService.addCourse(teacherId,courseId);
            dto = convertToDTO(teacher);
        }catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontro el profesor");
        }
        return new ResponseEntity<TeacherDTO>(dto, HttpStatus.OK);
    }
    @GetMapping("/teacher/findbyfullname/{fullname}")
    public <Flux>ResponseEntity<List<TeacherDTO>> listByFullname(@PathVariable (value = "fullname")String fullname){
        List<Teacher> list;
        List<TeacherDTO> listDTO=null;
        try {
            list = teacherService.listByFullName(fullname);
            listDTO = convertToListDTO(list);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista no disponible");
        }
        return new ResponseEntity<>(listDTO,HttpStatus.OK);
    }

    //Implementar filtro de rigurosidad
    @GetMapping("/teacher/rigurosity/{rigurosityName}")
    public <Flux>ResponseEntity<List<TeacherDTO>> listTeachersByRigurosity(@PathVariable(value="rigurosityName") String rigurosityName){
        List<Teacher> list;
        List<TeacherDTO> listDTO=null;
        try {
            list = teacherService.listTeachersByRigurosity(rigurosityName);
            listDTO = convertToListDTO(list);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista no disponible");
        }
        return new ResponseEntity<>(listDTO,HttpStatus.OK);
    }

    @GetMapping("/teacher/advancedsearch/{qualificationFrom}/{qualificationTo}/{rigurosityId}/{courseId}/{fullNameTeacher}")
    public <Flux>ResponseEntity<List<TeacherDTO>> listTeachersByFilters(@PathVariable(value="qualificationFrom") double qualificationFrom,
                                                                  @PathVariable(value="qualificationTo") double qualificationTo,
                                                                  @PathVariable(value="rigurosityId") Long rigurosityId,
                                                                  @PathVariable(value="courseId") Long courseId,
                                                                  @PathVariable(value="fullNameTeacher") String fullNameTeacher){
        List<Teacher> list;
        List<TeacherDTO> listDTO=null;
        try {
            list = teacherService.listTeachersByFilters(qualificationFrom,qualificationTo,rigurosityId,courseId,fullNameTeacher);
            listDTO = convertToListDTO(list);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista no disponible");
        }
        return new ResponseEntity<>(listDTO,HttpStatus.OK);
    }

    //Implementar filtro por curso para profesor
    @GetMapping("/teacher/course/{courseId}")
    public <Flux>ResponseEntity<List<TeacherDTO>> listTeachersByCourseFilter(@PathVariable(value="courseId") Long courseId){
        List<Teacher> list;
        List<TeacherDTO> listDTO=null;
        try {
            list = teacherService.listTeachersByCourse(courseId);
            listDTO = convertToListDTO(list);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista no disponible");
        }

        return new ResponseEntity<>(listDTO,HttpStatus.OK);
    }

    //Implementar filtro por calificacion para profesor
    @GetMapping("/teacher/qualification/{qualificationFrom}/{qualificationTo}")
    public <Flux>ResponseEntity<List<TeacherDTO>> listTeachersByQualificationFilter(@PathVariable(value="qualificationFrom") double qualificationFrom,
                                                                              @PathVariable(value="qualificationTo") double qualificationTo){
        List<Teacher> list;
        List<TeacherDTO> listDTO=null;
        try {
            list = teacherService.listTeachersByQualification(qualificationFrom,qualificationTo);
            listDTO = convertToListDTO(list);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista no disponible");
        }

        return new ResponseEntity<>(listDTO,HttpStatus.OK);
    }

    private Teacher convertToEntity(TeacherDTO teacherDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(teacherDTO, Teacher.class);
    }

    private TeacherDTO convertToDTO(Teacher teacher){
        ModelMapper modelMapper = new ModelMapper();
        // Mapear otros campos desde teacher a teacherDTO
        TeacherDTO teacherDTO = modelMapper.map(teacher, TeacherDTO.class);
        // Mapear los cursos desde teacher a CourseDTO y establecerlos en TeacherDTO
        List<CourseDTO> courseDTOList = teacher.getCoursearray()
                .stream()
                .map(course -> modelMapper.map(course, CourseDTO.class))
                .collect(Collectors.toList());
        teacherDTO.setCoursesarray(courseDTOList);
        if (teacher.getImage() != null) { // Null check to avoid NullPointerException
            ImageDTO imageDTO = modelMapper.map(teacher.getImage(), ImageDTO.class);
            teacherDTO.setImage(imageDTO);
        }
        //mapear el rigurosity
        RigurosityDTO rigurosityDTO= modelMapper.map(teacher.getRigurosity(), RigurosityDTO.class);
        teacherDTO.setRigurosity(rigurosityDTO);
        return teacherDTO;
    }

    private List<TeacherDTO> convertToListDTO(List<Teacher> list){
        return list.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    private byte[] readImageDataFromClasspath(String classpathResource) throws IOException {
        InputStream in = new ClassPathResource(classpathResource).getInputStream();
        return StreamUtils.copyToByteArray(in);
    }
}
