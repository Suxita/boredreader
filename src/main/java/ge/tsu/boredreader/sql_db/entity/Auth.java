package ge.tsu.boredreader.sql_db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Entity
@Table(name = "authorities",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "authority"})})
public class Auth {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @Setter
    @Column(name = "authority", length = 50, nullable = false)
    private String authority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    private User user;

    public Auth() {}

    public Auth(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }

    public Auth(User user, String authority) {
        this.username = user.getUsername();
        this.authority = authority;
        this.user = user;
    }

    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            this.username = user.getUsername();
        }
    }

    @Override
    public String toString() {
        return "Auth{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", authority='" + authority + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auth auth = (Auth) o;
        return Objects.equals(username, auth.username) &&
                Objects.equals(authority, auth.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, authority);
    }
}