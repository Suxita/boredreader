package ge.tsu.boredreader.Service;

import ge.tsu.boredreader.sql_db.entity.Auth;
import ge.tsu.boredreader.sql_db.entity.User;
import ge.tsu.boredreader.sql_db.repository.AuthorityRepository;
import ge.tsu.boredreader.sql_db.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       AuthorityRepository authorityRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean existsUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional
    public User register(String username, String rawPassword) {
        if (existsUsername(username)) {
            throw new RuntimeException("Username already exists: " + username);
        }

        try {
            String encodedPassword = passwordEncoder.encode(rawPassword);

            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(encodedPassword);
            newUser.setEnabled(true);

            User savedUser = userRepository.save(newUser);

            Auth authority = new Auth();
            authority.setUsername(savedUser.getUsername());
            authority.setAuthority("ROLE_USER");
            authorityRepository.save(authority);

            return savedUser;
        } catch (Exception e) {
            throw new RuntimeException("Failed to register user: " + e.getMessage(), e);
        }
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}