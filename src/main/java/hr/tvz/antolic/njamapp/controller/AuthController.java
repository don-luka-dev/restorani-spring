package hr.tvz.antolic.njamapp.controller;


import hr.tvz.antolic.njamapp.domain.ApplicationUser;
import hr.tvz.antolic.njamapp.domain.RefreshToken;
import hr.tvz.antolic.njamapp.dto.AuthRequestDTO;
import hr.tvz.antolic.njamapp.dto.JwtResponseDTO;
import hr.tvz.antolic.njamapp.dto.RefreshTokenRequestDTO;
import hr.tvz.antolic.njamapp.dto.UserDTO;
import hr.tvz.antolic.njamapp.security.SecurityUtils;
import hr.tvz.antolic.njamapp.service.JwtService;
import hr.tvz.antolic.njamapp.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private AuthenticationManager authenticationManager;

    private JwtService jwtService;

    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public JwtResponseDTO authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
            return JwtResponseDTO.builder()
                    .accessToken(jwtService.generateToken(authRequestDTO.getUsername()))
                    .token(refreshToken.getToken())
                    .build();
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }

    @PostMapping("/refreshToken")
    public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getApplicationUser)
                .map(applicationUser -> {
                    String accessToken = jwtService.generateToken(applicationUser.getUsername());
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestDTO.getToken()).build();
                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
    }

    @PostMapping("/logout")
    public void logout() {
        System.out.println("Logout...");
    }

}
