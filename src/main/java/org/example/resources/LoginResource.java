//package org.example.resources;
//
//import org.example.dao.GebruikerDao;
//import org.example.dao.GeneriekeDao;
//import org.example.domein.Gebruiker;
//
//import javax.inject.Inject;
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//
//@Path("/login")
//
//@Produces(MediaType.APPLICATION_JSON)
//public class LoginResource extends GeneriekeResource<Gebruiker> {
//
//    @Inject
//    public void setDao(GebruikerDao dao){
//        super.dao = dao;
//    }
//
//    @GET
//    public Gebruiker inloggen(String email, String wachtwoord){
//        return dao.login(email, wachtwoord);
//    }
//}
