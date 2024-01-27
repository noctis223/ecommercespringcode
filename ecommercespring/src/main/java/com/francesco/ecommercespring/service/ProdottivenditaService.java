package com.francesco.ecommercespring.service;

import com.francesco.ecommercespring.entity.Ordine;
import com.francesco.ecommercespring.entity.Prodottivendita;
import com.francesco.ecommercespring.repository.OrdineRepo;
import com.francesco.ecommercespring.repository.ProdottivenditaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdottivenditaService {
    @Autowired
    private ProdottivenditaRepo prodottivenditaRepo;
    @Autowired
    private OrdineRepo ordineRepo;

    //totale per ogni ordine
    @Transactional(readOnly = true)
    public float totale(Long ordineid) {
        Ordine ordine=ordineRepo.findOrdineById(ordineid);
        float totale=0;
        List<Prodottivendita>prodottiordine=prodottivenditaRepo.findByOrdine(ordine);
        for (Prodottivendita prop: prodottiordine){
            totale+=prop.getTotale();        }
        return totale;
    }
}