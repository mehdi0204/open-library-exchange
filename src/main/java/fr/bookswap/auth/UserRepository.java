package fr.bookswap.auth;

import fr.bookswap.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped

public class UserRepository implements PanacheRepository<User> {

    public User findByUsername(String username) {
      return find("username",username).firstResult();
    }

    public User findByEmail(String email) {
       return find("email",email).firstResult();
    }
}
