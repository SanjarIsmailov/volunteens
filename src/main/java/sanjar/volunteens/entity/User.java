package sanjar.volunteens.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    @Column(unique = true, nullable = false)
    private String email;

    private String phoneNumber;

    private String password;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Essay> essays;
}