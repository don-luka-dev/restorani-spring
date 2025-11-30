package hr.tvz.antolic.njamapp.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ROLES")
@Data
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

}
