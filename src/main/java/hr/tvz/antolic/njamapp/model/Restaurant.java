package hr.tvz.antolic.njamapp.model;

import hr.tvz.antolic.njamapp.domain.ApplicationUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.Duration;
import java.util.Map;
import java.util.List;

@Setter
@Getter
@Entity
public class Restaurant {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name="name")
    private String name;

    @Embedded
    private Address address;

    @Embedded
    private Contact contact;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "working_hours_id")
    private WorkingHours workingHours;

    @Column(name = "is_open")
    private boolean isOpen;

    @Column(name = "average_delivery_time_seconds")
    private Duration averageDeliveryTime;

    @Column(name = "average_customer_rating")
    private double averageCustomerRating;

    @Column(name = "max_orders")
    private int maxOrders;

    @Column(name = "michelin_star")
    private int michelinStar;

    @Column(name = "kratak_opis", length = 1000)
    private String kratakOpis;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private ApplicationUser createdBy;

    public Restaurant(Long id, String name, Address address, Contact contact, WorkingHours workingHours, boolean isOpen, Duration averageDeliveryTime, double averageCustomerRating, int maxOrders, int michelinStar, String kratakOpis) {
        this.id = id;
        this.name = name;
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

    public Restaurant() {

    }


}
