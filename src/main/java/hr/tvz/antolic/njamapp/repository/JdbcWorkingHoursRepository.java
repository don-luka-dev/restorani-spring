package hr.tvz.antolic.njamapp.repository;

import hr.tvz.antolic.njamapp.model.WorkingHours;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcWorkingHoursRepository implements WorkingHoursRepository {

    private static final String SELECT_ALL = "SELECT * FROM working_hours";
    private static final String SELECT_BY_ID = "SELECT * FROM working_hours WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    public JdbcWorkingHoursRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public WorkingHours findWorkingHoursById(Long id) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID, this::mapRow,id);
    }

    @Override
    public List<WorkingHours> findAll() {
        return jdbcTemplate.query(SELECT_ALL, this::mapRow);
    }


    public WorkingHours mapRow(ResultSet rs, int rowNum) throws SQLException {
            WorkingHours workingHours = new WorkingHours();
            workingHours.setId(rs.getLong("id"));
            workingHours.setOpeningTime(rs.getTime("open_time").toLocalTime());
            workingHours.setClosingTime(rs.getTime("close_time").toLocalTime());
            return workingHours;
        }
}

