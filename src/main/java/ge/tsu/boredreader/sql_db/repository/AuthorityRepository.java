// AuthorityRepository.java
package ge.tsu.boredreader.sql_db.repository;

import ge.tsu.boredreader.sql_db.entity.Auth;
import ge.tsu.boredreader.sql_db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuthorityRepository extends JpaRepository<Auth, Long> {

    /**
     * Find all authorities for a specific user
     * @param user the user to find authorities for
     * @return list of authorities
     */
    List<Auth> findByUser(User user);

    /**
     * Find authority by user and authority string
     * @param user the user
     * @param authority the authority string
     * @return Auth entity or null if not found
     */
    Auth findByUserAndAuthority(User user, String authority);

    /**
     * Delete all authorities for a specific user
     * @param user the user whose authorities to delete
     */
    void deleteByUser(User user);
}