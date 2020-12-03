package org.example.domein;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.interfaces.Searchable;
import org.example.util.Bezorgwijze;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name = "Gebruiker.findAll", query = "select g from Gebruiker g")
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
    private List<Bezorgwijze> bezorgwijzen;


    @OneToMany(mappedBy = "eigenaarAdvertentie",
                cascade = CascadeType.ALL,
                fetch = FetchType.EAGER)
    private List<Advertentie> aangebodenAdvertenties = new ArrayList<>();

    public Gebruiker(String email, String wachtwoord){
        this.email = email;
        this.wachtwoord = wachtwoord;
    }

    public void addBezorgwijze(Bezorgwijze b){
        if (bezorgwijzen == null){
            bezorgwijzen = new ArrayList<>();
        }
        bezorgwijzen.add(b);
    }
    

    @Override
    public String allSearchableDataText() {
        return email + " " + wachtwoord;
    }

}
