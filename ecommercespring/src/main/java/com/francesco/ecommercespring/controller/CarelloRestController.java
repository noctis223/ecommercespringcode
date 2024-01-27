package com.francesco.ecommercespring.controller;

import com.francesco.ecommercespring.entity.Utente;
import com.francesco.ecommercespring.service.CarrelloService;
import com.francesco.ecommercespring.service.UtenteService;
import com.francesco.ecommercespring.support.Excepion.UserNotFoundException;
import com.francesco.ecommercespring.support.ResponseMessage;
import com.francesco.ecommercespring.support.authentication.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController //sono i metodi chiamati con l http esporto una risorsa
@RequestMapping("/carrello")
public class CarelloRestController {
    @Autowired
    private CarrelloService carrelloService;
    @Autowired
    private UtenteService utenteService;

    //se è anonimo bisogna salvare sul client
    //funziona
    @GetMapping("/add/{id}/{quantita}")//inserimento siccome c è path variable riconosce l id
    public String aggiungiprodotto(@PathVariable("id")Long id,
    @PathVariable("quantita") Integer quantita,
    @AuthenticationPrincipal Authentication authentication){
        System.out.println("prodottiaggiunti"+id+"  "+quantita);
        if (authentication==null && Utils.getUsername()==""){
            System.out.println("e necessario loggarsi per poter concludere l ordine");
            System.out.println("prodotto aggiunto");
            return quantita +"di questo prodotto aggiunti al carrello";

        }
        else{
            try {
            Utente utente= utenteService.getCurrentlyLoggedInUtente(authentication);
            Integer quantitaggiunta= carrelloService.aggingiprodotto(id,quantita,utente);
            System.out.println("prodotto aggiunto");
            return quantitaggiunta +" di questo prodotto aggiunti al carrello";}
            catch (UserNotFoundException e){
                return "USERNAME_NOT_EXISTS";
            }
        }


    }
//funziona
    @CrossOrigin
    @GetMapping("/update/{id}/{quantita}")
    public ResponseEntity aggiornaprodotto(@PathVariable("id")Long id,
                                                   @PathVariable("quantita") Integer quantita,
                                                   @AuthenticationPrincipal Authentication authentication) {
        try {
            Utente utente = utenteService.getCurrentlyLoggedInUtente(authentication);
            carrelloService.aggiornacarrello(id, quantita, utente);
            System.out.println("Quantità aggiornata");
            String result="quantita aggiornata";
            return new ResponseEntity<>(result,HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("USERNAME_NOT_EXISTS");
        }
    }

//funziona
    @DeleteMapping("/delede/{id}")
    public ResponseEntity eliminaprodotto(@PathVariable("id")Long prodottoId,
                                  @AuthenticationPrincipal Authentication authentication){
        try {

        Utente utente= utenteService.getCurrentlyLoggedInUtente(authentication);
        carrelloService.eliminaprodottiutente(utente,prodottoId);
        System.out.println("prodotto eliminato");
        String result="Prodotto eliminato dal carrello";
        return new ResponseEntity<>(result,HttpStatus.OK);}
        catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("USERNAME_NOT_EXISTS");
        }

    }
}
