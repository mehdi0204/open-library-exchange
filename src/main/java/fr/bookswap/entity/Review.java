package fr.bookswap.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review extends PanacheEntity {

    @ManyToOne
    public User user;

    @ManyToOne
    public Book book;

    public int rating;

    public String comment;

    public LocalDateTime createdAt = LocalDateTime.now();
}