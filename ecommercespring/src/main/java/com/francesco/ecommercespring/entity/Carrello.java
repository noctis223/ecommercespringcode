package com.francesco.ecommercespring.entity;

import jakarta.persistence.*;
import lombok.Data;


//ho tanti "carrelli tanti quanti sono i prodotti"
@Entity
@Data
@Table(name = "carrello", schema = "ecommerce")
public class Carrello {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH, optional = false)//riferimento al prodotto nel carrello
    @JoinColumn(name = "prodotto_id")//mettendo refresh se cambia il prezzo cambiera pure nel carrello
    private Prodotto prodotto;

    @ManyToOne(cascade = CascadeType.MERGE, optional = false)//utente a cui è associato l ordine
    @JoinColumn(name = "utente_id")
    private Utente utente;

    private int quantita;

    @Transient
    public float subtotale(){
        return this.prodotto.getPrezzo()*quantita;
    }



}

