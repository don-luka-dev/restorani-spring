package hr.tvz.antolic.njamapp.controller;
import hr.tvz.antolic.njamapp.command.RestaurantCommand;
import hr.tvz.antolic.njamapp.dto.RestaurantDTO;
import hr.tvz.antolic.njamapp.model.WorkingHours;
import hr.tvz.antolic.njamapp.service.RestaurantService;
import jakarta.validation.Valid;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurant")
@CrossOrigin(origins = "http://localhost:4200")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/all")
    public List<RestaurantDTO> getAll() {
        //log.info("getAllRestaurantsItems called");
        return restaurantService.findAll();
    }

    @GetMapping("/{id}")
    public RestaurantDTO getRestaurantById(@PathVariable Long id) {
        return restaurantService.findRestaurantByID(id);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<RestaurantDTO>> getRestaurantByName(@PathVariable String name) {
        List<RestaurantDTO> restaurants = restaurantService.findRestaurantByName(name);
        if (!restaurants.isEmpty()) {
            return ResponseEntity.ok(restaurants);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> addRestaurant(@Valid @RequestBody RestaurantCommand restaurantCommand) {
        boolean added = restaurantService.addRestaurant(restaurantCommand);
        Map<String, String> response = new HashMap<>();

        if (added) {
            response.put("message", "Restoran uspješno dodan.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.put("message", "Restoran s istim imenom i adresom već postoji.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }


    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        restaurantService.deleteRestaurant(id);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateRestaurant(@PathVariable Long id, @Valid @RequestBody RestaurantCommand restaurantCommand) {
        boolean updated = restaurantService.updateRestaurant(id, restaurantCommand);
        if (updated) {
            return ResponseEntity.ok().build(); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 BAD_REQUEST ako nije uspješno
        }
    }

    @GetMapping("/working-hours")
    public List<WorkingHours> getAllWorkingHours() {
        //log.info("getAllRestaurantsItems called");
        return restaurantService.findAllWorkingHours();
    }


}
