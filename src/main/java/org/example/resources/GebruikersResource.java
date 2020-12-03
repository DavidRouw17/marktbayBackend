package org.example.resources;

import org.example.dao.GebruikerDao;
import org.example.domein.Gebruiker;

import javax.inject.Inject;
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
}
