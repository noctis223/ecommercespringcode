package com.francesco.ecommercespring.service;


import com.francesco.ecommercespring.entity.Ruoli;
import com.francesco.ecommercespring.entity.Utente;
import com.francesco.ecommercespring.repository.RuoliRepo;
import com.francesco.ecommercespring.repository.UtenteRepo;
import com.francesco.ecommercespring.support.Excepion.MailUserAlreadyExistsException;
import com.francesco.ecommercespring.support.Excepion.UserNotFoundException;
import com.francesco.ecommercespring.support.Excepion.UsernameAlreadyExistException;
import com.francesco.ecommercespring.support.authentication.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Service
public class UtenteService {
    @Autowired
    private UtenteRepo utenterepo;
    @Autowired
    private RuoliRepo ruoliRepo;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Utente registrazioneNewUtente(String username,String email,String nome,String cognome,Utente nuovo) throws MailUserAlreadyExistsException, UsernameAlreadyExistException{
        if ( utenterepo.existsByEmail(nuovo.getEmail()) ) {
            throw new MailUserAlreadyExistsException();
        }
        if ( utenterepo.existsByUsername(nuovo.getUsername()) ) {
            throw new UsernameAlreadyExistException();
        }
        Ruoli ruolo=ruoliRepo.findRuoliByNomeruolo("Utente");
        nuovo.setUsername(username);
        nuovo.setEmail(email);
        nuovo.setNome(nome);
        nuovo.setCognome(cognome);
        nuovo.setRuolo(ruolo);

     return utenterepo.save(nuovo);

    }

    @Transactional(readOnly = true, propagation = Propagation.NEVER)//se ce una transazione lancia un eccezione
    public List<Utente> getAllUtenti() {
        return utenterepo.findAll();
    }


    @Transactional(readOnly = true, propagation = Propagation.NEVER)
    public Utente getUtenteId(Long id) {
        return utenterepo.findUtenteById(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.NEVER)
    public List<Utente> getUtentemail(String email) throws UserNotFoundException {
        List<Utente> ris= utenterepo.findByEmailIsLikeIgnoreCase(email);
        if (ris.isEmpty() ) {
            throw new UserNotFoundException();
        }
        return ris;
    }



    @Transactional(readOnly = true, propagation = Propagation.NEVER)
    public List<Utente> getUtenteUsername(String username) throws UserNotFoundException {
        List<Utente> ris= utenterepo.findByUsernameContainingIgnoreCase(username);
        if (ris.isEmpty() ) {
            throw new UserNotFoundException();
        }
        return ris;
    }

    @Transactional(readOnly = true, propagation = Propagation.NEVER)
    public List<Utente> getUtentecognome(String cognome) {
        return utenterepo.findByCognomeIsLike(cognome);
    }

    @Transactional(readOnly = true)
    public List<Utente> getUtentiBefore(Date data) throws  UserNotFoundException{
        if ( utenterepo.findByDatacreazioneIsBefore(data).isEmpty() ) {
            throw new UserNotFoundException();
        }
        return utenterepo.findByDatacreazioneIsBefore(data);
    }

//mappa l utente loggato
    public Utente getCurrentlyLoggedInUtente(Authentication authentication)throws UserNotFoundException {
        String username= Utils.getUsername();
        if (! utenterepo.existsByUsername(username) ) {
            throw new UserNotFoundException();
        }
        Utente utente=utenterepo.findUtenteByUsername(username);
        return utente;
    }


    public void nuovoadmin()throws MailUserAlreadyExistsException{
        Ruoli adminrole=new Ruoli();
        adminrole.setNomeruolo("Admin");
        adminrole.setDescrizione("amministratore del sistema con tutti i privileggi");
        ruoliRepo.save(adminrole);


        Utente admin=new Utente();
        admin.setRuolo(adminrole);
        admin.setCap("89020");
        admin.setCognome("Laurendi");
        admin.setCitta("cosenza");
        admin.setNome("francesco");
        admin.setEmail("francescolaurendi22@gmail.com");
        if ( utenterepo.existsByEmail(admin.getEmail()) ) {
            throw new MailUserAlreadyExistsException();
        }
        admin.setAddress("contrada");
        admin.setUsername("francesco");
        admin.setEta(30);
        admin.setNumerotelefono("3317710646");
        utenterepo.save(admin);




    }
}
