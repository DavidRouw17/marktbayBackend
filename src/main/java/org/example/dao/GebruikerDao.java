package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.domein.Gebruiker;
import org.example.domein.GebruikerDto;
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

    public GebruikerDao(){
    }

    public List<Gebruiker> getByQuery(String q) {
        return getAll().stream()
                .filter(c -> c.allSearchableDataText().contains(q))
                .collect(Collectors.toList());
    }

    public GebruikerDto getByEmail(String email) throws GeenGebruikerGevondenExceptie {
        TypedQuery<GebruikerDto> namedQ = em.createNamedQuery("Gebruiker.zoekOpEmail", GebruikerDto.class);
        namedQ.setParameter("email", email);
        GebruikerDto g;
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


    public GebruikerDto login(String email, String wachtwoord) throws WachtwoordEmailComboKloptNietExceptie, GeenGebruikerGevondenExceptie {
        GebruikerDto g = getByEmail(email);
        if (!BCrypt.checkpw(wachtwoord, g.getWachtwoord())) {
            throw new WachtwoordEmailComboKloptNietExceptie();
        }
        Gebruiker gn = super.getById(g.getId());
        g.setBezorgwijzen(gn.getBezorgwijzen());
        return g;
    }
}
