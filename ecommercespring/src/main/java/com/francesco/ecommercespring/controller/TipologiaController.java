package com.francesco.ecommercespring.controller;

import com.francesco.ecommercespring.entity.Ruoli;
import com.francesco.ecommercespring.entity.Tipologia;
import com.francesco.ecommercespring.entity.Utente;
import com.francesco.ecommercespring.service.TipologiaService;
import com.francesco.ecommercespring.support.Excepion.RoleAlredyExsistException;
import com.francesco.ecommercespring.support.Excepion.TipologiaAlredyExsistException;
import com.francesco.ecommercespring.support.ResponseMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/tipologie")
public class TipologiaController {

  @Autowired
  private TipologiaService tipologiaService;
//funziona
    @PostMapping({"/creanuovatipologia"})
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity creanuovatipologia(@Valid @RequestBody Tipologia tipologia) {
        try {
            Tipologia aggiunto = tipologiaService.creanuovatipologia(tipologia);
            return new ResponseEntity(aggiunto, HttpStatus.OK);
        } catch (TipologiaAlredyExsistException e) {
            return new ResponseEntity<>(new ResponseMessage("TIPOLOGIA GIA ESISTENTE"), HttpStatus.BAD_REQUEST);
        }
    }

//funziona
        @GetMapping("/alltipo")
        public ResponseEntity getAlltipo()
        {
            List<Tipologia> tipologie=tipologiaService.getTipologie();
            return new ResponseEntity(tipologie,HttpStatus.OK);
        }

    }

