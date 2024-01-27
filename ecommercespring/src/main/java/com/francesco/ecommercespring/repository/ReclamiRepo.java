package com.francesco.ecommercespring.repository;


import com.francesco.ecommercespring.entity.Ordine;
import com.francesco.ecommercespring.entity.Reclami;
import com.francesco.ecommercespring.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReclamiRepo extends JpaRepository<Reclami, Long> {


    @Query("select r from Reclami r where r.Utente = ?1" )
    List<Reclami> findReclamiByUtente(Utente utente);


    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM Ordine o WHERE o.Reclamo IS NULL")
    boolean existsReclamo(Ordine ordine);


}