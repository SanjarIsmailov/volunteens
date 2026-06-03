package sanjar.volunteens.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sanjar.volunteens.entity.User;
import sanjar.volunteens.repo.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.jpa.hibernate.ddl-auto:update}")
    private String ddlAuto;

    public DataInitializer(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!ddlAuto.equalsIgnoreCase("create")
                && !ddlAuto.equalsIgnoreCase("create-drop")) {
            return;
        }

        if (userRepository.count() > 0) {
            return;
        }

        User admin = new User();
        admin.setName("Sanjar");
        admin.setSurname("Ismailov");
        admin.setEmail("mrsanjarismailov@gmail.com");
        admin.setPhoneNumber("+998935592299");
        admin.setAdmin(true);
        admin.setPassword(passwordEncoder.encode("iht2026"));
        userRepository.save(admin);

        User user = new User();
        user.setName("Ulug'bek");
        user.setSurname("Mirzarustamov");
        user.setEmail("umirzarustamov@gmail.com");
        user.setPhoneNumber("+998999777682");
        user.setAdmin(true);
        user.setPassword(passwordEncoder.encode("iht2026"));
        userRepository.save(user);

        System.out.println("Default users created.");
    }
}