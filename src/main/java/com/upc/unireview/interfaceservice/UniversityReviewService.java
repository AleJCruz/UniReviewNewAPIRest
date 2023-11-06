package com.upc.unireview.interfaceservice;

import com.upc.unireview.entities.UniversityReview;

import java.util.List;

public interface UniversityReviewService {
    public UniversityReview register(UniversityReview universityReview) throws Exception;

    public UniversityReview update(UniversityReview universityReview) throws Exception;

    public UniversityReview delete(Long id) throws Exception;
    public List<UniversityReview> listUniversitiesReviews();
    public List<UniversityReview> listUniversityReviewByNameU(String prefix);
    public List<UniversityReview> listUniversityReviewById(Long id);

}
