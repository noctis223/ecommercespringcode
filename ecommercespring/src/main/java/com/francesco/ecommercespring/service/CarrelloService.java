package com.francesco.ecommercespring.service;

import com.francesco.ecommercespring.entity.Carrello;
import com.francesco.ecommercespring.entity.Prodotto;
import com.francesco.ecommercespring.entity.Utente;
import com.francesco.ecommercespring.repository.CarrelloRepo;
import com.francesco.ecommercespring.repository.ProdottoRepo;
import com.francesco.ecommercespring.repository.UtenteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarrelloService {
    @Autowired
    private CarrelloRepo carrelloRepo;
    @Autowired
    private ProdottoRepo prodottoRepo;
    @Autowired
    private UtenteRepo utenteRepo;

    @Transactional(readOnly = true)
    public List<Carrello> listaprodotti(Utente utente){
        return carrelloRepo.findByUtente(utente);
    }

    @Transactional(readOnly = false ,propagation = Propagation.REQUIRED)
    public Integer aggingiprodotto(Long prodottoId, Integer quantita, Utente utente) {
        Integer aggiungiquantita=quantita;
        Prodotto prodotto= prodottoRepo.findByid(prodottoId);
        Carrello carrello= carrelloRepo.findByUtenteAndProdotto(utente,prodotto);//vede se ce un utente a cui è associato
        Utente utenter= utenteRepo.findUtenteByUsername(utente.getUsername());
        if(carrello!=null){
            aggiungiquantita=carrello.getQuantita()+quantita;
            carrello.setQuantita(aggiungiquantita);
        }
        else {
            carrello=new Carrello();
            carrello.setProdotto(prodotto);
            carrello.setQuantita(aggiungiquantita);
            carrello.setUtente(utenter);
        }
        carrelloRepo.save(carrello);
        return aggiungiquantita;
    }
    @Transactional(readOnly = false ,propagation = Propagation.REQUIRED)
    public float aggiornacarrello(Long prodottoid,Integer quantita,Utente utente){
        carrelloRepo.aggiornaquantita(quantita,prodottoid,utente.getId());
        Prodotto prodotto=prodottoRepo.findByid(prodottoid);
        float subtotale=prodotto.getPrezzo()*quantita;
        return subtotale;
    }

    @Transactional(readOnly = false ,propagation = Propagation.REQUIRED)
    public void eliminaprodottiutente(Utente utente, Long prodottoid){
        carrelloRepo.eliminaprodottiutente(utente.getId(),prodottoid);

    }

    public Integer aggingiprodottoclient(Long prodottoId, Integer quantita) {
        return null;
    }
}
