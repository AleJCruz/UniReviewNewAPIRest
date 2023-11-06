package com.upc.unireview.controller;

import com.upc.unireview.dto.CourseDTO;
import com.upc.unireview.dto.RigurosityDTO;
import com.upc.unireview.dto.TeacherDTO;
import com.upc.unireview.entities.Course;
import com.upc.unireview.entities.Rigurosity;
import com.upc.unireview.entities.Teacher;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.upc.unireview.interfaceservice.RigurosityService;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RigurosityController {
    @Autowired
    private RigurosityService rigurosityService;
    Logger logger = LoggerFactory.getLogger(RigurosityController.class);
    @PostMapping("/rigurosity")
    public ResponseEntity<RigurosityDTO> registerRigurosity(@RequestBody RigurosityDTO rigurosityDTO){
        Rigurosity rigurosity;
        RigurosityDTO dto;
        try{
            rigurosity = convertToEntity(rigurosityDTO);
            rigurosity = rigurosityService.registerRigurosity(rigurosity);
            dto = convertToDTO(rigurosity);

        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha podido registrar");
        }
        return new ResponseEntity<RigurosityDTO>(dto, HttpStatus.OK);
    }
    @GetMapping("/rigurosity")
    public ResponseEntity<List<RigurosityDTO>> listAllRigurosities(){
        List<Rigurosity> list;
        List<RigurosityDTO> listDTO=null;
        try {
            list = rigurosityService.listRigurosities();
            listDTO = convertToListDTO(list);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista no disponible");
        }
        return new ResponseEntity<>(listDTO,HttpStatus.OK);
    }
    @PutMapping("/rigurosity")
    public ResponseEntity<RigurosityDTO> updateRigurosity(@RequestBody RigurosityDTO rigurosityDTO){
        Rigurosity rigurosity;
        RigurosityDTO dto;
        try{
            rigurosity = convertToEntity(rigurosityDTO);
            rigurosity = rigurosityService.updateRigurosity(rigurosity);
            dto = convertToDTO(rigurosity);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se puede Actualizar");
        }
        return new ResponseEntity<RigurosityDTO>(dto,HttpStatus.OK);
    }
    @DeleteMapping("/rigurosity/{id}")
    public ResponseEntity<RigurosityDTO> delete(@PathVariable(value="id") Long id){
        Rigurosity rigurosity;
        RigurosityDTO dto;
        try {
            rigurosity = rigurosityService.deleteRigurosity(id);
            dto = convertToDTO(rigurosity);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontro el profesor");
        }
        return new ResponseEntity<RigurosityDTO>(dto, HttpStatus.OK);
    }

    private Rigurosity convertToEntity(RigurosityDTO rigurosityDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(rigurosityDTO, Rigurosity.class);
    }

    private RigurosityDTO convertToDTO(Rigurosity rigurosity){
        ModelMapper modelMapper =new ModelMapper();
        return modelMapper.map(rigurosity, RigurosityDTO.class);
    }

    private List<RigurosityDTO> convertToListDTO(List<Rigurosity> list){
        return list.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
