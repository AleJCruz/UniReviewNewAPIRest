package com.upc.unireview.interfaceservice;

import com.upc.unireview.entities.University;
import com.upc.unireview.entities.User;

import java.util.List;

public interface UniversityService {
    public University register(University university) throws Exception;

    public University updateUniversity(University university) throws Exception;

    public University deleteUniversity(Long id) throws Exception;
    public List<University> listUniversitiesByName(String name);
    public List<University> listUniversities();
    public List<University> listUniversitiesByFilters(String district, String modality, double qualificationFrom, double qualificationTo, double pensionFrom, double pensionTo);
    public List<University> listUniversitiesByEducationType(String educationType);
    public List<University> listUniversitiesByPension(double pensionFrom, double pensionTo);
    public String getEnrollmentLink(Long id);
    public University findByID(Long ID);
}
