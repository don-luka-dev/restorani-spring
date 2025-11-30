package hr.tvz.antolic.njamapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.tvz.antolic.njamapp.command.RestaurantCommand;
import hr.tvz.antolic.njamapp.dto.RestaurantDTO;
import hr.tvz.antolic.njamapp.model.Address;
import hr.tvz.antolic.njamapp.model.Contact;
import hr.tvz.antolic.njamapp.model.Restaurant;
import hr.tvz.antolic.njamapp.model.WorkingHours;
import hr.tvz.antolic.njamapp.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RestaurantControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantController restaurantController;

    @BeforeEach
    void setUp() {mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();}

    @Test
    void getAll() throws Exception {
        when(restaurantService.findAll()).thenReturn(List.of(createRestaurant1(), createRestaurant2()));

        mockMvc.perform(get("/restaurant/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].restaurantName").value("Pizzeria Napoli"))
                .andExpect(jsonPath("$[1].restaurantName").value("Sushi House"));
    }

    @Test
    void getRestaurantById() throws Exception {
        Long testId = 1L;
        RestaurantDTO restaurantDTO = createRestaurant1();

        when(restaurantService.findRestaurantByID(testId)).thenReturn(restaurantDTO);

        mockMvc.perform(get("/restaurant/{id}", testId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurantName").value("Pizzeria Napoli"))
                .andExpect(jsonPath("$.address.city").value("Zagreb"))
                .andExpect(jsonPath("$.open").value(true));
    }

    @Test
    void getRestaurantByName() throws Exception {
        String testName = "Sushi House";
        RestaurantDTO restaurantDTO = createRestaurant2();

        when(restaurantService.findRestaurantByName(testName)).thenReturn(List.of(restaurantDTO));

        mockMvc.perform(get("/restaurant/name/{name}", testName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].restaurantName").value("Sushi House"))
                .andExpect(jsonPath("$[0].address.city").value("Zagreb"))
                .andExpect(jsonPath("$[0].open").value(false));
    }


    @Test
    void addRestaurant_successfullyAdded() throws Exception {
        RestaurantCommand command = createRestaurantCommand();
        when(restaurantService.addRestaurant(any(RestaurantCommand.class))).thenReturn(true);

        mockMvc.perform(post("/restaurant")
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Restoran uspješno dodan."));
    }

    @Test
    void addRestaurant_alreadyExists() throws Exception {
        RestaurantCommand command = createRestaurantCommand();
        when(restaurantService.addRestaurant(any(RestaurantCommand.class))).thenReturn(false);

        mockMvc.perform(post("/restaurant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(command)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Restoran s istim imenom i adresom već postoji."));
    }

    @Test
    void deleteRestaurant_success() throws Exception {
        Long testId = 1L;

        doNothing().when(restaurantService).deleteRestaurant(testId);

        mockMvc.perform(delete("/restaurant/delete/{id}", testId))
                .andExpect(status().isOk());

        verify(restaurantService).deleteRestaurant(testId);
    }

    @Test
    void updateRestaurant_success() throws Exception {
        Long testId = 1L;
        RestaurantCommand command = createRestaurantCommand();

        when(restaurantService.updateRestaurant(testId, command)).thenReturn(true);

        mockMvc.perform(put("/restaurant/update/{id}", testId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(command)))
                .andExpect(status().isOk());
    }

    @Test
    void updateRestaurant_badRequest() throws Exception {
        Long testId = 1L;
        RestaurantCommand command = createRestaurantCommand();

        when(restaurantService.updateRestaurant(testId, command)).thenReturn(false);

        mockMvc.perform(put("/restaurant/update/{id}", testId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(command)))
                .andExpect(status().isBadRequest());
    }


    private RestaurantDTO createRestaurant1() {
        Address address = new Address("Zagreb", "Ilica 1", "10000", "Hrvatska");
        return new RestaurantDTO(
                1L,
                "Pizzeria Napoli",
                true,
                address,
                0.75  // npr. 75% popunjenosti
        );
    }

    private RestaurantDTO createRestaurant2() {
        Address address = new Address("Zagreb", "Kvatrićka 12", "10000", "Hrvatska");
        return new RestaurantDTO(
                2L,
                "Sushi House",
                false,
                address,
                0.4  // npr. 40% popunjenosti
        );
    }
    private RestaurantCommand createRestaurantCommand() {
        Address address = new Address("Ilica 1", "Zagreb", "10000", "Hrvatska");
        Contact contact = new Contact("info@napoli.hr", "012345678");
        WorkingHours workingHours = new WorkingHours(1L, LocalTime.of(8, 0), LocalTime.of(22, 0));

        return new RestaurantCommand(
                "Pizzeria Napoli",
                address,
                contact,
                workingHours,
                true,
                Duration.ofMinutes(45),
                4.5,
                20,
                1,
                "Autentična talijanska pizza u srcu Zagreba"
        );
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
