package org.example.domein;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.util.Bezorgwijze;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Advertentie.findAll", query = "SELECT new org.example.domein.AdvertentieDto(a.id, a.titel, a.soort," +
                "a.prijs, a.omschrijving, a.eigenaarVoornaam , a.eigenaarAchternaam)  FROM Advertentie a"),
        @NamedQuery(name = "Advertentie.findBySoort", query = "SELECT a FROM Advertentie a where a.soort LIKE :soort")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Advertentie {

    @Id
    @GeneratedValue
    private Long id;

    private String titel;
    private String soort;
    private double prijs;

    private String omschrijving;

    private String eigenaarVoornaam;
    private String eigenaarAchternaam;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String bijlage;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @Enumerated(EnumType.STRING)
    private List<Bezorgwijze> bezorgwijzen;

    @Transient
    private GebruikerDto eigenaarAdvertentie;

    @ManyToOne
    private Categorie categorie;

    //constructor voor testen
    public Advertentie(String titel, String soort, double prijs, String omschrijving, List<Bezorgwijze> bezorgwijzen){
        this.titel = titel;
        this.soort = soort;
        this.prijs = prijs;
        this.omschrijving = omschrijving;
        this.bezorgwijzen = bezorgwijzen;
    }

    public void setEigenaarAdvertentie(GebruikerDto eigenaarAdvertentie) {
        this.eigenaarAdvertentie = eigenaarAdvertentie;
        this.setEigenaarVoornaam(eigenaarAdvertentie.getVoornaam());
        this.setEigenaarAchternaam(eigenaarAdvertentie.getAchternaam());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Advertentie)) return false;
        Advertentie that = (Advertentie) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
