package org.example.resources;

import org.example.dao.GebruikerDao;
import org.example.domein.Advertentie;
import org.example.domein.Gebruiker;
import org.example.domein.GebruikerDto;
import org.example.domein.InlogPoging;
import org.example.exceptions.GebruikerBestaatAlExceptie;
import org.example.exceptions.GeenGebruikerGevondenExceptie;
import org.example.exceptions.WachtwoordEmailComboKloptNietExceptie;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;

@Path("/gebruikers")

@Produces(MediaType.APPLICATION_JSON)
public class GebruikersResource extends GeneriekeResource<Gebruiker> {



    @Inject
    public void setDao(GebruikerDao dao) {
        super.dao = dao;
    }

    public GebruikerDao getDao(){
        return (GebruikerDao) super.dao;
    }


    @POST
    public Gebruiker post(Gebruiker g) throws GebruikerBestaatAlExceptie {
        if (dao.add(g)) {
            return g;
        } else {
            throw new GebruikerBestaatAlExceptie();
        }
    }

    @Path("/login")
    @POST
    public GebruikerDto inloggen(InlogPoging p) throws GeenGebruikerGevondenExceptie, WachtwoordEmailComboKloptNietExceptie {

        return getDao().login(p.getEmail(), p.getWachtwoord());
    }

    @Path("{id}/advertenties")
    @POST
    public void addAdvertentie(@PathParam("id") long id, Advertentie a) {
        Gebruiker g = dao.getById(id);
        g.addAdvertentie(a);
        dao.update(g);
    }

    @Path("{id}/advertenties")
    @GET
    public List<Advertentie> addAdvertentie(@PathParam("id") long id) {
        Gebruiker g = dao.getById(id);
        return g.getAangebodenAdvertenties();
    }


}
