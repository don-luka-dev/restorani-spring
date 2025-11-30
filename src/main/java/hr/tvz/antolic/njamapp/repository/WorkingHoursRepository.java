package hr.tvz.antolic.njamapp.repository;

import hr.tvz.antolic.njamapp.model.WorkingHours;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkingHoursRepository {

    WorkingHours findWorkingHoursById(Long Id);
    List<WorkingHours> findAll();

}
