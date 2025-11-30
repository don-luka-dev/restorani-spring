package hr.tvz.antolic.njamapp.dto;

import hr.tvz.antolic.njamapp.model.Restaurant;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewDTO{

    private Long id;
    private String title;
    private String text;
    private int rating; // 1 - 5
    private Restaurant restaurant;

    public ReviewDTO(Long id, String title, String text, int rating, Restaurant restaurant) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.rating = rating;
        this.restaurant = restaurant;
    }

}



