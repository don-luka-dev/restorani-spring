package hr.tvz.antolic.njamapp.dto;

import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private Set<String> roles;


}
