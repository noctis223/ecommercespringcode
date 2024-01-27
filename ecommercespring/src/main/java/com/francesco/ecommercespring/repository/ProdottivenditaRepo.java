package com.francesco.ecommercespring.repository;

import com.francesco.ecommercespring.entity.Ordine;
import com.francesco.ecommercespring.entity.Prodottivendita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdottivenditaRepo extends JpaRepository<Prodottivendita,Long> {
    List<Prodottivendita> findByTotaleGreaterThan(float totale);

    List<Prodottivendita> findByOrdine(Ordine ordine);
}
