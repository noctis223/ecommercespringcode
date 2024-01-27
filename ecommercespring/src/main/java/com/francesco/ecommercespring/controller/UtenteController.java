package com.francesco.ecommercespring.controller;

import com.francesco.ecommercespring.entity.Utente;
import com.francesco.ecommercespring.service.UtenteService;
import com.francesco.ecommercespring.support.Excepion.MailUserAlreadyExistsException;
import com.francesco.ecommercespring.support.Excepion.UserNotFoundException;
import com.francesco.ecommercespring.support.Excepion.UsernameAlreadyExistException;
import com.francesco.ecommercespring.support.ResponseMessage;
import com.francesco.ecommercespring.support.authentication.Utils;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
//mancano alcuni controlli
@RestController
@CrossOrigin
@RequestMapping("/utenti")
public class UtenteController {
    @Autowired
    private UtenteService utenteService;
//funziona
    @PostMapping("/registrazioneNuovoUtente")
    public ResponseEntity registrazioNewUtente( @RequestBody @Valid Utente nuovo){
        try{
            String username= Utils.getUsername();
            String email=Utils.getEmail();
            String nome=Utils.getnome();
            String cognome=Utils.getcognome();
            Utente aggiunto = utenteService.registrazioneNewUtente(username,email,nome,cognome,nuovo);
            return new ResponseEntity(aggiunto, HttpStatus.OK);
        } catch (MailUserAlreadyExistsException e) {
            return new ResponseEntity<>(new ResponseMessage("MAIL_ALREADY_EXISTS"), HttpStatus.BAD_REQUEST);
        }
         catch (UsernameAlreadyExistException e) {
              return new ResponseEntity<>(new ResponseMessage("USERNAME_ALREADY_EXISTS"), HttpStatus.BAD_REQUEST);
    }
    }//RESPONSE ENTITY DA UNA RISPOSTA AL NOSTRO CLIENT

//funziona
    @GetMapping("/alluser")
    @PreAuthorize("hasAnyAuthority('Admin')")
    public ResponseEntity getAll()
    {
        List<Utente> utente=utenteService.getAllUtenti();
        return new ResponseEntity(utente,HttpStatus.OK);
    }
//funziona
    @CrossOrigin
    @GetMapping("/oldUser")
    @PreAuthorize("hasAnyAuthority('Admin')")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public ResponseEntity Utentianziani(@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd")  Date date ) {
        try {

            List<Utente> utente = utenteService.getUtentiBefore(date);
            return new ResponseEntity(utente, HttpStatus.OK);
        }catch (UserNotFoundException e) {
            return new ResponseEntity<>(new ResponseMessage("USERNAME_NOT_FOUND"), HttpStatus.NOT_FOUND);
        }
    }
    //funziona
    @GetMapping("/search/username")
    @PreAuthorize("hasAnyAuthority('Admin')")
    public ResponseEntity UtenteUsername(@RequestParam @Valid String username){
        try{
        List<Utente> utente=utenteService.getUtenteUsername(username);
        return new ResponseEntity(utente,HttpStatus.OK);
    }catch (UserNotFoundException e) {
        return new ResponseEntity<>(new ResponseMessage("USERNAME_NOT_EXIST"), HttpStatus.BAD_REQUEST);
    }
    }
//funziona
    @GetMapping("/search/email")
    @PreAuthorize("hasAnyAuthority('Admin')")
    public ResponseEntity Utentemail(@RequestParam @Valid String email) {
        try {
            List<Utente> utente = utenteService.getUtentemail(email);
            return new ResponseEntity(utente, HttpStatus.OK);

        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(new ResponseMessage("USERNAME_NOT_EXIST"), HttpStatus.BAD_REQUEST);

        }}


//request param si usa per la ricerca avanzata perche prende i parametri solo se ci sono

    }
