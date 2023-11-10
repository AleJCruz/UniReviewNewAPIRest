package com.upc.unireview.controller;
import com.upc.unireview.dto.CourseDTO;
import com.upc.unireview.entities.Course;
import com.upc.unireview.interfaceservice.CourseService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CourseController {
    @Autowired
    private CourseService courseService;
    Logger logger = LoggerFactory.getLogger(CourseController.class);

    @PostMapping("/course")
    public <Mono>ResponseEntity<CourseDTO> register(@RequestBody CourseDTO courseDTO){
        Course course;
        CourseDTO dto;
        try{
            course = convertToEntity(courseDTO);
            course = courseService.register(course);
            dto = convertToDTO(course);

        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha podido registrar");
        }
        return new ResponseEntity<CourseDTO>(dto, HttpStatus.OK);
    }
    @GetMapping("/courses")
    public <Flux>ResponseEntity<List<CourseDTO>> list(){
        List<Course> list;
        List<CourseDTO> listDTO=null;
        try {
            list = courseService.listCourses();
            listDTO = convertToListDTO(list);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista no disponible");
        }
        return new ResponseEntity<>(listDTO,HttpStatus.OK);
    }

    @PutMapping("/course")
    public <Mono>ResponseEntity<CourseDTO> updateCourse(@RequestBody CourseDTO courseDTO){
        Course course;
        CourseDTO dto;
        try{
            course = convertToEntity(courseDTO);
            course = courseService.updateCourse(course);
            dto = convertToDTO(course);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se puede Actualizar");
        }
        return new ResponseEntity<CourseDTO>(dto,HttpStatus.OK);
    }

    @DeleteMapping("/course/{id}")
    public <Mono>ResponseEntity<CourseDTO> delete(@PathVariable(value="id") Long id){
        Course course;
        CourseDTO dto;
        try {
            course = courseService.deleteCourse(id);
            dto = convertToDTO(course);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el curso");
        }
        return new ResponseEntity<CourseDTO>(dto, HttpStatus.OK);
    }

    private Course convertToEntity(CourseDTO courseDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(courseDTO, Course.class);
    }

    private CourseDTO convertToDTO(Course course){
        ModelMapper modelMapper =new ModelMapper();
        return modelMapper.map(course, CourseDTO.class);
    }

    private List<CourseDTO> convertToListDTO(List<Course> list){
        return list.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
