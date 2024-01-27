package com.francesco.ecommercespring.service;


import com.francesco.ecommercespring.entity.Tipologia;
import com.francesco.ecommercespring.repository.TipologiaRepo;
import com.francesco.ecommercespring.support.Excepion.TipologiaAlredyExsistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TipologiaService {
    @Autowired  //istanzia in automatico quando è necessario
    private TipologiaRepo tipologiaRepo;
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Tipologia creanuovatipologia(Tipologia tipologia) throws TipologiaAlredyExsistException {
        if ( tipologiaRepo.existsByTipo(tipologia.getTipo()) ) {
            throw new TipologiaAlredyExsistException();
        }
        return tipologiaRepo.save(tipologia);
    }
    @Transactional(readOnly = true)
    public List<Tipologia> getTipologie(){
            return tipologiaRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Tipologia SearchTipologia(String tipo){
        return tipologiaRepo.findByTipo(tipo);
    }

}
