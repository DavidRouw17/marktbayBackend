package org.example.resources;

import org.example.dao.GebruikerDao;
import org.example.dao.GeneriekeDao;
import org.example.domein.*;
import org.example.exceptions.GebruikerBestaatAlExceptie;
import org.example.exceptions.GeenGebruikerGevondenExceptie;
import org.example.exceptions.WachtwoordEmailComboKloptNietExceptie;
import org.example.interfaces.Searchable;
import org.example.util.Bezorgwijze;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.io.File;
import java.net.URL;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class GebruikersResourceIT {

    @ArquillianResource
    private static URL deploymentURL;

    private static String gebruikersResource;
    private static String gebruikersUri = "app/gebruikers";

    @Inject
    GebruikerDao dao;

    @Before
    public void setUp(){
        gebruikersResource = deploymentURL + gebruikersUri;
        dao.add(new Gebruiker("davidmail", "wachtwoord"));
        dao.add(new Gebruiker("henkmail", "wachtwoord"));
    }

    @After
    public void tearDown(){

    }

    @Deployment
    public static Archive<?> createDeployment(){
        WebArchive archive = ShrinkWrap.create(WebArchive.class, "GebruikersResourceIT.war")
                .addClass(MainPath.class)
                .addClass(GeneriekeResource.class)
                .addClass(GebruikersResource.class)
                .addClass(GeneriekeDao.class)
                .addClass(GebruikerDao.class)
                .addClass(Gebruiker.class)
                .addClass(Searchable.class)
                .addClass(Bezorgwijze.class)
                .addClass(Adres.class)
                .addClass(Advertentie.class)
                .addClass(GebruikerBestaatAlExceptie.class)
                .addClass(GeenGebruikerGevondenExceptie.class)
                .addClass(WachtwoordEmailComboKloptNietExceptie.class)
                .addClass(GebruikerDto.class)
                .addClass(Categorie.class)
                .addClass(InlogPoging.class)
                .addAsLibraries(jbcrypt())
                .addAsWebInfResource("persistence.xml", "classes/META-INF/persistence.xml");

        return archive;
    }

    private static File[] jbcrypt(){
        return Maven.resolver()
                .loadPomFromFile("pom.xml")
                .resolve("org.mindrot:jbcrypt")
                .withTransitivity()
                .asFile();
    }

    @Test
    public void testIfGetAllWorks(){
        Client http = ClientBuilder.newClient();
        String message = http
                .target(gebruikersResource)
                .request().get(String.class);

        assertThat(message, containsString("davidmail"));
        assertThat(message, containsString("henkmail"));
    }

    @Test
    public void testIfGetWorks(){
        Client http = ClientBuilder.newClient();
        String message = http
                .target(gebruikersResource + "/2")
                .request().get(String.class);

        assertThat(message, not(containsString("davidmail")));
        assertThat(message, containsString("henkmail"));
    }

    @Test
    public void testIfPostWorks(){
        Client http = ClientBuilder.newClient();
        Gebruiker g = new Gebruiker("hermanmail3", "ww");

        String postedContact = http
                .target(gebruikersResource)
                .request().post(Entity.entity(g, MediaType.APPLICATION_JSON), String.class);

        String message = http
                .target(gebruikersResource)
                .request().get(String.class);

        System.out.println(message);

        assertThat(message, containsString("hermanmail3"));
    }

    @Test
    public void testIfPutWorks(){
        Client http = ClientBuilder.newClient();
        Gebruiker g = dao.getById(1);
        g.setEmail("nieuwmail");
        String postedContact = http
                .target(gebruikersResource + "/" + g.getId())
                .request().put(Entity.entity(g, MediaType.APPLICATION_JSON), String.class);

        String message = http
                .target(gebruikersResource + "/1")
                .request().get(String.class);


        assertThat(message, containsString("nieuwmail"));
    }

    @Test
    public void testIfDeleteWorks(){
        Client http = ClientBuilder.newClient();
        Gebruiker g = dao.getById(2);
        String postedContact = http
                .target(gebruikersResource + "/" + g.getId())
                .request().delete(String.class);

        String message = http
                .target(gebruikersResource + "/2")
                .request().get(String.class);


        assertThat(message, nullValue());
    }

    @Test
    public void testIfLoginWorks(){
//        Client http = ClientBuilder.newClient();
//        Gebruiker g = new Gebruiker("hermanmail", "ww");
//
//        String postedContact = http
//                .target(gebruikersResource)
//                .request().post(Entity.entity(g, MediaType.APPLICATION_JSON), String.class);
//
//        String message = http
//                .target(gebruikersResource)
//                .request().get(String.class);
//
//        System.out.println(message);
//
//        assertThat(message, containsString("hermanmail"));
    }

}
