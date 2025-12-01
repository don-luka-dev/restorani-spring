import { Component, EventEmitter, Output } from '@angular/core';
import { Restaurant } from '../../models/restaurant';
import { RestaurantService } from '../../services/restaurant.service';

@Component({
  selector: 'app-add-restaurant',
  standalone: false,
  templateUrl: './add-restaurant.component.html',
  styleUrls: ['./add-restaurant.component.css']
})
export class AddRestaurantComponent {
  @Output() restoranDodan = new EventEmitter<void>();

  noviRestoran: Restaurant = {
    restaurantName: 'lirili',
    isOpen: false,
    address: { street: 'harambasiceva', city: 'zagreb', postalCode: '12222', houseNumber: '45' },
    averageCustomerRating: 4,
    maxOrders: 2,
    michelinStar: 2,
    kratakOpis: 'bokbok',
    contact: { phone: '234243242', email: 'info@lirili.com' },
    workingHours: {
      start: '08:00',
        end: '22:00'
    },
    averageDeliveryTime: 'PT30M'
  };

  constructor(private restaurantService: RestaurantService) {}

  dodajRestoran() {
    this.restaurantService.add(this.noviRestoran).subscribe({
      next: () => {
        //console.log('Restoran uspješno dodan.');
        this.restoranDodan.emit(); // signal to parent to refresh list
        this.resetForm();
      },
      error: (err) => console.error('Greška pri dodavanju:', err)
    });
  }

  resetForm() {
    this.noviRestoran = {
    restaurantName: 'lirili',
    isOpen: false,
    address: { street: 'harambasiceva', city: 'zagreb', postalCode: '12222', houseNumber: '45' },
    averageCustomerRating: 4,
    maxOrders: 2,
    michelinStar: 2,
    kratakOpis: 'bokbok',
    contact: { phone: '234243242', email: 'info@lirili.com' },
    workingHours: {
      start: '08:00',
        end: '22:00'
    },
    averageDeliveryTime: 'PT30M'
    };
  }
}