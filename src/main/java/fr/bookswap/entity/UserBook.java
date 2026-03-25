package fr.bookswap.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_books")
public class UserBook extends PanacheEntity {

    @ManyToOne
    public User user;

    @ManyToOne
    public Book book;

    @Enumerated(EnumType.STRING)
    public BookStatus status;

    @Enumerated(EnumType.STRING)
    public BookCondition bookCondition;

    public boolean availableForExchange = false;
    public boolean availableForLoan = false;

    public LocalDateTime addedAt = LocalDateTime.now();

    public enum BookStatus { OWNED, WISHLIST, READ }
    public enum BookCondition { NEW, GOOD, WORN }
}