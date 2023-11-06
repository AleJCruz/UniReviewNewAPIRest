package com.upc.unireview.dto;

import lombok.Data;

@Data
public class ImageDTO {
    private Long id;
    private String filename;
    private byte[] imageData; // Datos binarios de la imagen
}
