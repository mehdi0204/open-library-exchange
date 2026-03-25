package fr.bookswap.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "exchanges")
public class Exchange extends PanacheEntity {

    @ManyToOne
    public User requester;

    @ManyToOne
    public User owner;

    @ManyToOne
    public UserBook userBook;

    @Enumerated(EnumType.STRING)
    public ExchangeType type;

    @Enumerated(EnumType.STRING)
    public ExchangeStatus status = ExchangeStatus.PENDING;

    public String message;

    public LocalDateTime requestedAt = LocalDateTime.now();
    public LocalDateTime updatedAt;

    public enum ExchangeType { EXCHANGE, LOAN }
    public enum ExchangeStatus { PENDING, ACCEPTED, REFUSED }
}