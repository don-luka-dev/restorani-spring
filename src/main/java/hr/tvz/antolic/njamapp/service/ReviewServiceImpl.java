package hr.tvz.antolic.njamapp.service;

import hr.tvz.antolic.njamapp.dto.RestaurantDTO;
import hr.tvz.antolic.njamapp.dto.ReviewDTO;
import hr.tvz.antolic.njamapp.model.Restaurant;
import hr.tvz.antolic.njamapp.model.Review;
import hr.tvz.antolic.njamapp.repository.SpringDataReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService{


    SpringDataReviewRepository reviewRepository;

    public ReviewServiceImpl(SpringDataReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<ReviewDTO> findAll() {
        return reviewRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> findReviewByRestaurantId(Long id) {
        return reviewRepository.findReviewByRestaurantId(id)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

    }

    private ReviewDTO convertToDTO(Review review) {
        return new ReviewDTO(
                review.getId(),
                review.getTitle(),
                review.getText(),
                review.getRating(),
                review.getRestaurant()

        );
    }
}
