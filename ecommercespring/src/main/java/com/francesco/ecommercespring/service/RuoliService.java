package com.francesco.ecommercespring.service;

import com.francesco.ecommercespring.entity.Ruoli;
import com.francesco.ecommercespring.entity.Utente;
import com.francesco.ecommercespring.repository.RuoliRepo;
import com.francesco.ecommercespring.support.Excepion.RoleAlredyExsistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RuoliService {
    @Autowired  //istanzia in automatico quando è necessario
    private RuoliRepo ruolorepos;
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Ruoli creanuovoruolo(Ruoli ruolo) throws RoleAlredyExsistException {
        if ( ruolorepos.existsByNomeruolo(ruolo.getNomeruolo()) ) {
            throw new RoleAlredyExsistException();
        }
        return ruolorepos.save(ruolo);
    }

    @Transactional(readOnly = true)
    public List<Utente> GetallUser(Ruoli ruolo){
        return ruolorepos.findByAllUtenti(ruolo);
    }
}
