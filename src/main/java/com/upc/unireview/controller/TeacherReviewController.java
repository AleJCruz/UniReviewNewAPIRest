package com.upc.unireview.controller;

import com.upc.unireview.dto.TeacherDTO;
import com.upc.unireview.dto.TeacherReviewDTO;
import com.upc.unireview.dto.UserDTO;
import com.upc.unireview.entities.TeacherReview;
import com.upc.unireview.interfaceservice.TeacherReviewService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TeacherReviewController {

    @Autowired
    private TeacherReviewService teacherReviewService;
    Logger logger = LoggerFactory.getLogger(TeacherReviewController.class);
    @PostMapping("/teacherReview")
    public ResponseEntity<TeacherReviewDTO> register(@RequestBody TeacherReviewDTO teacherReviewDTO){
        TeacherReview teacherReview;
        TeacherReviewDTO dto;
        try{
            teacherReview = convertToEntity(teacherReviewDTO);
            teacherReview = teacherReviewService.register(teacherReview);
            dto = convertToDTO(teacherReview);

        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha podido registrar");
        }
        return new ResponseEntity<TeacherReviewDTO>(dto, HttpStatus.OK);
    }
    @GetMapping("/teacherReviews")
    public ResponseEntity<List<TeacherReviewDTO>> list(){
        List<TeacherReview> list;
        List<TeacherReviewDTO> listDTO=null;
        try {
            list = teacherReviewService.listTeachersReviews();
            listDTO = convertToListDTO(list);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista no disponible");
        }
        return new ResponseEntity<>(listDTO,HttpStatus.OK);
    }
    @PutMapping("/teacherReview")
    public ResponseEntity<TeacherReviewDTO> updateTeacherReview(@RequestBody TeacherReviewDTO teacherDTO){
        TeacherReview teacherReview;
        TeacherReviewDTO dto;
        try{
            teacherReview = convertToEntity(teacherDTO);
            teacherReview = teacherReviewService.updateTeacherReview(teacherReview);
            dto = convertToDTO(teacherReview);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se puede Actualizar");
        }
        return new ResponseEntity<TeacherReviewDTO>(dto,HttpStatus.OK);
    }
    @GetMapping("/teacherReviews/byid/{id}")
    public ResponseEntity<List<TeacherReviewDTO>> listByTeacherId(@PathVariable(value="id") Long id){
        List<TeacherReview> list;
        List<TeacherReviewDTO> listDTO=null;
        try {
            list = teacherReviewService.listReviewsByTeacherId(id);
            listDTO = convertToListDTO(list);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista no disponible");
        }
        return new ResponseEntity<>(listDTO,HttpStatus.OK);
    }

    @DeleteMapping("/teacherReview/{id}")
    public ResponseEntity<TeacherReviewDTO> delete(@PathVariable(value="id") Long id){
        TeacherReview teacherReview;
        TeacherReviewDTO dto;
        try {
            teacherReview = teacherReviewService.deleteTeacherReview(id);
            dto = convertToDTO(teacherReview);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontro el profesor");
        }
        return new ResponseEntity<TeacherReviewDTO>(dto, HttpStatus.OK);
    }

    @GetMapping("/teacherReviews/byname/{prefix}")
    public ResponseEntity<List<TeacherReviewDTO>> listByNameTeacher(@PathVariable(value="prefix") String prefix){
        List<TeacherReview> list;
        List<TeacherReviewDTO> listDTO=null;
        try {
            list = teacherReviewService.listTeachersByTeacherName(prefix);
            listDTO = convertToListDTO(list);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista no disponible");
        }
        return new ResponseEntity<>(listDTO,HttpStatus.OK);
    }

    private TeacherReview convertToEntity(TeacherReviewDTO teacherReviewDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(teacherReviewDTO, TeacherReview.class);
    }

    private TeacherReviewDTO convertToDTO(TeacherReview teacherReview){
        ModelMapper modelMapper = new ModelMapper();
        // Mapear otros campos desde teacher a teacherDTO
        TeacherReviewDTO teacherReviewDTO = modelMapper.map(teacherReview, TeacherReviewDTO.class);

        TeacherDTO teacher = modelMapper.map(teacherReview.getTeacher(),TeacherDTO.class);
        teacherReviewDTO.setTeacherDTO(teacher);

        UserDTO user = modelMapper.map(teacherReview.getUser(),UserDTO.class);
        teacherReviewDTO.setUser(user);

        return teacherReviewDTO;
    }

    private List<TeacherReviewDTO> convertToListDTO(List<TeacherReview> list){
        return list.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
