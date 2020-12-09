package org.example.resources;

import org.example.dao.GebruikerDao;
import org.example.domein.Gebruiker;
import org.example.domein.InlogPoging;
import org.example.exceptions.GebruikerBestaatAlExceptie;
import org.example.exceptions.GeenGebruikerGevondenExceptie;
import org.example.exceptions.WachtwoordEmailComboKloptNietExceptie;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/gebruikers")

@Produces(MediaType.APPLICATION_JSON)
public class GebruikersResource extends GeneriekeResource<Gebruiker> {

    @Inject
    public void setDao(GebruikerDao dao){
        super.dao = dao;
    }

    @POST
    public Gebruiker post(Gebruiker g) throws GebruikerBestaatAlExceptie {
        if (dao.add(g)) {
            return g;
        } else {
            throw new GebruikerBestaatAlExceptie();
        }
    }

    @Path("/login") @POST
    public Gebruiker inloggen(InlogPoging p) throws GeenGebruikerGevondenExceptie, WachtwoordEmailComboKloptNietExceptie {
        return dao.login(p.getEmail(), p.getWachtwoord());
    }


}
