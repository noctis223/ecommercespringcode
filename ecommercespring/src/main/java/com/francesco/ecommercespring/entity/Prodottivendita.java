package com.francesco.ecommercespring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
//SONO I DETTAGLI DELL ORDINE
@Entity
@Data
@Table(name = "prodottivendita", schema = "ecommerce")
public class Prodottivendita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "acquisto")
    @JsonIgnore
    @ToString.Exclude
    private Ordine ordine;

    @Basic
    @Column(name = "quantita", nullable = false)
    private int quantita;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "prodotto")
    private Prodotto prodotto;

    private Float totale;

    private Float prezzo;


    public Prodottivendita() {

    }

    private String username;

    private String indirizzo;
}
