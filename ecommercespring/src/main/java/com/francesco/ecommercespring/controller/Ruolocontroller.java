package com.francesco.ecommercespring.controller;

import com.francesco.ecommercespring.entity.Ruoli;
import com.francesco.ecommercespring.entity.Utente;
import com.francesco.ecommercespring.service.RuoliService;
import com.francesco.ecommercespring.support.Excepion.RoleAlredyExsistException;
import com.francesco.ecommercespring.support.ResponseMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ruoli")
public class Ruolocontroller {
    @Autowired
    private RuoliService ruoloservice;

    //Funziona
    @PostMapping({"/creanuovoruolo"})
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity creanuvoruolo(@Valid @RequestBody Ruoli ruolo) {
        try {
            Ruoli aggiunto=ruoloservice.creanuovoruolo(ruolo);
            return new ResponseEntity(aggiunto, HttpStatus.OK);
        } catch (RoleAlredyExsistException e) {
            return new ResponseEntity<>(new ResponseMessage("ROLE_ALREADY_EXISTS"), HttpStatus.BAD_REQUEST);
        }

    }

    //funziona
    @GetMapping("/VisualizzaUtenti")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity VisualizzaUtenti(@RequestParam(value = "ruolo") Ruoli ruoli){
        List<Utente>utenti=ruoloservice.GetallUser(ruoli);
        return new ResponseEntity<>(utenti,HttpStatus.OK);

    }

}
//PRENDILO NEL BODY DELLA RICHIESTA ,QUELLO CHE C'E NEL BODY E CODIFICATO