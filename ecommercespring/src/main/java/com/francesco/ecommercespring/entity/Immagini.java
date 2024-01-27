package com.francesco.ecommercespring.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Set;

@Data
@Entity
@Table(name = "immagini",schema = "ecommerce")
public class Immagini {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    private String name;

    private String type;

    @Column(length = 500000000)
    private byte[] size;

    public Immagini(String originalFilename, String contentType, byte[] bytes) {
    }


    public Immagini() {

    }



}
