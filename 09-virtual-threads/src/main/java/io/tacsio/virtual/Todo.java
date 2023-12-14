package io.tacsio.virtual;

import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Todo extends PanacheEntityBase {
    
    @Id
    @GeneratedValue
    public UUID uuid;

    @NotBlank
    public String title;
}
