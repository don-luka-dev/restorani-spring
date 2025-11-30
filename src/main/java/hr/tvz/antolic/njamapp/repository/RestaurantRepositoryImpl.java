package hr.tvz.antolic.njamapp.repository;

import hr.tvz.antolic.njamapp.model.Address;
import hr.tvz.antolic.njamapp.model.Contact;
import hr.tvz.antolic.njamapp.model.Restaurant;
import hr.tvz.antolic.njamapp.model.WorkingHours;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository {

    WorkingHours workingHours = new WorkingHours(1L,LocalTime.of(8, 0), LocalTime.of(22, 0));

    private List<Restaurant> restaurants = new ArrayList<>(List.of(
            new Restaurant(
                    1L,
                    "Pizzeria Napoli",
                    new Address("Zagreb", "Ulica 1", "46A", "10000"),
                    new Contact("012345678", "napoli@email.com"),
                    workingHours,
                    true,
                    Duration.ofMinutes(30),
                    4.5,
                    50,
                    1,
                    "Authentic Italian pizzeria with a cozy atmosphere."
            ),
            new Restaurant(
                    2L,
                    "Bistro Paris",
                    new Address("Zagreb", "Ulica 2", "50D", "10000"),
                    new Contact("0987654321", "paris@email.com"),
                    workingHours,
                    false,
                    Duration.ofMinutes(45),
                    4.7,
                    40,
                    2,
                    "French cuisine with a modern twist."
            )
        )
    );


    @Override
    public List<Restaurant> findAll() {
        return restaurants;
    }

    @Override
    public Optional<Restaurant> findRestaurantByID(Long id) {
        return restaurants.stream()
                .filter(restaurant -> restaurant.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean existsByNameAndAddress(String name, Address address) {
        return restaurants.stream()
                .anyMatch(r -> r.getName().equalsIgnoreCase(name) && r.getAddress().equals(address));
    }

    @Override
    public  Optional<Restaurant> save(Restaurant restaurant) {
        restaurant.setId((long) (restaurants.size() + 1)); // Generiramo ID
        restaurants.add(restaurant);
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        restaurants.removeIf(restaurant -> restaurant.getId().equals(id));
    }
}
