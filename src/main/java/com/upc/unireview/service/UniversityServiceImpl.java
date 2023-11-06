package com.upc.unireview.service;
import com.upc.unireview.entities.University;
import com.upc.unireview.entities.UniversityReview;
import com.upc.unireview.entities.User;
import com.upc.unireview.interfaceservice.UniversityService;
import com.upc.unireview.repository.UniversityReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.upc.unireview.repository.UniversityRepository;

import java.util.ArrayList;
import java.util.List;
@Service
public class UniversityServiceImpl implements UniversityService{
    @Autowired
    private UniversityRepository universityRepository;
    @Autowired
    private UniversityReviewRepository universityReviewRepository;

    public University register(University university) throws Exception{
        return universityRepository.save(university);
    }

    public void setUniversityQualification(University university){
        List<UniversityReview> l1= universityReviewRepository.findUniversityReviewsByUniversity_Id(university.getId());
        double sum=0;
        for (UniversityReview universityReview:l1
             ) {
            sum+=universityReview.getScore();
        }
        university.setQualification(sum/l1.size());
    }

    public University updateUniversity(University university) throws Exception{
        //controlar que no se repita el campus al actualizar
        universityRepository.findById(university.getId()).
                orElseThrow(()->new Exception("No se encontró la entidad"));
        return universityRepository.save(university);
    }

    public University deleteUniversity(Long id) throws Exception {
        University university = universityRepository.findById(id).orElseThrow(() -> new Exception("No se encontro la universidad"));
        universityRepository.delete(university);
        return university;
    }
    public List<University> listUniversities(){
        List<University> l1=new ArrayList<>();
        for (University university:universityRepository.findAll()){
            setUniversityQualification(university);
            l1.add(university);
        }
        return l1;
    }
    public List<University> listUniversitiesByName(String name){
        List <University> l1 = universityRepository.findAllByNameStartingWith(name);
        for (University university:l1
             ) {
               setUniversityQualification(university);
        }
        return l1;
    }

    public List<University> listUniversitiesByFilters(String district, String modality, double qualificationFrom, double qualificationTo, double pensionFrom, double pensionTo){
        List<University> list= listUniversities();
        if(!district.isEmpty() && district != null){
            for (University university:listUniversities()
                 ) {
                if(!university.getCampus().equals(district)){
                    list.remove(university);
                }
            }
        }
        if(!modality.isEmpty() && modality != null){
            for (University university:listUniversities()
            ) {
                switch (modality){
                    case "EPE":
                        if(!university.isHavepeoplewhowork()){
                            list.remove(university);
                        }
                        break;
                    case "PREGRADO":
                        if(!university.isHaveundergraduate()){
                            list.remove(university);
                        }
                        break;
                    case "POSGRADO":
                        if(!university.isHavepostgraduate()){
                            list.remove(university);
                        }
                        break;
                }
            }
        }

        if((pensionFrom<pensionTo) && pensionFrom >= 0){
            for (University university:listUniversities()
            ) {
                if(university.getPension()<pensionFrom || university.getPension()>pensionTo){
                    list.remove(university);
                }
            }
        }
        for (University university:list
        ) {
            setUniversityQualification(university);
        }
        return list;
    }

    //filtro para universidad si solo quiere buscar por tipo de educación
    public List<University> listUniversitiesByEducationType(String educationType){
        List<University> list= new ArrayList<>();
        for (University uni :listUniversities()) {
            switch (educationType){
                //EPE
                case "EPE":
                    if(uni.isHavepeoplewhowork()){
                        list.add(uni);
                    }
                    break;
                //PREGRADO
                case "PREGRADO":
                    if(uni.isHaveundergraduate()){
                        list.add(uni);
                    }
                    break;
                //POSGRADO
                case "POSGRADO":
                    if(uni.isHavepostgraduate()){
                        list.add(uni);
                    }
                    break;
            }
        }
        return list;
    }

    //filtro de universidad por pensión promedio
    public List<University> listUniversitiesByPension(double pensionFrom, double pensionTo){
        List<University> list= new ArrayList<>();
        for (University uni :listUniversities()) {
            if(uni.getPension()>=pensionFrom && uni.getPension()<=pensionTo){
                list.add(uni);
            }
        }
        return list;
    }

    //filtro para ingresar el id de una universidad y devolver su link de matrícula
    public String getEnrollmentLink(Long id){
        return universityRepository.findById(id).get().getEnrollmentLink();
    }
}
