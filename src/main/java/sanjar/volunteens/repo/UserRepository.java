package sanjar.volunteens.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sanjar.volunteens.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
