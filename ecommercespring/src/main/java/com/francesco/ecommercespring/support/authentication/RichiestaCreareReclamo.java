package com.francesco.ecommercespring.support.authentication;

public class RichiestaCreareReclamo {
    private Long id;
    private String descrizione;

    public RichiestaCreareReclamo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}

