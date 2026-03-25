package fr.bookswap.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "genres")
public class Genre extends PanacheEntity {

    @Column(unique = true, nullable = false)
    public String name;
}