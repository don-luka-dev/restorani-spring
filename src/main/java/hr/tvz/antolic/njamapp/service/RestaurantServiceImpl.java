package hr.tvz.antolic.njamapp.service;

import hr.tvz.antolic.njamapp.command.RestaurantCommand;
import hr.tvz.antolic.njamapp.domain.ApplicationUser;
import hr.tvz.antolic.njamapp.dto.RestaurantDTO;
import hr.tvz.antolic.njamapp.model.Restaurant;
import hr.tvz.antolic.njamapp.model.WorkingHours;
import hr.tvz.antolic.njamapp.repository.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final SpringDataRestaurantRepository restaurantRepository;
    private final SpringDataWorkingHoursRepository workingHoursRepository;
    private final UserRepository userRepository;

    public RestaurantServiceImpl(SpringDataRestaurantRepository restaurantRepository, SpringDataWorkingHoursRepository workingHoursRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.workingHoursRepository = workingHoursRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<RestaurantDTO> findAll() {
        return restaurantRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantDTO findRestaurantByID(Long id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        return restaurant.map(this::convertToDTO).orElse(null);
    }

    @Override
    public List<RestaurantDTO> findBest(String address) {
        return restaurantRepository.findAll()
                .stream()
                .filter(r -> r.getAddress().getCity().equalsIgnoreCase(address))
                .sorted((r1, r2) -> Double.compare(r2.getAverageCustomerRating(), r1.getAverageCustomerRating()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }



    @Override
    public List<RestaurantDTO> findWorst(Double rating) {
         return restaurantRepository.findAll()
                .stream()
                .filter(r -> r.getAverageCustomerRating() <= rating)
                .sorted((r1, r2) -> Double.compare(r1.getAverageCustomerRating(), r2.getAverageCustomerRating()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }



    @Override
    public List<RestaurantDTO> findRestaurantByName(String name) {
        return restaurantRepository.findAll()
                .stream()
                .filter(r -> r.getName().equalsIgnoreCase(name))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public boolean addRestaurant(RestaurantCommand restaurantCommand) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        ApplicationUser user = userRepository.findByUsername(currentUsername);

        if (user == null) {
            throw new UsernameNotFoundException("Korisnik nije pronađen");
        }

        boolean isAdmin = user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        if (!isAdmin && restaurantRepository.existsByCreatedBy_Username(user.getUsername()) && restaurantRepository.existsByNameAndAddress(restaurantCommand.getRestaurantName(), restaurantCommand.getAddress())) {
            // USER već ima jedan restoran ili restoran vec postoji
            return false;
        }

        Restaurant newRestaurant = new Restaurant(
                null,
                restaurantCommand.getRestaurantName(),
                restaurantCommand.getAddress(),
                restaurantCommand.getContact(),
                restaurantCommand.getWorkingHours(),
                restaurantCommand.isOpen(),
                restaurantCommand.getAverageDeliveryTime(),
                restaurantCommand.getAverageCustomerRating(),
                restaurantCommand.getMaxOrders(),
                restaurantCommand.getMichelinStar(),
                restaurantCommand.getKratakOpis()
        );

        newRestaurant.setCreatedBy(user); // moraš imati field u entitetu Restaurant

        restaurantRepository.save(newRestaurant);
        return true;
    }

    @Override
    public void deleteRestaurant(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        ApplicationUser currentUser = userRepository.findByUsername(username);

        if (currentUser == null) {
            throw new UsernameNotFoundException("Korisnik nije pronađen");
        }

        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        System.out.println("Korisnik: " + username + ", isAdmin: " + isAdmin);

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        if (isAdmin || restaurant.getCreatedBy().getId().equals(currentUser.getId())) {
            restaurant.setWorkingHours(null);
            restaurantRepository.deleteById(id);
            System.out.println("Restoran obrisan: " + id);
        } else {
            System.out.println("Access denied za korisnika: " + username);
            throw new AccessDeniedException("You are not authorized to delete this restaurant.");
        }
    }


    private RestaurantDTO convertToDTO(Restaurant restaurant) {
        int activeOrders = getRandomNumberBetween(10, 100);
        double workload = (double) activeOrders / restaurant.getMaxOrders();
        return new RestaurantDTO(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.isOpen(),
                restaurant.getAddress(),
                workload
        );
    }


    @Override
    public boolean updateRestaurant(Long id, RestaurantCommand restaurantCommand) {
        Optional<Restaurant> optional = restaurantRepository.findById(id);
        if (optional.isPresent()) {
            Restaurant r = optional.get();
            r.setName(restaurantCommand.getRestaurantName());
            r.setAddress(restaurantCommand.getAddress());
            r.setContact(restaurantCommand.getContact());
            r.setWorkingHours(restaurantCommand.getWorkingHours());
            r.setOpen(restaurantCommand.isOpen());
            r.setAverageDeliveryTime(restaurantCommand.getAverageDeliveryTime());
            r.setAverageCustomerRating(restaurantCommand.getAverageCustomerRating());
            r.setMaxOrders(restaurantCommand.getMaxOrders());
            restaurantRepository.save(r);
            return true;
        }
        return false;
    }

    @Override
    public List<WorkingHours> findAllWorkingHours() {
        return new ArrayList<>(workingHoursRepository.findAll());
    }

    private int getRandomNumberBetween(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

}
