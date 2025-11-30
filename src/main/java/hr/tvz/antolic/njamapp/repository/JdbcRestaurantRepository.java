package hr.tvz.antolic.njamapp.repository;

import hr.tvz.antolic.njamapp.model.Address;
import hr.tvz.antolic.njamapp.model.Contact;
import hr.tvz.antolic.njamapp.model.Restaurant;
import hr.tvz.antolic.njamapp.model.WorkingHours;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//@Primary
@Repository
public class JdbcRestaurantRepository implements RestaurantRepository {

    private static final String SELECT_BY_ID = "SELECT * FROM RESTAURANT WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM RESTAURANT";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertRestaurant;
    private WorkingHoursRepository workingHoursRepository;

    public JdbcRestaurantRepository(JdbcTemplate jdbcTemplate, WorkingHoursRepository workingHoursRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.workingHoursRepository = workingHoursRepository;
        this.insertRestaurant = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("restaurant")
                .usingGeneratedKeyColumns("id");
    }
    @Override
    public List<Restaurant> findAll() {
        return jdbcTemplate.query(SELECT_ALL, this::mapRow);
    }

    @Override
    public Optional<Restaurant> findRestaurantByID(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID, this::mapRow, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Restaurant> save(Restaurant restaurant) {
        try {
            restaurant.setId(saveRestaurantDetails(restaurant));
            return Optional.of(restaurant);
        } catch (DuplicateKeyException e) {
            return Optional.empty();
        }
    }

    private long saveRestaurantDetails(Restaurant restaurant) {
        Map<String, Object> values = new HashMap<>();

        values.put("restaurant_name", restaurant.getName());
        values.put("address_city", restaurant.getAddress().getCity());
        values.put("address_street", restaurant.getAddress().getStreet());
        values.put("address_house_number", restaurant.getAddress().getHouseNumber());
        values.put("address_postal_code", restaurant.getAddress().getPostalCode());
        values.put("contact_phone_number", restaurant.getContact().getPhoneNumber());
        values.put("contact_email", restaurant.getContact().getEmail());
        values.put("working_hours_id", 1L);
        values.put("is_open", restaurant.isOpen());
        values.put("average_delivery_time_seconds", restaurant.getAverageDeliveryTime().toSeconds());
        values.put("average_customer_rating", restaurant.getAverageCustomerRating());
        values.put("max_orders", restaurant.getMaxOrders());
        values.put("michelin_star", restaurant.getMichelinStar());
        values.put("kratak_opis", restaurant.getKratakOpis());

        return insertRestaurant.executeAndReturnKey(values).longValue();
    }


    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM restaurant WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private Restaurant mapRow(ResultSet rs, int rowNum) throws SQLException {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(rs.getLong("id"));
        restaurant.setName(rs.getString("restaurant_name"));

        Address address = new Address();
        address.setCity(rs.getString("address_city"));
        address.setStreet(rs.getString("address_street"));
        address.setHouseNumber(rs.getString("address_house_number"));
        address.setPostalCode(rs.getString("address_postal_code"));
        restaurant.setAddress(address);

        Contact contact = new Contact();
        contact.setPhoneNumber(rs.getString("contact_phone_number"));
        contact.setEmail(rs.getString("contact_email"));
        restaurant.setContact(contact);

        WorkingHours workingHours = workingHoursRepository.findWorkingHoursById(rs.getLong("working_hours_id"));
        restaurant.setWorkingHours(workingHours);

        restaurant.setOpen(rs.getBoolean("is_open"));
        restaurant.setAverageDeliveryTime(Duration.ofDays(rs.getLong("average_delivery_time_seconds")));
        restaurant.setAverageCustomerRating(rs.getDouble("average_customer_rating"));
        restaurant.setMaxOrders(rs.getInt("max_orders"));
        restaurant.setMichelinStar(rs.getInt("michelin_star"));
        restaurant.setKratakOpis(rs.getString("kratak_opis"));

        return restaurant;
    }


    @Override
    public boolean existsByNameAndAddress(String name, Address address) {
        return false;
    }
}
