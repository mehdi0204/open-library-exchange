package fr.bookswap.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "books")
public class Book extends PanacheEntity {

    @Column(unique = true, nullable = false)
    public String isbn;

    @Column(nullable = false)
    public String title;

    public String description;

    public int publicationYear;

    public String coverUrl;

    @ManyToMany
    public List<Author> authors;

    @ManyToMany
    public List<Genre> genres;

    @ManyToOne
    public User createdBy;

    public LocalDateTime createdAt = LocalDateTime.now();
}