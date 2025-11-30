package hr.tvz.antolic.njamapp.controller;

import hr.tvz.antolic.njamapp.dto.UserDTO;
import hr.tvz.antolic.njamapp.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserDetailsServiceImpl userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {mockMvc = MockMvcBuilders.standaloneSetup(userController).build();}


    @Test
    void getCurrentUser_returnsUserDTO() throws Exception {

        UserDTO dummyUser = new UserDTO();
        dummyUser.setFirstName("test");
        dummyUser.setLastName("user");

        when(userService.getCurrentUser()).thenReturn(dummyUser);

        mockMvc.perform(get("/api/user/current-user")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("test"))
                .andExpect(jsonPath("$.lastName").value("user"));
    }
}
