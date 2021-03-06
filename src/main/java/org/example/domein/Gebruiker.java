package org.example.domein;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.interfaces.Searchable;
import org.example.util.Bezorgwijze;
import org.example.util.GebruikerConverter;
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


    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Advertentie> aangebodenAdvertenties;

    public Gebruiker(String email, String wachtwoord) {
        this.email = email;
        this.wachtwoord = wachtwoord;
    }

    public Gebruiker(long id, String voornaam, String achternaam, String email, String wachtwoord) {
        this.id = id;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.email = email;
        this.wachtwoord = wachtwoord;
    }

    public Gebruiker(String voornaam, String achternaam, String email, String wachtwoord) {

        this.voornaam = voornaam;
        this.achternaam = achternaam;
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
        a.setEigenaarAdvertentie(new GebruikerConverter().convert(this));
    }

    public void updateAdds(){
        for (Advertentie a : aangebodenAdvertenties) {
            a.setEigenaarAdvertentie(new GebruikerConverter().convert(this));
        }
    }

    public void removeAdvertentie(long id){
        for (Advertentie a : aangebodenAdvertenties) {
            if (a.getId() == id){
                aangebodenAdvertenties.remove(a);
                a.setEigenaarAdvertentie(null);
                break;
            }
        }

    }

    @Override
    public String allSearchableDataText() {
        return email + " " + wachtwoord;
    }

}
