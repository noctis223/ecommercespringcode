package com.francesco.ecommercespring.controller;

import com.francesco.ecommercespring.entity.Carrello;
import com.francesco.ecommercespring.entity.Utente;
import com.francesco.ecommercespring.service.CarrelloService;
import com.francesco.ecommercespring.service.UtenteService;
import com.francesco.ecommercespring.support.Excepion.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class CarrelloController {
    @Autowired
    private CarrelloService carrelloService;
    @Autowired
    private UtenteService utenteService;

    //@Scheduled svuota il carrello dopo un certo tempo

    // funziona
    @GetMapping("/carrello")
    @PreAuthorize("hasAnyAuthority('Utente')")
    public List<Carrello> showcarrello( @AuthenticationPrincipal Authentication authentication) throws UserNotFoundException {
        Utente utente = utenteService.getCurrentlyLoggedInUtente(authentication);
        List<Carrello> carrelli = carrelloService.listaprodotti(utente);
        StringBuilder sb = new StringBuilder();
        for (Carrello c : carrelli) {
            sb.append("id="+c.getId()).append("-").append(c.getProdotto()).append("\n");

        }
        System.out.println(sb.toString());
        return carrelli;


    }


}
//model ritorna delle informazioni all interfaccia grafica