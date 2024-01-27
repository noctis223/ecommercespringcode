package com.francesco.ecommercespring.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Collection;
import java.util.Set;


@Entity
@Data
@Table(name = "ruoli", schema = "ecommerce")
public class Ruoli {


    @Id
    @Column(name = "nomeruolo", nullable = false)
    private String nomeruolo;
    @NotNull
    private String descrizione ;


    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "Ruolo")
    private Collection<Utente> Utenti;


}

