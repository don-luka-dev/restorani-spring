package hr.tvz.antolic.njamapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.tvz.antolic.njamapp.controller.AuthController;
import hr.tvz.antolic.njamapp.domain.ApplicationUser;
import hr.tvz.antolic.njamapp.domain.RefreshToken;
import hr.tvz.antolic.njamapp.dto.AuthRequestDTO;
import hr.tvz.antolic.njamapp.dto.JwtResponseDTO;
import hr.tvz.antolic.njamapp.dto.RefreshTokenRequestDTO;
import hr.tvz.antolic.njamapp.service.JwtService;
import hr.tvz.antolic.njamapp.service.RefreshTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private RefreshTokenService refreshTokenService;

    @InjectMocks
    private AuthController authController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {mockMvc = MockMvcBuilders.standaloneSetup(authController).build();}


    @Test
    void authenticateAndGetToken_success() throws Exception {
        AuthRequestDTO authRequest = new AuthRequestDTO("user", "pass");

        Authentication authenticationMock = mock(Authentication.class);
        when(authenticationMock.isAuthenticated()).thenReturn(true);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authenticationMock);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken("refresh-token-123");
        when(refreshTokenService.createRefreshToken("user")).thenReturn(refreshToken);

        when(jwtService.generateToken("user")).thenReturn("access-token-123");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("access-token-123"))
                .andExpect(jsonPath("$.token").value("refresh-token-123"));
    }

    @Test
    void authenticateAndGetToken_fail() throws Exception {
        AuthRequestDTO authRequest = new AuthRequestDTO("user", "wrongpass");

        Authentication authenticationMock = mock(Authentication.class);
        when(authenticationMock.isAuthenticated()).thenReturn(false);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authenticationMock);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void refreshToken_success() throws Exception {
        RefreshTokenRequestDTO refreshTokenRequest = new RefreshTokenRequestDTO("refresh-token-123");

        ApplicationUser appUser = new ApplicationUser();
        appUser.setUsername("user");

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken("refresh-token-123");
        refreshToken.setApplicationUser(appUser);

        when(refreshTokenService.findByToken("refresh-token-123")).thenReturn(Optional.of(refreshToken));
        when(refreshTokenService.verifyExpiration(refreshToken)).thenReturn(refreshToken);
        when(jwtService.generateToken("user")).thenReturn("new-access-token");

        mockMvc.perform(post("/auth/refreshToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(refreshTokenRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("new-access-token"))
                .andExpect(jsonPath("$.token").value("refresh-token-123"));
    }

    @Test
    void refreshToken_fail() throws Exception {
        String invalidToken = "invalid-refresh-token";
        RefreshTokenRequestDTO requestDTO = new RefreshTokenRequestDTO();
        requestDTO.setToken(invalidToken);

        when(refreshTokenService.findByToken(invalidToken)).thenReturn(Optional.empty());

        mockMvc.perform(post("/auth/refreshToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\":\"" + invalidToken + "\"}"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void logout() throws Exception {
        mockMvc.perform(post("/auth/logout"))
                .andExpect(status().isOk());
    }
}
