package com.francesco.ecommercespring.service;

import com.francesco.ecommercespring.entity.Ordine;
import com.francesco.ecommercespring.entity.Reclami;
import com.francesco.ecommercespring.entity.Utente;
import com.francesco.ecommercespring.repository.ReclamiRepo;
import com.francesco.ecommercespring.repository.UtenteRepo;
import com.francesco.ecommercespring.support.Excepion.ReclamoAlreadyExistsException;
import com.francesco.ecommercespring.support.Excepion.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReclamiService {
    @Autowired
    private ReclamiRepo reclamiRepo;

    @Autowired
    private UtenteRepo utenteRepo;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Reclami Creanuovoreclamo(Ordine ordine,Utente u, String descrizione) throws ReclamoAlreadyExistsException {
        if (reclamiRepo.existsReclamo(ordine) ) {
            throw new ReclamoAlreadyExistsException();
        }
        Reclami reclamo=new Reclami();
        reclamo.setOrdine(ordine);
        reclamo.setUtente(u);
        reclamo.setDescrizioneproblema(descrizione);
        return reclamiRepo.save(reclamo);

    }
    public List<Reclami> GetReclamiUtente(Utente utente) throws UserNotFoundException{
        if ( !utenteRepo.existsById(utente.getId()) ) {
            throw new UserNotFoundException();
        }
        return reclamiRepo.findReclamiByUtente(utente);
    }

    public List<Reclami> getreclami(){
       return reclamiRepo.findAll();
    }

@Transactional(propagation = Propagation.REQUIRED)
    public void eliminareclamo(Long id) {
        Optional<Reclami> r = reclamiRepo.findById(id);
        r.ifPresent(reclamiRepo::delete);
    }




}
