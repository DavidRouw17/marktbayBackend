package org.example.resources;

import org.example.dao.AdvertentieDao;
import org.example.dao.GebruikerDao;
import org.example.domein.*;
import org.example.exceptions.GebruikerBestaatAlExceptie;
import org.example.exceptions.GeenGebruikerGevondenExceptie;
import org.example.exceptions.WachtwoordEmailComboKloptNietExceptie;
import org.example.util.AdvertentieConverter;
import org.example.util.Bezorgwijze;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Path("/gebruikers")

@Produces(MediaType.APPLICATION_JSON)
public class GebruikersResource extends GeneriekeResource<Gebruiker> {


    @Inject
    public void setDao(GebruikerDao dao) {
        super.dao = dao;
    }

    public GebruikerDao getDao() {
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
        if (a.getSoort().equals("Dienst")) {
            a.setBezorgwijzen(new ArrayList<Bezorgwijze>(Arrays.asList(Bezorgwijze.DIENST)));
        }
        if (a.getBezorgwijzen() == null) {
            a.setBezorgwijzen(g.getBezorgwijzen());
        }
        g.addAdvertentie(a);
        dao.update(g);
    }

    @Path("{id}/advertenties")
    @GET
    public List<AdvertentieDto> addAdvertentie(@PathParam("id") long id) {
        return dao.getById(id).getAangebodenAdvertenties().stream()
                .map(a -> new AdvertentieConverter().convert(a))
                .collect(Collectors.toList());
    }

    @Path("{id}/advertenties/{adid}")
    @DELETE
    public void deleteAdvertentie(@PathParam("id") long id,
                                  @PathParam("adid") long adid) {
        System.out.println("delete advertentie aangeroepen!");
        Gebruiker g = dao.getById(id);
        g.removeAdvertentie(adid);
        dao.update(g);

    }


}
