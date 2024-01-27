package com.francesco.ecommercespring.repository;

import com.francesco.ecommercespring.entity.Carrello;
import com.francesco.ecommercespring.entity.Prodotto;
import com.francesco.ecommercespring.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarrelloRepo extends JpaRepository<Carrello,Long> {

    List<Carrello> findByUtente(Utente utente);//abbiamo tutti i prodotti del carrello

    Carrello findByUtenteAndProdotto(Utente utente, Prodotto prodotto);

    @Query("update Carrello c SET c.quantita = ?1 where c.prodotto.id=?2 "+ "and c.utente.id=?3")
    @Modifying
    void aggiornaquantita(Integer quantita, Long prodottoid, long utenteid);

    @Query("delete from Carrello c where c.utente.id=?1"+" and c.prodotto.id=?2")
    @Modifying
    void eliminaprodottiutente(long utenteid, long prodottoid);
}
