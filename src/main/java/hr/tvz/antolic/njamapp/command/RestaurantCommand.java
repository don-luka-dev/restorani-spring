package hr.tvz.antolic.njamapp.command;

import hr.tvz.antolic.njamapp.model.Address;
import hr.tvz.antolic.njamapp.model.Contact;
import hr.tvz.antolic.njamapp.model.WorkingHours;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.Duration;
import java.util.Map;

@Setter
@Getter
public class RestaurantCommand {

    @NotBlank(message = "Ime restorana ne smije biti prazno.")
    private String restaurantName;

    @NotNull(message = "Adresa restorana ne smije biti null.")
    private Address address;

    @NotNull(message = "Kontakt podaci su obavezni.")
    private Contact contact;

    @NotNull(message = "Radno vrijeme ne smije biti null.")
    private WorkingHours workingHours;

    private boolean isOpen;

    @NotNull(message = "Prosječno vrijeme dostave je obavezno.")
    private Duration averageDeliveryTime;

    @PositiveOrZero(message = "Prosječna ocjena mora biti 0 ili veća.")
    private double averageCustomerRating;

    @PositiveOrZero(message = "Maksimalan broj narudžbi mora biti 0 ili veći.")
    private int maxOrders;

    @PositiveOrZero(message = "Broj Michelin zvjezdica mora biti 0 ili veći.")
    private int michelinStar;

    @NotBlank(message = "Kratak opis ne smije biti prazan.")
    private String kratakOpis;

    public RestaurantCommand(String restaurantName, Address address, Contact contact, WorkingHours workingHours, boolean isOpen, Duration averageDeliveryTime, double averageCustomerRating, int maxOrders, int michelinStar, String kratakOpis) {
        this.restaurantName = restaurantName;
        this.address = address;
        this.contact = contact;
        this.workingHours = workingHours;
        this.isOpen = isOpen;
        this.averageDeliveryTime = averageDeliveryTime;
        this.averageCustomerRating = averageCustomerRating;
        this.maxOrders = maxOrders;
        this.michelinStar = michelinStar;
        this.kratakOpis = kratakOpis;
    }

}
