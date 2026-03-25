package fr.bookswap.entity;



import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")

public class User extends PanacheEntity {

    @Column(unique = true, nullable = false)
    public String username;

    @Column(unique = true, nullable = false)
    public String email;

    @Column(nullable = false)
    public String passwordHash;

    @ElementCollection(fetch = FetchType.EAGER)
    public Set<String> roles;

    public boolean active = true;

    public LocalDateTime createdAt = LocalDateTime.now();
}