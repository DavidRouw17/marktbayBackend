package org.example.domein;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.util.Bezorgwijze;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Advertentie.findAll", query = "SELECT a FROM Advertentie a"),
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

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String bijlage;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Bezorgwijze> bezorgwijzen;

    @ManyToOne
    private Gebruiker eigenaarAdvertentie;

    @ManyToOne
    private Categorie categorie;



}
