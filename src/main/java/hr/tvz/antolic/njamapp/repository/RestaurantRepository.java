package hr.tvz.antolic.njamapp.repository;

import hr.tvz.antolic.njamapp.model.Address;
import hr.tvz.antolic.njamapp.model.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository {

    List<Restaurant> findAll();
    Optional<Restaurant> findRestaurantByID(Long id);
    boolean existsByNameAndAddress(String name, Address address);
    Optional<Restaurant> save(Restaurant restaurant);
    void delete(Long id);
    }
