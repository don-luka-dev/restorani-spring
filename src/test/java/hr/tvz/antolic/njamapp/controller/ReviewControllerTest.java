package hr.tvz.antolic.njamapp.controller;

import hr.tvz.antolic.njamapp.dto.ReviewDTO;
import hr.tvz.antolic.njamapp.model.Restaurant;
import hr.tvz.antolic.njamapp.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ReviewControllerTest {

    private MockMvc mockMvc;

    @Mock
    ReviewService reviewService;

    @InjectMocks
    ReviewController reviewController;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
    }

    private Restaurant createRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Burger Bar"); // ispravno ime
        return restaurant;
    }

    private ReviewDTO createReview1() {
        return new ReviewDTO(1L, "Great burger", "Best burger I’ve had!", 5, createRestaurant());
    }

    private ReviewDTO createReview2() {
        return new ReviewDTO(2L, "Okay burger", "Pretty average.", 3, createRestaurant());
    }

    @Test
    void getAllReviews() throws Exception {
        List<ReviewDTO> reviews = List.of(createReview1(), createReview2());

        when(reviewService.findAll()).thenReturn(reviews);

        mockMvc.perform(get("/review/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("Great burger"))
                .andExpect(jsonPath("$[0].restaurant.name").value("Burger Bar"))
                .andExpect(jsonPath("$[1].rating").value(3));
    }

    @Test
    void getReviewsByRestaurantId() throws Exception {
        Long restaurantId = 1L;
        List<ReviewDTO> reviews = List.of(createReview1(), createReview2());

        when(reviewService.findReviewByRestaurantId(restaurantId)).thenReturn(reviews);

        mockMvc.perform(get("/review/restaurantId/{id}", restaurantId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].text").value("Pretty average."))
                .andExpect(jsonPath("$[0].restaurant.id").value(1));
    }
}

