package com.francesco.ecommercespring.repository;

import com.francesco.ecommercespring.entity.Prodotto;
import com.francesco.ecommercespring.entity.Tipologia;
import jakarta.persistence.Id;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface ProdottoRepo extends JpaRepository<Prodotto,Long> {

//prezzo minore di quello dato
@Query("select p from Prodotto p where (p.nome like :nome or:nome is null) AND " +
        " (p.quantita>:quantita OR:quantita is null )AND " +
        " (p.tipologia=:tipologia or:tipologia is null ) AND "+
        " (p.prezzo>=:prezzo or :prezzo is null ) AND " +
         "(p.prezzo<=:prezzomax or:prezzo is null)")
    Page<Prodotto> ricercavanzata(String nome, Integer quantita, Integer prezzo, Integer prezzomax, Tipologia tipologia, Pageable pageable);


    @Query("SELECT p FROM Prodotto p WHERE p.tipologia.id = :tipologiaId")
    Page<Prodotto> findByTipologiaEquals(Long tipologiaId,Pageable pageable);

    Prodotto findByBarCode(String brcode);

    Page<Prodotto> findByNomeIsLike(String nome, Pageable paging);


    Page<Prodotto> findByNomeContaining(String nome,Pageable paging);

    boolean existsByBarCode(String barcode);

   Optional<Prodotto> findById(Prodotto prodottoId);

   Prodotto findByid(Long prodottoId);



}