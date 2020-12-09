package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.domein.Gebruiker;
import org.example.exceptions.GeenGebruikerGevondenExceptie;
import org.example.exceptions.WachtwoordEmailComboKloptNietExceptie;
import org.mindrot.jbcrypt.BCrypt;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class GebruikerDao extends GeneriekeDao<Gebruiker> {

    public List<Gebruiker> getByQuery(String q) {
        return getAll().stream()
                .filter(c -> c.allSearchableDataText().contains(q))
                .collect(Collectors.toList());
    }

    public Gebruiker getByEmail(String email) throws GeenGebruikerGevondenExceptie {
        TypedQuery<Gebruiker> namedQ = em.createNamedQuery("Gebruiker.zoekOpEmail", Gebruiker.class);
        namedQ.setParameter("email", email);
        Gebruiker g;
        try {
            g = namedQ.getSingleResult();
        } catch (NoResultException n) {
            throw new GeenGebruikerGevondenExceptie();
        }
        return g;
    }

    @Override
    public boolean add(Gebruiker g) {
        try {
            getByEmail(g.getEmail());
            return false;
        } catch (GeenGebruikerGevondenExceptie e) {
            g.setWachtwoord(BCrypt.hashpw(g.getWachtwoord(), BCrypt.gensalt()));
            em.persist(g);
            return true;
        }
    }

    public Gebruiker login(String email, String wachtwoord) throws WachtwoordEmailComboKloptNietExceptie, GeenGebruikerGevondenExceptie {
        Gebruiker g = getByEmail(email);
        if (!BCrypt.checkpw(wachtwoord, g.getWachtwoord())) {
            throw new WachtwoordEmailComboKloptNietExceptie();
        }
        return g;
    }
}
