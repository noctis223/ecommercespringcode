package com.francesco.ecommercespring.repository;

import com.francesco.ecommercespring.entity.Ruoli;
import com.francesco.ecommercespring.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RuoliRepo extends JpaRepository<Ruoli,String> {

    boolean existsByNomeruolo(String nomeruolo);

    @Query("select u from Utente u where u.Ruolo = ?1" )
    List<Utente> findByAllUtenti(Ruoli ruolo);

    Ruoli findRuoliByNomeruolo(String nomeruolo);
}
