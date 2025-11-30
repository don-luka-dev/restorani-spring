package hr.tvz.antolic.njamapp.service;

import hr.tvz.antolic.njamapp.domain.ApplicationUser;
import hr.tvz.antolic.njamapp.domain.UserRole;
import hr.tvz.antolic.njamapp.dto.UserDTO;
import hr.tvz.antolic.njamapp.repository.UserRepository;
import hr.tvz.antolic.njamapp.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = this.repository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("Unknown user " + username);
        }

        List<UserRole> userRoleList = user.getRoles();

        List<SimpleGrantedAuthority> authorities = userRoleList.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();

    }

    public UserDTO getCurrentUser() {
        String username = SecurityUtils.getCurrentUsername()
                .orElseThrow(() -> new RuntimeException("User not authenticated"));

        ApplicationUser user = repository.findByUsername(username);
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roles(user.getRoles().stream().map(UserRole::getName).collect(Collectors.toSet()))
                .build();
    }
}
