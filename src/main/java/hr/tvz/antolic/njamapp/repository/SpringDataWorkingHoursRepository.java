package hr.tvz.antolic.njamapp.repository;

import hr.tvz.antolic.njamapp.model.WorkingHours;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface SpringDataWorkingHoursRepository extends JpaRepository<WorkingHours, Long> {


}
