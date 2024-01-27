package com.francesco.ecommercespring.service;

import com.francesco.ecommercespring.entity.*;
import com.francesco.ecommercespring.repository.*;
import com.francesco.ecommercespring.support.Excepion.*;
import com.francesco.ecommercespring.support.authentication.Utils;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OrdineService {
    @Autowired
    private OrdineRepo ordineRepo;

    @Autowired
    private UtenteRepo utenteRepo;

    @Autowired
    private ProdottivenditaRepo prodottivenduti;
    @Autowired
    private ProdottoRepo prodottoRepo;
    @Autowired
    private CarrelloRepo carrelloRepo;


    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = {QtaUnAvaliableException.class, PriceChangedException.class})
    public void aggiungiordine() throws QtaUnAvaliableException, PriceChangedException, ProductnotfoundException {
        String username = Utils.getUsername();
        Utente c = utenteRepo.findUtenteByUsername(username);
        Ordine o = new Ordine();
        o.setBuyer(c);
        o.setDatadiAcq(new Date(System.currentTimeMillis()));
        o.setStato("Effettuato");
        o.setMetodopagamento("Contanti");
        ordineRepo.save(o);
        List<Carrello>carrelli=c.getCarrello();
        if (carrelli.isEmpty()){
            throw new ProductnotfoundException();
        }
        for (Carrello carrello : c.getCarrello()) {
            Prodotto p = prodottoRepo.findByid(carrello.getProdotto().getId());
            if (p==null) throw new ProductnotfoundException();
            if (p.getQuantita() < carrello.getQuantita()) throw new QtaUnAvaliableException(p.getId());
            if (p.getPrezzo() != carrello.getProdotto().getPrezzo()) throw new PriceChangedException(p.getId());


            Prodottivendita dto = new Prodottivendita();
            dto.setProdotto(carrello.getProdotto());
            dto.setQuantita(carrello.getQuantita());
            dto.setPrezzo(carrello.subtotale()/carrello.getQuantita());
            dto.setTotale(dto.getPrezzo() * dto.getQuantita());
            dto.setOrdine(o);
            prodottivenduti.save(dto);
            if(o.getProdottiacquistati()==null){
                o.setProdottiacquistati(new ArrayList<>());
            }
            o.getProdottiacquistati().add(dto);
            System.out.println(p.getId());
            p.setQuantita(p.getQuantita() - dto.getQuantita());
            carrelloRepo.delete(carrello);
        }
    }

    @Transactional(readOnly = true)
    public List<Ordine> getOrdiniutente(Utente utente)  {
        return ordineRepo.findOrdineByBuyer(utente);
    }

    @Transactional(readOnly = true)
    public Ordine getOrdineutente(Utente utente, Long ordineid){
        return ordineRepo.findOrdineByIdAndBuyer(ordineid,utente);
    }

    @Transactional(readOnly = true)
    public Ordine getordine( Long ordineid){
        Ordine par= ordineRepo.findOrdineById(ordineid);
        if(par.getStato().equals("Reso")){
            return par;
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<Ordine> getOrdinibyUtenteinPeriod(Utente user, Date startDate, Date endDate) throws UserNotFoundException, DateWrongRangeException {
        if ( !utenteRepo.existsById(user.getId()) ) {
            throw new UserNotFoundException();
        }
        if ( startDate.compareTo(endDate) >= 0 ) {
            throw new DateWrongRangeException();
        }
        return ordineRepo.findByBuyerInPeriod(startDate, endDate, user);
    }

    @Transactional(readOnly = true)
    public List<Ordine> getOrdiniReclamo(List<Ordine> ordini) {
        return ordineRepo.findOrdineByReclamoIsNotNull(ordini);
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRED, rollbackFor = {QtaUnAvaliableException.class, PriceChangedException.class})
    public void acquistaora(Ordine orderInput) {

        Collection<Prodottivendita> prodotti = orderInput.getProdottiacquistati();
        String username = Utils.getUsername();
        Utente c = utenteRepo.findUtenteByUsername(username);
        Ordine ordine = new Ordine();
        ordine.setBuyer(c);
        ordine.setStato("Effettuato");
        ordine.setDatadiAcq(new Date(System.currentTimeMillis()));
        ordine.setProdottiacquistati(prodotti);
        ordineRepo.save(ordine);

        for (Prodottivendita o: prodotti) {
            Prodotto product = prodottoRepo.findByid(o.getProdotto().getId());

            Prodottivendita orderDetail = new Prodottivendita();
            orderDetail.setOrdine(ordine);
            orderDetail.setPrezzo(o.getPrezzo());
            orderDetail.setProdotto(product);
            orderDetail.setQuantita(o.getQuantita());
            orderDetail.setTotale(product.getPrezzo() * o.getQuantita());
            orderDetail.setUsername(ordine.getBuyer().getNome());
            orderDetail.setIndirizzo(ordine.getBuyer().getAddress());
            product.setQuantita(product.getQuantita()-o.getQuantita());
            prodottivenduti.save(orderDetail);

}}
    @Transactional(readOnly = true)
    public List<Ordine> getallordini()  {
        return ordineRepo.findAll();
    }

    @Transactional(readOnly = false)
    public void Cambiastato(Long id)  {
        Ordine o= ordineRepo.findOrdineById(id);
        if(o!=null){
            o.setStato("Spedito");
            ordineRepo.save(o);
        }
        else {
            System.out.println("errore");
        }
    }

    @Transactional(readOnly = true)
    public List<Ordine> getOrdineutentestato(Utente utente, String stato){
        return ordineRepo.findOrdineByBuyerEqualsAndStatoEquals(utente,stato);
    }

}
