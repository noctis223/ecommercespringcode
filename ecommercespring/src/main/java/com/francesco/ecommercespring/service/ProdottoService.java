package com.francesco.ecommercespring.service;

import com.francesco.ecommercespring.entity.Prodotto;
import com.francesco.ecommercespring.entity.Tipologia;
import com.francesco.ecommercespring.repository.ProdottoRepo;
import com.francesco.ecommercespring.repository.TipologiaRepo;
import com.francesco.ecommercespring.support.Excepion.*;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdottoService {
    @Autowired
    private ProdottoRepo prodottoRepo;
    @Autowired
    private TipologiaRepo tipologiaRepo;

    @Transactional(readOnly = false , propagation = Propagation.REQUIRED)
    public void aggiungiprodotto(Prodotto prodotto) throws BarCodeAlreadyExistException, TipologiaNotFoundExceptions,QtaUnAvaliableException {
        if ( prodotto.getBarCode() != null && prodottoRepo.existsByBarCode(prodotto.getBarCode()) && !prodottoRepo.existsById(prodotto.getId()) ) {
            throw new BarCodeAlreadyExistException();
        }
        if(!tipologiaRepo.existsByTipo(prodotto.getTipologia().getTipo()) || prodotto.getTipologia().equals(null)){
            throw new TipologiaNotFoundExceptions();
        }
        if(prodotto.getQuantita()<=0){
            throw new QtaUnAvaliableException(prodotto.getQuantita());
        }
        prodottoRepo.save(prodotto);
    }

    @Transactional(readOnly = false , propagation = Propagation.REQUIRED)
    public void aggiungiquantita(Prodotto prodotto, Integer quantita) throws BarCodeNotfoundexception,ProductnotfoundException,QtaUnAvaliableException {
        if(prodotto== null){
            throw new BarCodeNotfoundexception();
        }
        if ( prodotto.getBarCode() == null || !prodottoRepo.existsByBarCode(prodotto.getBarCode()) ) {
            throw new BarCodeNotfoundexception();
        }
        if(prodotto.getId() ==null || prodottoRepo.findByid(prodotto.getId())==null){
            throw new ProductnotfoundException();
        }
        if(quantita<=0){
            throw new QtaUnAvaliableException(quantita);
        }
        Prodotto aggiornare=prodottoRepo.findByid(prodotto.getId());
        aggiornare.setQuantita(quantita);
        prodottoRepo.save(aggiornare);
    }

    public Tipologia cercatipologia(String nometipologia) throws TipologiaNotFoundExceptions{
        if(!tipologiaRepo.existsByTipo(nometipologia)){
            throw new TipologiaNotFoundExceptions();
        }
        Tipologia tipologia=tipologiaRepo.findByTipo(nometipologia);
        return tipologia;
    }

    @Transactional(readOnly = true)//non paginato
    public Prodotto getprodottobyid(Long id) {
        return prodottoRepo.findByid(id);
    }

    @Transactional(readOnly = true ,propagation = Propagation.NEVER)
    public List<Prodotto> showAllProducts(int pageNumber, int pageSize, String sortBy)throws NewEmpityresultException {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Prodotto> pagedResult = prodottoRepo.findAll(paging);
        if ( pagedResult.hasContent() ) {
            return pagedResult.getContent();
        }
        else {
            throw new NewEmpityresultException();

        }
    }

    @Transactional(readOnly = true)
    public List<Prodotto> showProductsByNome(String nome, int pageSize,int pageNumber,String sortBy)throws NewEmpityresultException {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Prodotto> pagedResult = prodottoRepo.findByNomeContaining(nome,paging);
        if ( pagedResult.hasContent() ) {
            return pagedResult.getContent();
        }
        else {
            throw new NewEmpityresultException();

        }
    }

    @Transactional(readOnly = true)
    public List<Prodotto> showsimilarproduct(String nome, int pageSize,int pageNumber,String sortBy)throws NewEmpityresultException {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Prodotto> pagedResult = prodottoRepo.findByNomeIsLike(nome,paging);
        if ( pagedResult.hasContent() ) {
            return pagedResult.getContent();
        }
        else {
            throw new NewEmpityresultException();

        }
    }

    @Transactional(readOnly = true)
    public Prodotto showProductsByBarCode(String barCode)throws BarCodeNotfoundexception {
        return prodottoRepo.findByBarCode(barCode);
    }

    @Transactional(readOnly = true)
    public List<Prodotto> showTipologiaprodotti(Tipologia tipologia, int pageSize,int pageNumber,String sortBy)throws NewEmpityresultException {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Prodotto> pagedResult = prodottoRepo.findByTipologiaEquals(tipologia.getId(),paging);
        if ( pagedResult.hasContent() ) {
            return pagedResult.getContent();
        }
        else {
            throw new NewEmpityresultException();

        }
    }

    @Transactional(readOnly = true)
    public List<Prodotto> Searchproduct(String nome,Integer quantita,Integer prezzo,Integer prezzomax, Tipologia tipologia,int pageNumber,int pageSize,String sortBy)throws NewEmpityresultException {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Prodotto> pagedResult = prodottoRepo.ricercavanzata(nome,quantita,prezzo,prezzomax,tipologia,paging);
        if ( pagedResult.hasContent() ) {
            return pagedResult.getContent();
        }
        else {
            throw new NewEmpityresultException();

        }
    }

    @Transactional(readOnly = false)
    public void eliminaprodotto(Long prodottoid) {
        prodottoRepo.deleteById(prodottoid);

    }
}
