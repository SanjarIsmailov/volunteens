package sanjar.volunteens.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sanjar.volunteens.entity.Essay;
import sanjar.volunteens.entity.User;

import java.util.List;

public interface EssayRepository extends JpaRepository<Essay, Long> {
    List<Essay> findByAuthor(User author);
}