package hr.tvz.antolic.njamapp.dto;

import hr.tvz.antolic.njamapp.model.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantDTO {

    private Long id;
    private String restaurantName;
    private Address address;
    private boolean isOpen;
    private double workload;
    private String username;

    public RestaurantDTO(Long id, String restaurantName, boolean isOpen, Address address, double workload) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.isOpen = isOpen;
        this.address = address;
        this.workload = workload;
    }

    public RestaurantDTO() {
    }


}
