package hr.tvz.antolic.njamapp.repository;


import hr.tvz.antolic.njamapp.model.Review;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
public interface SpringDataReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.restaurant.id = ?1")
    List<Review> findReviewByRestaurantId(Long id);

}
