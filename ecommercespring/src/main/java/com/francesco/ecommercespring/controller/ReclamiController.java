package com.francesco.ecommercespring.controller;

import com.francesco.ecommercespring.entity.Ordine;
import com.francesco.ecommercespring.entity.Prodotto;
import com.francesco.ecommercespring.entity.Reclami;
import com.francesco.ecommercespring.entity.Utente;
import com.francesco.ecommercespring.repository.OrdineRepo;
import com.francesco.ecommercespring.repository.UtenteRepo;
import com.francesco.ecommercespring.service.ReclamiService;
import com.francesco.ecommercespring.support.Excepion.ReclamoAlreadyExistsException;
import com.francesco.ecommercespring.support.ResponseMessage;
import com.francesco.ecommercespring.support.authentication.RichiestaCreareReclamo;
import com.francesco.ecommercespring.support.authentication.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/reclamo")
public class ReclamiController {
    @Autowired
    private ReclamiService reclamiService;
    @Autowired
    private OrdineRepo ordineRepo;
    @Autowired
    private UtenteRepo utenteRepo;

    //ordine a cui fa riferimento il reclamo funziona

    @CrossOrigin
    @PostMapping("/creareclamo")
    @PreAuthorize("hasAnyAuthority('Utente')")
    public ResponseEntity Creareclamo(@RequestBody RichiestaCreareReclamo richiesta) {
        Long id = richiesta.getId();
        String descrizione = richiesta.getDescrizione();
        try {
            String utente= Utils.getUsername();
            Utente u= utenteRepo.findUtenteByUsername(utente);
            Ordine ordine=ordineRepo.findOrdineById(id);
            reclamiService.Creanuovoreclamo(ordine,u,descrizione);
        }catch (ReclamoAlreadyExistsException e) {
            return new ResponseEntity<>(new ResponseMessage("E STATO GIA APERTO UN RECLAMO PER QUEST ORDINE ESISTENTE"), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/creareso")
    @PreAuthorize("hasAnyAuthority('Utente')")
    public ResponseEntity Creareclamo(@RequestBody Long id) {
            Ordine ordine=ordineRepo.findOrdineById(id);
            ordine.setStato("Reso");
            ordineRepo.save(ordine);
            return new ResponseEntity<>(id, HttpStatus.OK);
    }


    @CrossOrigin
    @GetMapping("/allreclami")
    public List<Reclami> getReclamo() {
        return reclamiService.getreclami();
    }

    @CrossOrigin
    @DeleteMapping ("/allreclami/delede/{id}")
    public void elimina(@PathVariable("id") Long id) {
        reclamiService.eliminareclamo(id);
    }

}
