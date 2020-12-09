package org.example.resources;

import org.example.dao.GeneriekeDao;
import org.example.exceptions.GebruikerBestaatAlExceptie;

import javax.ws.rs.*;
import java.util.Collection;

public abstract class GeneriekeResource<E> {

    protected GeneriekeDao<E> dao;

    @GET
    public Collection<E> getAll(@QueryParam("q") String q) {
        return q == null ? dao.getAll() : dao.getByQuery(q);
    }

    @GET @Path("{id}")
    public E get(@PathParam("id") long id) {
        return dao.getById(id);
    }

    @POST
    public E post(E e) throws GebruikerBestaatAlExceptie {
        if (dao.add(e)) {
            return e;
        } else {
            throw new RuntimeException("Post " + e + " failed.");
        }
    }

    @DELETE @Path("{id}")
    public void delete(@PathParam("id") long id) {
        if (!dao.remove(id)) {
            throw new BadRequestException("Delete with id " + id + " failed.");
        }
    }

    @PUT @Path("{id}")
    public E put(@PathParam("id") long id, E e) {
        if (dao.update(e)) {
            return e;
        } else {
            throw new RuntimeException("Update " + e + " failed.");
        }
    }
}
