package hr.tvz.antolic.njamapp.model;

import jakarta.persistence.*;

import jakarta.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String text;

    @Column(nullable = false)
    private int rating; // 1 - 5

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    // getteri i setteri

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public int getRating() { return rating; }

    public void setRating(int rating) { this.rating = rating; }

    public Restaurant getRestaurant() { return restaurant; }

    public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }
}



