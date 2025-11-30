package hr.tvz.antolic.njamapp.service;

import hr.tvz.antolic.njamapp.dto.ReviewDTO;
import hr.tvz.antolic.njamapp.model.Review;

import java.util.List;


public interface ReviewService {

    List<ReviewDTO> findAll();
    List<ReviewDTO> findReviewByRestaurantId(Long id);
}
