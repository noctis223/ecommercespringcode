package com.francesco.ecommercespring.repository;

import com.francesco.ecommercespring.entity.Tipologia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TipologiaRepo extends JpaRepository<Tipologia,Long> {
    Tipologia findByTipo (String tipo);
    boolean existsByTipo(String tipo);
}
