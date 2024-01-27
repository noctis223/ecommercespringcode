package com.francesco.ecommercespring.repository;

import com.francesco.ecommercespring.entity.Ordine;
import com.francesco.ecommercespring.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrdineRepo extends JpaRepository<Ordine, Long> {
    List<Ordine> findByBuyer(Utente utente);

    @Query("select o from Ordine as o where o.datadiAcq > ?1 and o.datadiAcq < ?2 and o.buyer = ?3")
    List<Ordine> findByBuyerInPeriod(Date startDate, Date endDate, Utente utente);

    @Query("select o from Ordine o where o.Reclamo != null " )
    List<Ordine> findOrdineByReclamoIsNotNull(List<Ordine> Ordini);


    Ordine findOrdineByIdAndBuyer(Long ordineid,Utente utente);

    List<Ordine> findOrdineByBuyer(Utente utente);

    Ordine findOrdineById(Long ordineid);

    @Query("SELECT o FROM Ordine o WHERE o.buyer = :buyer AND o.Stato = :stato")
    List<Ordine> findOrdineByBuyerEqualsAndStatoEquals(@Param("buyer") Utente buyer, @Param("stato") String stato);

    @Query("SELECT o FROM Ordine o WHERE o.Stato = :stato")
    List<Ordine> findOrdineByStatoEquals(String stato);
}
