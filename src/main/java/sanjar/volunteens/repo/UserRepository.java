package sanjar.volunteens.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sanjar.volunteens.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}