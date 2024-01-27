package com.francesco.ecommercespring.controller;

import com.francesco.ecommercespring.entity.Ordine;
import com.francesco.ecommercespring.entity.Prodottivendita;
import com.francesco.ecommercespring.repository.OrdineRepo;
import com.francesco.ecommercespring.repository.ProdottivenditaRepo;
import com.francesco.ecommercespring.service.ProdottivenditaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasAuthority('Utente')")
@RequestMapping(value = "/ordini/{id}/")
public class ProdottivenditaController {
    @Autowired
    ProdottivenditaService prodottivenditaService;
    @Autowired
    private ProdottivenditaRepo prodottivenditaRepo;
    @Autowired
    private OrdineRepo ordineRepo;

    @GetMapping("/totale")
    public float Totale( @PathVariable(name = "ordine") Long ordine){
        float totale=prodottivenditaService.totale(ordine);

       return totale;

    }
    @GetMapping("/dettagli")
    public List<Prodottivendita> Dettagli(@PathVariable(name = "ordine") Long ordine){
        Ordine o= ordineRepo.findOrdineById(ordine);
        List<Prodottivendita> ris= prodottivenditaRepo.findByOrdine(o);
        return ris;
    }
}
