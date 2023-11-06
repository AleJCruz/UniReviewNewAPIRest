package com.upc.unireview.repository;

import com.upc.unireview.entities.UniversityReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UniversityReviewRepository extends JpaRepository<UniversityReview,Long> {
    List<UniversityReview> findUniversityReviewsByUniversity_NameStartingWith(String Prefix);

    public List<UniversityReview>findUniversityReviewsByUniversity_Id(Long id);

}
