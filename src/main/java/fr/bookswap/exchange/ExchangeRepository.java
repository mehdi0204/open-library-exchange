package fr.bookswap.exchange;

import fr.bookswap.entity.Exchange;
import fr.bookswap.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ExchangeRepository implements PanacheRepository<Exchange> {

    public List<Exchange> findByUser(User user) {
        return find("requester = ?1 or owner = ?1", user).list();
    }
}