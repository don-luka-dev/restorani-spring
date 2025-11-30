package hr.tvz.antolic.njamapp.service;

import hr.tvz.antolic.njamapp.command.RestaurantCommand;
import hr.tvz.antolic.njamapp.dto.RestaurantDTO;
import hr.tvz.antolic.njamapp.model.WorkingHours;

import java.util.List;

public interface RestaurantService {

    List<RestaurantDTO> findAll();
    RestaurantDTO findRestaurantByID(Long id);
    List<RestaurantDTO> findBest(String address);
    List<RestaurantDTO> findWorst(Double rating);
    boolean addRestaurant(RestaurantCommand restaurantCommand);
    void deleteRestaurant(Long id);
    List<RestaurantDTO> findRestaurantByName(String name);
    boolean updateRestaurant(Long id, RestaurantCommand restaurantCommand);
    List<WorkingHours> findAllWorkingHours();
}
