package com.upc.unireview.controller;

import com.upc.unireview.dto.UniversityDTO;
import com.upc.unireview.dto.UniversityReviewDTO;
import com.upc.unireview.dto.UserDTO;
import com.upc.unireview.entities.UniversityReview;
import com.upc.unireview.interfaceservice.UniversityReviewService;
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
public class UniversityReviewController {
    @Autowired
    private UniversityReviewService universityReviewService;
    Logger logger = LoggerFactory.getLogger(UniversityReviewController.class);
    @PostMapping("/universityReview")
    public ResponseEntity<UniversityReviewDTO> register(@RequestBody UniversityReviewDTO universityReviewDTO){
        UniversityReview universityReview;
        UniversityReviewDTO dto;
        try {
            universityReview=convertToEntity(universityReviewDTO);
            universityReview=universityReviewService.register(universityReview);
            dto=convertToDTO(universityReview);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No se ha podido registrar");
        }
        return new ResponseEntity<UniversityReviewDTO>(dto,HttpStatus.OK);
    }

    @GetMapping("/universityReview/byname/{prefix}")
    public ResponseEntity<List<UniversityReviewDTO>> list(@PathVariable(value="prefix") String prefix){
        List<UniversityReview> list;
        List<UniversityReviewDTO> listDTO=null;
        try {
            list = universityReviewService.listUniversityReviewByNameU(prefix);
            listDTO = convertToListDTO(list);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista no disponible");
        }
        return new ResponseEntity<>(listDTO,HttpStatus.OK);
    }
    @GetMapping("/universityReview/")
    public ResponseEntity<List<UniversityReviewDTO>> listByUniversityName(){
        List<UniversityReview> list;
        List<UniversityReviewDTO> listDTO=null;
        try {
            list = universityReviewService.listUniversitiesReviews();
            listDTO = convertToListDTO(list);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista no disponible");
        }
        return new ResponseEntity<>(listDTO,HttpStatus.OK);
    }

    @PutMapping("/universityReview")
    public ResponseEntity<UniversityReviewDTO> updateUser(@RequestBody UniversityReviewDTO universityReviewDTO){
        UniversityReview universityReview;
        UniversityReviewDTO dto;
        try{
            universityReview = convertToEntity(universityReviewDTO);
            universityReview = universityReviewService.update(universityReview);
            dto = convertToDTO(universityReview);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se puede Actualizar");
        }
        return new ResponseEntity<UniversityReviewDTO>(dto,HttpStatus.OK);
    }

    @DeleteMapping("/universityReview/{id}")
    public ResponseEntity<UniversityReviewDTO> delete(@PathVariable(value="id") Long id){
        UniversityReview universityReview;
        UniversityReviewDTO dto;
        try {
            universityReview = universityReviewService.delete(id);
            dto = convertToDTO(universityReview);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontro el usuario");
        }
        return new ResponseEntity<UniversityReviewDTO>(dto, HttpStatus.OK);
    }
    //Listar todas las reseñas pertenecientes a una universidad al pasarle por parámetro de búsqueda el Id de una universidad
    @GetMapping("/universityReview/byidUniversity/{id}")
    public ResponseEntity<List<UniversityReviewDTO>> listUniversityReviewsByIdOfUniversity(@PathVariable(value="id") Long id){
        List<UniversityReview> list;
        List<UniversityReviewDTO> listDTO=null;
        try {
            list = universityReviewService.listUniversityReviewById(id);
            listDTO = convertToListDTO(list);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista no disponible");
        }
        return new ResponseEntity<>(listDTO,HttpStatus.OK);
    }



    private UniversityReview convertToEntity(UniversityReviewDTO universityDTO){
        ModelMapper modelMapper = new ModelMapper();
        UniversityReview universityReview = modelMapper.map(universityDTO, UniversityReview.class);
        return universityReview;
    }

    private UniversityReviewDTO convertToDTO(UniversityReview universityReview){
        ModelMapper modelMapper =new ModelMapper();
        UniversityReviewDTO universityReviewDTO = modelMapper.map(universityReview, UniversityReviewDTO.class);

        UniversityDTO uni = modelMapper.map(universityReview.getUniversity(),UniversityDTO.class);
        universityReviewDTO.setUniversity(uni);

        UserDTO user = modelMapper.map(universityReview.getUser(),UserDTO.class);
        universityReviewDTO.setUser(user);
        return universityReviewDTO;
    }

    private List<UniversityReviewDTO> convertToListDTO(List<UniversityReview> list){
        return list.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
