// UserRepository.java
package ge.tsu.boredreader.sql_db.repository;

import ge.tsu.boredreader.sql_db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Find user by username
     * @param username the username to search for
     * @return User entity or null if not found
     */
    User findByUsername(String username);

    /**
     * Check if user exists by username
     * @param username the username to check
     * @return true if user exists, false otherwise
     */
    boolean existsByUsername(String username);
}