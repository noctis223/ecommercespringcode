package com.francesco.ecommercespring.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity

@Data
@Table(name = "utenti", schema = "ecommerce")
public class Utente {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id",unique = true, nullable = false)
    private Long id;

    @Size(max = 30)
    private String username;

    private String nome;
    @Basic
    @Column(name = "cognome", nullable = false, length = 50)
    private String cognome;
    @Basic
    @Column(name = "telefono", nullable = false, length = 10)
    private String numerotelefono;
    @Email
    private String email;

    @Basic
    @Column(name = "address", nullable = false, length = 150)
    private String address;

    @Basic
    @Column(name = "citta", nullable = false, length = 150)
    private String citta;

    @Basic
    @Column(name = "cap", nullable = false, length = 6)
    private String cap;


    private int eta;

    @OneToMany(mappedBy = "buyer")
    @JsonIgnore
    private List<Ordine> ordini;



    @OneToMany(fetch = FetchType.LAZY, mappedBy = "Utente", cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<Reclami> Reclami;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date datacreazione;

    @OneToMany(mappedBy = "utente")
    @JsonIgnore
    private List<Carrello> carrello;


    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "ruolou")
    @JsonIgnore
    private Ruoli Ruolo;
}
