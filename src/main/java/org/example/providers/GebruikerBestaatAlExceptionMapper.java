package org.example.providers;

import org.example.exceptions.GebruikerBestaatAlExceptie;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GebruikerBestaatAlExceptionMapper implements ExceptionMapper<GebruikerBestaatAlExceptie> {

    @Override
    public Response toResponse(GebruikerBestaatAlExceptie exception) {
        return Response.status(Response.Status.FORBIDDEN)
                .entity("Bad request: " + exception.getMessage())
                .build();
    }
}
