package hr.tvz.antolic.njamapp.repository;

import hr.tvz.antolic.njamapp.model.Address;
import hr.tvz.antolic.njamapp.model.Restaurant;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Primary
@Repository
public interface SpringDataRestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("SELECT COUNT(r) > 0 FROM Restaurant r WHERE r.name = ?1 AND r.address = ?2")
    boolean existsByNameAndAddress(String name, Address address);

    boolean existsByCreatedBy_Username(String username);

}
