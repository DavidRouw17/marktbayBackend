package org.example.providers;

import org.example.exceptions.GebruikerBestaatAlExceptie;
import org.example.exceptions.GeenGebruikerGevondenExceptie;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GeenGebruikerGevondenExceptionMapper implements ExceptionMapper<GeenGebruikerGevondenExceptie> {

    @Override
    public Response toResponse(GeenGebruikerGevondenExceptie exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Bad request: " + exception.getMessage())
                .build();
    }
}
