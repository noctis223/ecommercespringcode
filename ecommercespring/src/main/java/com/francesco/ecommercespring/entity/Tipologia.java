package com.francesco.ecommercespring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "tipologia", schema = "ecommerce")
public class Tipologia {
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    @Column(name = "id", unique = true,nullable = false)
    private Long id;
    @NotNull
    @Basic
    @Column(nullable = false,length = 50)
    private String tipo;


    @Basic
    private String descrizione;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipologia")
    private List<Prodotto>prodotti;


    @Override
    public String toString() {
        return "Tipologia{" +
                "id=" + id +
                ", nome='" + tipo + '\'' +
                '}';
    }

}
