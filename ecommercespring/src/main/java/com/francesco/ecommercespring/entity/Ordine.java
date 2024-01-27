package com.francesco.ecommercespring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.*;

@Entity
@Data
@Table(name = "ordine", schema = "ecommerce")
public class Ordine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    //c era jsonignore se non funzione aggiungiordine
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date datadiAcq;


    @ManyToOne
    @JoinColumn(name = "acquirente")
    private Utente buyer;//deve essere lo stesso di quello del carrello


    @OneToMany(mappedBy = "ordine", cascade = CascadeType.MERGE)
    private Collection<Prodottivendita> prodottiacquistati;

    @Null
    @JsonIgnore
    @OneToOne(mappedBy = "Ordine", optional = false)
    private Reclami Reclamo;


    public void setStato(String stato) {
        Stato = stato;
    }

    @Column(name = "stato", nullable = false)
    private String Stato;

    @JsonIgnore

    @Column(name = "metodo")
    private String metodopagamento;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ordine{");
        sb.append("id=").append(id);
        sb.append(", buyer=").append(buyer);
        sb.append(", data di acquisto=").append(datadiAcq);
        sb.append(", prodotti acquistati=").append(prodottiacquistati);
        sb.append('}');
        return sb.toString();
    }




}
