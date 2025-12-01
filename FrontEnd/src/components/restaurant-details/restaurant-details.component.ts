
import { RestaurantDTO } from '../../models/restaurant';
import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RestaurantService } from '../../services/restaurant.service';
import { Review } from '../../models/review';
import { ReviewService } from '../../services/review.service';

@Component({
  selector: 'app-restaurant-details',
  templateUrl: './restaurant-details.component.html',
  styleUrls: ['./restaurant-details.component.css'],
  standalone: false,
})
export class RestaurantDetailsComponent implements OnInit {
  restaurant?: RestaurantDTO;
  reviews: Review[] = [];

  constructor(
    private route: ActivatedRoute,
    private restaurantService: RestaurantService,
    private reviewService: ReviewService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.restaurantService.getAll().subscribe(restorani => {
      this.restaurant = restorani.find(r => r.id === id);
      if (this.restaurant) {
        this.reviewService.getByRestaurantId(id).subscribe(reviews => {
          this.reviews = reviews;
        });
      }
    });
  }
}
