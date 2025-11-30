package hr.tvz.antolic.njamapp.controller;

import hr.tvz.antolic.njamapp.dto.RestaurantDTO;
import hr.tvz.antolic.njamapp.dto.ReviewDTO;
import hr.tvz.antolic.njamapp.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@CrossOrigin(origins = "http://localhost:4200")
public class ReviewController {

    private final ReviewService reviewService;


    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/all")
    public List<ReviewDTO> getAll() {
        //log.info("getAllRestaurantsItems called");
        return reviewService.findAll();
    }

    @GetMapping("/restaurantId/{id}")
    public List<ReviewDTO> getReviewByRestaurantId(@PathVariable Long id) {
        return  reviewService.findReviewByRestaurantId(id);
    }
}
