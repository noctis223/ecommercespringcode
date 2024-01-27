package com.francesco.ecommercespring.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "reclami", schema = "ecommerce")
public class Reclami {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @ManyToOne (cascade=CascadeType.MERGE)
    @JoinColumn( name = "utente")
    private Utente Utente;

    @OneToOne
    @JoinColumn(name = "ordine")
    private Ordine Ordine;


    @Basic
    @Column(name = "descrizioneproblema", nullable = true, length = 500)
    private String descrizioneproblema;


}
