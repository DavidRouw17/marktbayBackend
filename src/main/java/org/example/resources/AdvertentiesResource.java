package org.example.resources;

import org.example.dao.AdvertentieDao;
import org.example.domein.Advertentie;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/advertenties")

@Produces(MediaType.APPLICATION_JSON)
public class AdvertentiesResource extends GeneriekeResource<Advertentie> {

    @Inject
    public void setDao(AdvertentieDao dao){super.dao = dao;}


}
