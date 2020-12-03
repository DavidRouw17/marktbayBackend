package org.example.domein;

import org.example.util.Bezorgwijze;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Advertentie.findAll", query = "SELECT a FROM Advertentie a"),
        @NamedQuery(name = "Advertentie.findBySoort", query = "SELECT a FROM Advertentie a where a.soort LIKE :soort")
})
@Inheritance(strategy = InheritanceType.JOINED)
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
    private List<Bezorgwijze> bezorgwijzen;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Gebruiker eigenaarAdvertentie;

}
