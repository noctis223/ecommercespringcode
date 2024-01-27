package com.francesco.ecommercespring.repository;


import com.francesco.ecommercespring.entity.Utente;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UtenteRepo extends JpaRepository<Utente, Long> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Utente findUtenteById(Long id);

    Utente findUtenteByUsername(String username);

    List<Utente> findByEmailIsLikeIgnoreCase(String email);

    List<Utente> findByCognomeIsLike(String cognome);

    List<Utente> findByUsernameContainingIgnoreCase(String username);

    List<Utente> findByDatacreazioneIsBefore(Date data);




}
