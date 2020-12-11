package org.example.resources;

import org.example.dao.AdvertentieDao;
import org.example.domein.Advertentie;
import org.example.domein.AdvertentieDto;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;

@Path("/advertenties")

@Produces(MediaType.APPLICATION_JSON)
public class AdvertentiesResource extends GeneriekeResource<Advertentie> {

    @Inject
    public void setDao(AdvertentieDao dao){super.dao = dao;}

    public AdvertentieDao getDao(){
        return (AdvertentieDao) super.dao;
    }

    @GET
    public List<AdvertentieDto> getAllDto(@QueryParam("q") String q) {
        return q == null ? getDao().getAllDto() : getDao().getByQueryDto(q);
    }


}
