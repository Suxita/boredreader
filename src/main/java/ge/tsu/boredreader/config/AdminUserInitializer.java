package ge.tsu.boredreader.config;

import ge.tsu.boredreader.sql_db.entity.Auth;
import ge.tsu.boredreader.sql_db.entity.User;
import ge.tsu.boredreader.sql_db.repository.AuthorityRepository;
import ge.tsu.boredreader.sql_db.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AdminUserInitializer.class);
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserInitializer(UserRepository userRepository,
                                AuthorityRepository authorityRepository,
                                PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        String adminUsername = "admin";

        if (!userRepository.existsByUsername(adminUsername)) {
            logger.info("Admin user not found. Creating admin user...");

            // Create admin user
            User admin = new User();
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEnabled(true);
            userRepository.save(admin);


            logger.info("Admin user created successfully");
        } else {
            logger.info("Admin user already exists");
        }
    }
}