package com.francesco.ecommercespring.controller;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.francesco.ecommercespring.entity.Immagini;
import com.francesco.ecommercespring.repository.ImmaginiRepository;
import com.francesco.ecommercespring.entity.Prodotto;
import com.francesco.ecommercespring.entity.Tipologia;
import com.francesco.ecommercespring.repository.ProdottoRepo;
import com.francesco.ecommercespring.repository.TipologiaRepo;
import com.francesco.ecommercespring.service.ProdottoService;
import com.francesco.ecommercespring.support.Excepion.*;
import com.francesco.ecommercespring.support.ResponseMessage;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/prodotti")
public class ProdottoController {
    @Autowired
    private ProdottoService prodottoService;
    @Autowired
    private ProdottoRepo prodottoRepo;
    @Autowired
    private ImmaginiRepository immaginiRepository;
    @Autowired
    private TipologiaRepo tipologiaRepo;


    //funziona
    @PostMapping(value = "/aggiungiprodotto", consumes ={MediaType.MULTIPART_FORM_DATA_VALUE} )
    @PreAuthorize("hasAnyAuthority('Admin')")
    public ResponseEntity Aggiungiprodotti(@RequestPart("prodotto") Prodotto prodotto, @RequestPart(value = "immagini", required = false)MultipartFile[] file ){
        try {
            if(file!=null){
            Set<Immagini> immagini= uploadimmagine(file);
            prodotto.setImmagini(immagini);}
            Tipologia tipo=prodottoService.cercatipologia(prodotto.getTipologia().getTipo());
            prodotto.setTipologia(tipo);
            prodottoService.aggiungiprodotto(prodotto);
        }catch (BarCodeAlreadyExistException e) {
            return new ResponseEntity<>(new ResponseMessage("ERRORE PRODOTTO GIA ESISTENTE"), HttpStatus.CONFLICT);
        }catch (TipologiaNotFoundExceptions e){
            return new ResponseEntity<>(new ResponseMessage("TIPOLOGIA INESISTENTE"), HttpStatus.CONFLICT);
        }catch (Exception e){
            System.out.println("errore");
            return new ResponseEntity<>(new ResponseMessage("ERRORE NEL CARRICAMENTO DELLE IMMAGINI"), HttpStatus.NOT_FOUND);

        }

        Prodotto result=prodotto;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public Set<Immagini> uploadimmagine(MultipartFile[] immagini) throws IOException {
        Set<Immagini> immaginimodel=new HashSet<>();
        for(MultipartFile file : immagini){
            Immagini im= new Immagini(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );
            im.setName(file.getOriginalFilename());
            im.setType(file.getContentType());
            im.setSize(file.getBytes());
            //immaginiRepository.save(im);
            System.out.println(file.getOriginalFilename());
            System.out.println(file.getBytes());
            System.out.println(file.getContentType());
            immaginimodel.add(im);
        }
        return immaginimodel;
    }
  //funziona ma non visualizza la nuova quantità anche se salvata
    @CrossOrigin
    @PutMapping("/aggingiquantita")
    @PreAuthorize("hasAnyAuthority('Admin')")
    public ResponseEntity Aggiungiquantita(@RequestParam String brcode,@RequestParam Integer quantita){
        try {
            Prodotto product= prodottoRepo.findByBarCode(brcode);
            if (product == null){
                throw new BarCodeNotfoundexception();
            }
            if(quantita<=0){
                throw new QtaUnAvaliableException(quantita);
            }
            prodottoService.aggiungiquantita(product,quantita);
            Prodotto result=prodottoRepo.findByBarCode(brcode);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (BarCodeNotfoundexception | ProductnotfoundException | QtaUnAvaliableException e) {
            return new ResponseEntity<>(new ResponseMessage("ERRORE PRODOTTO NON ESISTENTE"), HttpStatus.NO_CONTENT);
        }

    }



//funziona
    @CrossOrigin
    @GetMapping("/showall")
    public ResponseEntity ShowAll(@RequestParam(value = "pageSize", defaultValue = "10") int pageSize, @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber, @RequestParam(value = "sortBy", defaultValue = "id") String sortBy) {
        try {
            List<Prodotto> result = prodottoService.showAllProducts(pageNumber, pageSize, sortBy);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NewEmpityresultException e) {
            return new ResponseEntity<>(new ResponseMessage("ERROR_NO PRODUCT"), HttpStatus.NO_CONTENT);
        }
    }

    //funziona tuttavia come in amazon bisogna scrivere di meno e poi mi da i prodotti con il nome ugugale o pi lungo
    @CrossOrigin
    @GetMapping("/search/byname")//il costruttore di paging vuole prima la size della pagina
    public ResponseEntity Showproductbyname(@RequestParam(value = "nome") String nome,@RequestParam(value = "pageSize", defaultValue = "2") int pageNumber, @RequestParam(value = "pageNumber", defaultValue = "0") int pageSize, @RequestParam(value = "sortBy", defaultValue = "id") String sortBy) {
        try {
            List<Prodotto> result = prodottoService.showProductsByNome(nome, pageNumber, pageSize, sortBy);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (NewEmpityresultException e){
            return new ResponseEntity<>(new ResponseMessage("ERROR_NO PRODUCT"), HttpStatus.NO_CONTENT);
        }
    }
//funziona e vede la nuova quantita
    @CrossOrigin
    @GetMapping("/search/byBrcode")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity ShowproductbyCode(@RequestParam(value = "brcode") String brcode) {
        try {
            Prodotto result = prodottoService.showProductsByBarCode(brcode);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (BarCodeNotfoundexception e){
            return new ResponseEntity<>(new ResponseMessage("ERROR_PRODOTTO NON REGISTRATO"), HttpStatus.NO_CONTENT);
        }
    }

    //funziona
    @GetMapping("/search/byTipologia")
    public ResponseEntity ShowproductbyTipologia(@RequestParam(value = "tipologia") String tipologia, @RequestParam(value = "pageNumber", defaultValue = "0") int pageSize,@RequestParam(value = "pageSize", defaultValue = "2") int pageNumber, @RequestParam(value = "sortBy", defaultValue = "id") String sortBy) {
        try {
            Tipologia tipo=tipologiaRepo.findByTipo(tipologia);
            List<Prodotto> result = prodottoService.showTipologiaprodotti(tipo, pageNumber, pageSize, sortBy);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (NewEmpityresultException e){
            return new ResponseEntity<>(new ResponseMessage("ERROR_NO PRODUCT"), HttpStatus.NO_CONTENT);
        }
    }
    //funziona ma se si mette il prezzo minimo bisogna mettere anche il massimo
    @GetMapping("/search/advance")
    public ResponseEntity Showproductadvance(@RequestParam(value = "nome",required = false) String nome,@RequestParam(value = "quantita",required = false)Integer quantita,@RequestParam(value = "prezzomin",required = false)Integer prezzo, @RequestParam(value = "prezzomax",required = false) Integer prezzomax,@RequestParam(value = "tipologia",required = false) Tipologia tipologia,@RequestParam(value = "pageSize", defaultValue = "2") int pageSize,@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber, @RequestParam(value = "sortBy", defaultValue = "id") String sortBy) {
        try {
           List <Prodotto> result = prodottoService.Searchproduct(nome, quantita,prezzo, prezzomax, tipologia,pageNumber,pageSize,sortBy);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (NewEmpityresultException e){
            return new ResponseEntity<>(new ResponseMessage("ERROR_PRODOTTO NON REGISTRATO"), HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping({"/eliminaprodotto/{id}"})
    public void eliminaprodotto(@PathVariable("id") Long id) {
        prodottoService.eliminaprodotto(id);
    }

    @GetMapping({"/getprodottobyid/{id}"})
    public Prodotto getprodottobyid(@PathVariable("id") Long id){
        return prodottoService.getprodottobyid(id);
    }
    @GetMapping("/esporta")
    public ResponseEntity exportData(HttpServletResponse response) throws IOException {
        try {
        List<Prodotto> dataList =prodottoService.showAllProducts(0,10,"id");
        XmlMapper xmlMapper = new XmlMapper();
        String xmlData = xmlMapper.writeValueAsString(dataList);
        response.setContentType("application/xml");
        response.setHeader("Content-Disposition", "attachment; filename=data.xml");

            try (OutputStream out = response.getOutputStream()) {
                out.write(xmlData.getBytes());
                out.flush();
            }
        return new ResponseEntity<>(dataList,HttpStatus.OK);
        }catch (NewEmpityresultException e){
            return new ResponseEntity<>(new ResponseMessage("ERROR_NO PRODUCT"), HttpStatus.NO_CONTENT);
        }
    }


}
