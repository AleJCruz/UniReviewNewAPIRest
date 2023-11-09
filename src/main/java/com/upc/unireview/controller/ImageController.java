package com.upc.unireview.controller;

import com.upc.unireview.dto.ImageDTO;
import com.upc.unireview.entities.Image;
import com.upc.unireview.interfaceservice.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ImageController {

    @Autowired
    ImageService imageService;

    @PostMapping("/image/upload")
    public <Mono>ResponseEntity<ImageDTO> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            if (file != null) {
                Image image = new Image();
                image.setFilename(file.getOriginalFilename());
                image.setImageData(file.getBytes());
                image =  imageService.uploadImage(image);
                ImageDTO dto = convertToDTO(image);
                return new ResponseEntity<>(dto, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/image/list")
    public <Flux>ResponseEntity<List<ImageDTO>> getAllImages() {
        return new ResponseEntity<>(convertToListDTO(imageService.listImage()), HttpStatus.OK);
    }

    @DeleteMapping("/image/{id}")
    public <Mono>ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private ImageDTO convertToDTO(Image image){
        ModelMapper modelMapper =new ModelMapper();
        return modelMapper.map(image, ImageDTO.class);
    }

    private List<ImageDTO> convertToListDTO(List<Image> list){
        return list.stream().map(this::convertToDTO).collect(Collectors.toList());
    }


}
