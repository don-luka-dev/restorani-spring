package hr.tvz.antolic.njamapp.model;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Entity
public class WorkingHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "open_time")
    private LocalTime openingTime;

    @Column(name = "close_time")
    private LocalTime closingTime;

    public WorkingHours() {
    }

    public WorkingHours(Long id,LocalTime openingTime, LocalTime closingTime) {
        this.id = id;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    // Getteri i setteri

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isOpen(LocalTime currentTime) {
        return currentTime.isAfter(openingTime) && currentTime.isBefore(closingTime);
    }
}
