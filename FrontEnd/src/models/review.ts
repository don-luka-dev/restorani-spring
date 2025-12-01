import { RestaurantDTO } from "./restaurant";

export interface Review {
    id?: number;
    title: string;
    text: string;
    rating: number; // vrijednost između 1 i 5
    restaurant: RestaurantDTO; 
    username: string;
  }

  
  