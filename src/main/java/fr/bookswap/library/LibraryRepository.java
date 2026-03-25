package fr.bookswap.library;

import fr.bookswap.entity.User;
import fr.bookswap.entity.UserBook;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class LibraryRepository implements PanacheRepository<UserBook> {

    public List<UserBook> findByUser(User user) {
        return find("user", user).list();
    }
}

