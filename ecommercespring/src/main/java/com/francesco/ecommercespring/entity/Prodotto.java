package com.francesco.ecommercespring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "prodotto", schema = "ecommerce")
@JacksonXmlRootElement(localName = "data")
public class Prodotto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JacksonXmlProperty
    private Long id;
    @Basic
    @JacksonXmlProperty
    private String nome;
    @Basic
    @NotNull
    @Column(name = "bar_code", length = 50)
    @JacksonXmlProperty
    private String barCode;

    @Basic
    @Column(name = "descrizione", nullable = true, length = 500)
    @JacksonXmlProperty
    private String descrizione;
    @Basic
    @Column(name = "prezzo", nullable = false)
    @JacksonXmlProperty
    private float prezzo;

    @Basic
    @Column(name = "quantita", nullable = false)
    @JacksonXmlProperty
    private int quantita;

    @Basic
    @Column(name = "brend", nullable = false)
    @JacksonXmlProperty
    private String brend;


    @Version  //lock ottimistico
    @Column(name = "version", nullable = false)
    @JsonIgnore
    private long version;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "prodotto")
    @JsonIgnore
    @ToString.Exclude
    private List<Prodottivendita> prodottivendita;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "tipologia")
    @JacksonXmlProperty
    private Tipologia tipologia;


    private String genere;

    @Column(name = "taglia", nullable = false)
    private String taglia;


    private String tessuto;


    private String colore;


    private String fasciaeta;



    @JsonIgnore
    @OneToMany(mappedBy = "prodotto")
    @JacksonXmlProperty
    private Collection<Carrello> carrello;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "prodottoim", schema = "ecommerce",
         joinColumns ={
            @JoinColumn(name = "prodottoid")
    },
         inverseJoinColumns={
             @JoinColumn(name = "immagineid")
        }
    )

    private Set<Immagini> immagini;

    public Set<Immagini> getImmagini() {
        return immagini;
    }

    public void setImmagini(Set<Immagini> immagini) {
        this.immagini = immagini;
    }


    @Override
    public String toString() {
        return "Prodotto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", prezzo=" + prezzo +
                ", marca=" + brend +
                ", quantita=" + quantita +
                ", tipologia=" + tipologia +
                ", immagini=" + getImmagini() +
                '}';
    }


}
