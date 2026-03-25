package fr.bookswap.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "authors")
public class Author extends PanacheEntity {

    public String firstName;
    public String lastName;
}