package sanjar.volunteens.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sanjar.volunteens.entity.Program;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    List<Program> findByActiveTrue();
    List<Program> findAllByOrderByIdDesc();
}