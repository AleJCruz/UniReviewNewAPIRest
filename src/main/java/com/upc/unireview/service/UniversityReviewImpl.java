package com.upc.unireview.service;

import com.upc.unireview.entities.UniversityReview;
import com.upc.unireview.interfaceservice.UniversityReviewService;
import com.upc.unireview.repository.UniversityReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversityReviewImpl implements UniversityReviewService {

    @Autowired
    UniversityReviewRepository universityReviewRepository;

    public UniversityReview register(UniversityReview universityReview) throws Exception{
        return universityReviewRepository.save(universityReview);
    }

    public UniversityReview update(UniversityReview universityReview) throws Exception{
        //controlar que no se repita el campus al actualizar
        universityReviewRepository.findById(universityReview.getId()).
                orElseThrow(()->new Exception("No se encontró la entidad"));
        return universityReviewRepository.save(universityReview);
    }

    public UniversityReview delete(Long id) throws Exception{
        UniversityReview universityReview = universityReviewRepository.findById(id).orElseThrow(()->new Exception("No se encontro la universidad"));
        universityReviewRepository.delete(universityReview);
        return universityReview;
    }
    public List<UniversityReview> listUniversityReviewByNameU(String prefix){return universityReviewRepository.findUniversityReviewsByUniversity_NameStartingWith(prefix);}
    //este es para la barra de busqueda al momento de escribir el nombre de la universidad y darle click a la imagen de la universidad, carga todas las reseñas en una nueva página
    public List<UniversityReview> listUniversityReviewById(Long id){return universityReviewRepository.findUniversityReviewsByUniversity_Id(id);}
    public List<UniversityReview> listUniversitiesReviews(){
        return universityReviewRepository.findAll();
    }
}
