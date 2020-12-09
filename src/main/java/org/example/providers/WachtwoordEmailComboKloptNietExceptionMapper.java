package org.example.providers;

import org.example.exceptions.GebruikerBestaatAlExceptie;
import org.example.exceptions.WachtwoordEmailComboKloptNietExceptie;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WachtwoordEmailComboKloptNietExceptionMapper implements ExceptionMapper<WachtwoordEmailComboKloptNietExceptie> {

    @Override
    public Response toResponse(WachtwoordEmailComboKloptNietExceptie exception) {
        return Response.status(Response.Status.NOT_ACCEPTABLE)
                .entity("Bad request: " + exception.getMessage())
                .build();
    }
}
