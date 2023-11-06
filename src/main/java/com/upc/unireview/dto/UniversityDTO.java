package com.upc.unireview.dto;
import lombok.Data;
import javax.persistence.Column;
@Data
public class UniversityDTO {
    private Long id;
    private String name;
    private String campus;
    private transient double qualification;
    private double pension;
    private boolean havepostgraduate;
    private boolean haveundergraduate;
    private boolean havepeoplewhowork;
    private String enrollmentLink;
    private ImageDTO image;
}
