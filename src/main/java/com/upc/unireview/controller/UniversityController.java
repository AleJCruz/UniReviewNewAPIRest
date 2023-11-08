package com.upc.unireview.controller;
import com.upc.unireview.dto.ImageDTO;
import com.upc.unireview.dto.UniversityDTO;
import com.upc.unireview.entities.University;
import com.upc.unireview.interfaceservice.ImageService;
import com.upc.unireview.interfaceservice.UniversityService;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api")
public class UniversityController {
    @Autowired
    private UniversityService universityService;
    @Autowired
    private ImageService imageService;
    Logger logger = LoggerFactory.getLogger(UniversityController.class);
    @PostMapping("/university")
    public <Mono>ResponseEntity<UniversityDTO> register(@RequestBody UniversityDTO universityDTO){
        University university;
        UniversityDTO dto;
        try {
            university=convertToEntity(universityDTO);
            university=universityService.register(university);
            dto=convertToDTO(university);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No se ha podido registrar");
        }
        return new ResponseEntity<UniversityDTO>(dto,HttpStatus.OK);
    }

    @GetMapping("/universities")
    public <Flux>ResponseEntity<List<UniversityDTO>> list(){
        List<University> list;
        List<UniversityDTO> listDTO=null;
        try {
            list = universityService.listUniversities();
            listDTO = convertToListDTO(list);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista no disponible");
        }
        return new ResponseEntity<>(listDTO,HttpStatus.OK);
    }

    @PutMapping("/university")
    public <Mono>ResponseEntity<UniversityDTO> updateUser(@RequestBody UniversityDTO universityDTO){
        University university;
        UniversityDTO dto;
        try{
            university = convertToEntity(universityDTO);
            university = universityService.updateUniversity(university);
            dto = convertToDTO(university);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se puede Actualizar");
        }
        return new ResponseEntity<UniversityDTO>(dto,HttpStatus.OK);
    }

    @DeleteMapping("/university/{id}")
    public <Mono>ResponseEntity<UniversityDTO> delete(@PathVariable(value="id") Long id){
        University university;
        UniversityDTO dto;
        try {
            university = universityService.deleteUniversity(id);
            dto = convertToDTO(university);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontro el usuario");
        }
        return new ResponseEntity<UniversityDTO>(dto, HttpStatus.OK);
    }

    @GetMapping("/universities/findby/{name}")
    public <Flux>ResponseEntity<List<UniversityDTO>> listByName(@PathVariable (value = "name")String name){
        List<University> list;
        List<UniversityDTO> listDTO=null;
        try {
            list = universityService.listUniversitiesByName(name);
            listDTO = convertToListDTO(list);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista no disponible");
        }
        return new ResponseEntity<>(listDTO,HttpStatus.OK);
    }
    //TODO: Implementar filtros
    @GetMapping("/universities/findbyfilters/{district}/{modality}/{qualificationFrom}/{qualificationTo}/{pensionFrom}/{pensionTo}")
    public <Flux>ResponseEntity<List<UniversityDTO>> listByFiltersAdvanced(@PathVariable(value = "district") String district,
                                                                      @PathVariable(value = "modality") String modality,
                                                                      @PathVariable(value = "qualificationFrom") double qualificationFrom,
                                                                      @PathVariable(value = "qualificationTo") double qualificationTo,
                                                                      @PathVariable(value = "pensionFrom") double pensionFrom,
                                                                      @PathVariable(value = "pensionTo") double pensionTo){
        List<University> list;
        List<UniversityDTO> listDTO=null;
        try {
            list = universityService.listUniversitiesByFilters(district,modality,qualificationFrom,qualificationTo,pensionFrom,pensionTo);
            listDTO = convertToListDTO(list);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista no disponible");
        }
        return new ResponseEntity<>(listDTO,HttpStatus.OK);
    }

    //Implementar filtro de tipo de educacion
    @GetMapping("/universities/findbyeducationtype/{educationType}")
    public <Flux>ResponseEntity<List<UniversityDTO>> listByEducationType(@PathVariable(value = "educationType") String educationType)
    {
        List<University> list;
        List<UniversityDTO> listDTO=null;
        try {
            list = universityService.listUniversitiesByEducationType(educationType);
            listDTO = convertToListDTO(list);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista no disponible");
        }
        return new ResponseEntity<>(listDTO,HttpStatus.OK);
    }

    //universidad por id
    @GetMapping("/university/{id}")
    public <Mono>ResponseEntity<UniversityDTO> getById(@PathVariable(value="id") Long id){
        University university;
        UniversityDTO dto;
        try{
            university = universityService.findByID(id);
            dto = convertToDTO(university);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe profesor con ese ID");
        }
        return new ResponseEntity<UniversityDTO>(dto,HttpStatus.OK);
    }

    //Implementar filtro de pensión promedio
    @GetMapping("/universities/findbypension/{pensionFrom}/{pensionTo}")
    public <Flux>ResponseEntity<List<UniversityDTO>> listByPension(@PathVariable(value = "pensionFrom") double pensionFrom,
                                                             @PathVariable(value = "pensionTo") double pensionTo)
    {
        List<University> list;
        List<UniversityDTO> listDTO=null;
        try {
            list = universityService.listUniversitiesByPension(pensionFrom,pensionTo);
            listDTO = convertToListDTO(list);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista no disponible");
        }
        return new ResponseEntity<>(listDTO,HttpStatus.OK);
    }

    //Implementar filtro de link de matricula
    @GetMapping("/universities/findlinkbyid/{id}")
    public <Mono>ResponseEntity<String> listLinkById(@PathVariable(value = "id") Long id)
    {
        String link;
        try {
            link = universityService.getEnrollmentLink(id);
        }
        catch (Exception e){
            logger.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista no disponible");
        }
        return new ResponseEntity<>(link,HttpStatus.OK);
    }

    private University convertToEntity(UniversityDTO universityDTO){
        ModelMapper modelMapper = new ModelMapper();
        University university = modelMapper.map(universityDTO, University.class);
        return university;
    }

    private UniversityDTO convertToDTO(University university){
        ModelMapper modelMapper =new ModelMapper();
        UniversityDTO universityDTO = modelMapper.map(university, UniversityDTO.class);
        if (university.getImage() != null) { // Null check to avoid NullPointerException
            ImageDTO imageDTO = modelMapper.map(university.getImage(), ImageDTO.class);
            universityDTO.setImage(imageDTO);
        }
        return universityDTO;
    }

    private List<UniversityDTO> convertToListDTO(List<University> list){
        return list.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private byte[] readImageDataFromClasspath(String classpathResource) throws IOException {
        InputStream in = new ClassPathResource(classpathResource).getInputStream();
        return StreamUtils.copyToByteArray(in);
    }
}
