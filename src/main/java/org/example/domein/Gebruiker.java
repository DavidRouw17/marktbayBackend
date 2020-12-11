package org.example.domein;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.interfaces.Searchable;
import org.example.util.Bezorgwijze;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Gebruiker.findAll", query = "select new org.example.domein.GebruikerDto(g.id, g.voornaam, g.achternaam, g.email, g.wachtwoord) from Gebruiker g"),
        @NamedQuery(name = "Gebruiker.zoekOpEmail", query = "select new org.example.domein.GebruikerDto(g.id, g.voornaam, g.achternaam, g.email, g.wachtwoord) from Gebruiker g where g.email like :email")})
public class Gebruiker implements Searchable {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String wachtwoord;
    private String voornaam;
    private String achternaam;

    @OneToOne(cascade = CascadeType.ALL)
    private Adres adres;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @Enumerated(EnumType.STRING)
    private List<Bezorgwijze> bezorgwijzen;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eigenaarAdvertentie")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Advertentie> aangebodenAdvertenties;

    public Gebruiker(String email, String wachtwoord) {
        this.email = email;
        this.wachtwoord = wachtwoord;
    }


    public void addBezorgwijze(Bezorgwijze b) {
        bezorgwijzen.add(b);
    }

    public void addAdvertentie(Advertentie a){
        if(aangebodenAdvertenties == null){
            aangebodenAdvertenties = new ArrayList<>();
        }
        aangebodenAdvertenties.add(a);
        a.setEigenaarAdvertentie(this);
    }

    @Override
    public String allSearchableDataText() {
        return email + " " + wachtwoord;
    }

}
