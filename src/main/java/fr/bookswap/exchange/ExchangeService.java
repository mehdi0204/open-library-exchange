package fr.bookswap.exchange;

import fr.bookswap.auth.UserRepository;
import fr.bookswap.entity.Exchange;
import fr.bookswap.entity.User;
import fr.bookswap.entity.UserBook;
import fr.bookswap.exchange.dto.ExchangeRequest;
import fr.bookswap.library.LibraryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ExchangeService {

    @Inject
    ExchangeRepository exchangeRepository;

    @Inject
    LibraryRepository libraryRepository;

    @Inject
    UserRepository userRepository;

    public List<Exchange> getMyExchanges(String username, Exchange.ExchangeStatus status) {
        User user = userRepository.findByUsername(username);
        List<Exchange> exchanges = exchangeRepository.findByUser(user);

        if (status != null) {
            exchanges = exchanges.stream()
                    .filter(e -> e.status == status)
                    .toList();
        }

        return exchanges;
    }

    public Exchange getById(Long id, String username) {
        Exchange exchange = exchangeRepository.findById(id);
        if (exchange == null) {
            throw new NotFoundException("Échange introuvable");
        }
        User user = userRepository.findByUsername(username);
        if (!exchange.requester.id.equals(user.id) && !exchange.owner.id.equals(user.id)) {
            throw new ForbiddenException("Accès refusé");
        }
        return exchange;
    }

    @Transactional
    public Exchange create(ExchangeRequest request, String username) {
        User requester = userRepository.findByUsername(username);
        UserBook userBook = libraryRepository.findById(request.getUserBookId());

        if (userBook == null) {
            throw new NotFoundException("Livre introuvable dans la bibliothèque");
        }
        if (userBook.user.id.equals(requester.id)) {
            throw new BadRequestException("Vous ne pouvez pas faire une demande sur vos propres livres");
        }
        if (!userBook.availableForExchange && request.getType() == Exchange.ExchangeType.EXCHANGE) {
            throw new BadRequestException("Ce livre n'est pas disponible à l'échange");
        }
        if (!userBook.availableForLoan && request.getType() == Exchange.ExchangeType.LOAN) {
            throw new BadRequestException("Ce livre n'est pas disponible au prêt");
        }

        Exchange exchange = new Exchange();
        exchange.requester = requester;
        exchange.owner = userBook.user;
        exchange.userBook = userBook;
        exchange.type = request.getType();
        exchange.message = request.getMessage();
        exchangeRepository.persist(exchange);

        return exchange;
    }

    @Transactional
    public Exchange accept(Long id, String username) {
        Exchange exchange = findAndCheckOwner(id, username);
        exchange.status = Exchange.ExchangeStatus.ACCEPTED;
        exchange.updatedAt = LocalDateTime.now();
        return exchange;
    }

    @Transactional
    public Exchange refuse(Long id, String username) {
        Exchange exchange = findAndCheckOwner(id, username);
        exchange.status = Exchange.ExchangeStatus.REFUSED;
        exchange.updatedAt = LocalDateTime.now();
        return exchange;
    }

    private Exchange findAndCheckOwner(Long id, String username) {
        Exchange exchange = exchangeRepository.findById(id);
        if (exchange == null) {
            throw new NotFoundException("Échange introuvable");
        }
        if (!exchange.owner.username.equals(username)) {
            throw new ForbiddenException("Seul le propriétaire peut répondre à cette demande");
        }
        if (exchange.status != Exchange.ExchangeStatus.PENDING) {
            throw new BadRequestException("Cette demande a déjà été traitée");
        }
        return exchange;
    }
}