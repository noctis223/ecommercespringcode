package com.francesco.ecommercespring.controller;

import com.francesco.ecommercespring.entity.Prodotto;
import com.francesco.ecommercespring.repository.ProdottivenditaRepo;
import com.francesco.ecommercespring.repository.ProdottoRepo;
import com.francesco.ecommercespring.repository.UtenteRepo;
import com.francesco.ecommercespring.support.authentication.Utils;
import org.aspectj.weaver.ast.Or;
import org.springframework.security.access.method.P;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.francesco.ecommercespring.entity.Ordine;
import com.francesco.ecommercespring.entity.Prodottivendita;
import com.francesco.ecommercespring.entity.Utente;
import com.francesco.ecommercespring.repository.OrdineRepo;
import com.francesco.ecommercespring.service.OrdineService;
import com.francesco.ecommercespring.service.ProdottivenditaService;
import com.francesco.ecommercespring.service.UtenteService;
import com.francesco.ecommercespring.support.Excepion.*;
import com.francesco.ecommercespring.support.ResponseMessage;
import com.francesco.ecommercespring.support.utility.OrderErrorTypes;
import com.francesco.ecommercespring.support.utility.RispostaOrdine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/ordini")
public class OrdineController {
    @Autowired
    private OrdineService ordineService;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    ProdottivenditaService prodottivenditaService;
    @Autowired
    private OrdineRepo ordineRepo;
    @Autowired
    private ProdottivenditaRepo prodottivenditaRepo;
    @Autowired
    private UtenteRepo utenteRepo;
    @Autowired
    private ProdottoRepo prodottoRepo;

    //funziona
    @CrossOrigin
    @GetMapping(value = "/ordine")
    @PreAuthorize("hasAnyAuthority('Utente')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RispostaOrdine> creaordine(@AuthenticationPrincipal Authentication authentication) throws UserNotFoundException, ProductnotfoundException {
        try {
            ordineService.aggiungiordine();
        } catch (QtaUnAvaliableException e) {
            return ResponseEntity.ok(new RispostaOrdine(e.getPid(), OrderErrorTypes.QTA));
        } catch (PriceChangedException e) {
            return ResponseEntity.ok(new RispostaOrdine(e.getPid(), OrderErrorTypes.PRICE));
        }
        return ResponseEntity.ok(new RispostaOrdine());
    }
//funziona
    @CrossOrigin
    @GetMapping("/mieiordini")
    public List<Ordine> getOrdini( @AuthenticationPrincipal Authentication authentication) throws UserNotFoundException {
        Utente utente= utenteService.getCurrentlyLoggedInUtente(authentication);
        return ordineService.getOrdiniutente(utente);
    }
//funziona
    @GetMapping("/mieiordini/{id}")
    public Ordine getOrdine( @AuthenticationPrincipal Authentication authentication,@PathVariable("id")Long ordineid) throws UserNotFoundException {
        Utente utente= utenteService.getCurrentlyLoggedInUtente(authentication);
        return ordineService.getOrdineutente(utente,ordineid);
    }
//funziona
    @CrossOrigin
    @GetMapping("/mieiordini/dettagliordine/{id}")
    public List<Prodottivendita> getdettagli( @AuthenticationPrincipal Authentication authentication,@PathVariable("id")Long ordineid) throws UserNotFoundException {
        Ordine risu=ordineRepo.findOrdineById(ordineid);
        List <Prodottivendita> ris= prodottivenditaRepo.findByOrdine(risu);
        return ris;
    }
//funziona
    @CrossOrigin
    @GetMapping("/mieiordini/perdata")
    public ResponseEntity getPurchasesInPeriod( @AuthenticationPrincipal Authentication authentication,@RequestParam("startDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date start, @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date end) {
        try {
            Utente utente = utenteService.getCurrentlyLoggedInUtente(authentication);
            List<Ordine> result = ordineService.getOrdinibyUtenteinPeriod(utente, start, end);
            if (result.size() <= 0) {
                return new ResponseEntity<>(new ResponseMessage("Nessun ordine nel perido selezionato!"), HttpStatus.OK);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L utente non esiste!", e);
        } catch (DateWrongRangeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La data di inizio non è compatibile cn i criteri selezionati!", e);
        }

    }
        @CrossOrigin
        @PostMapping("/acquistaora")
        public ResponseEntity<?> Acquistaora(@RequestBody Ordine ordineInput) {
             ordineService.acquistaora(ordineInput);
            Ordine result=ordineInput;
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

    @CrossOrigin
    @GetMapping("/allordini")
    public List<Ordine> getOrdini() {
        return ordineService.getallordini();
    }

    @CrossOrigin
    @PreAuthorize("hasAnyAuthority('Admin')")
    @GetMapping("/cambiastato/{id}")
    public void getOrdini(@PathVariable("id") Long id) {
        ordineService.Cambiastato(id);
    }

    @CrossOrigin
    @GetMapping("/mieiordini/stato")
    public List<Ordine> getOrdinistato( @AuthenticationPrincipal Authentication authentication,@RequestParam("stato")String stato) throws UserNotFoundException {
        Utente utente= utenteService.getCurrentlyLoggedInUtente(authentication);
        return ordineService.getOrdineutentestato(utente,stato);
    }

    @CrossOrigin
    @PreAuthorize("hasAnyAuthority('Admin')")
    @GetMapping("/resoid/{id}")
    public Ordine getOrdine(@PathVariable("id") Long id) {
        return ordineService.getordine(id);
    }

    //gestisce tutti i resi
    @CrossOrigin
    @PreAuthorize("hasAnyAuthority('Admin')")
    @GetMapping("/resi")
    public List<Ordine> getOrdineresi() {
        return ordineRepo.findOrdineByStatoEquals("Reso");
    }
}
