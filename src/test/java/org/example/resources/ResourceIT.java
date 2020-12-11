package org.example.resources;

import org.example.MainPath;
import org.example.dao.GebruikerDao;
import org.example.domein.Advertentie;
import org.example.domein.Gebruiker;
import org.example.domein.InlogPoging;
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
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Arquillian.class)
public class ResourceIT {

    @ArquillianResource
    private static URL deploymentURL;

    private static String gebruikersResource;
    private static String gebruikersUri = "app/gebruikers";

    @Inject
    GebruikerDao dao;

    @Before
    public void setUp() {
        gebruikersResource = deploymentURL + gebruikersUri;
        dao.add(new Gebruiker("davidmail", "wachtwoord"));
        dao.add(new Gebruiker("henkmail", "wachtwoord"));
    }

    @After
    public void tearDown() {

    }

    @Deployment
    public static Archive<?> createDeployment() {
        WebArchive archive = ShrinkWrap.create(WebArchive.class, "GebruikersResourceIT.war")
                .addPackages(true, MainPath.class.getPackage())
                .addAsLibraries(jbcrypt())
                .addAsWebInfResource("persistence.xml", "classes/META-INF/persistence.xml");

        return archive;
    }

    private static File[] jbcrypt() {
        return Maven.resolver()
                .loadPomFromFile("pom.xml")
                .resolve("org.mindrot:jbcrypt")
                .withTransitivity()
                .asFile();
    }

    @Test
    public void testIfGetAllWorks() {
        Client http = ClientBuilder.newClient();
        String message = http
                .target(gebruikersResource)
                .request().get(String.class);
        System.out.println(message);
        assertThat(message, containsString("davidmail"));
        assertThat(message, containsString("henkmail"));
    }

    @Test
    public void testIfGetWorks() {
        Client http = ClientBuilder.newClient();
        String message = http
                .target(gebruikersResource + "/2")
                .request().get(String.class);

        assertThat(message, not(containsString("davidmail")));
        assertThat(message, containsString("henkmail"));
    }

    @Test
    public void testIfPostWorks() {
        Client http = ClientBuilder.newClient();
        Gebruiker g = new Gebruiker("hermanmail3", "ww");

        String postedContact = http
                .target(gebruikersResource)
                .request().post(Entity.entity(g, MediaType.APPLICATION_JSON), String.class);

        String message = http
                .target(gebruikersResource)
                .request().get(String.class);


        assertThat(message, containsString("hermanmail3"));
    }

    @Test
    public void testIfPostDoesNotWorkWhenEmailAlreadyExists() {
        Client http = ClientBuilder.newClient();
        Gebruiker g = new Gebruiker("henkmail", "ww");

        Response post = http.target(gebruikersResource)
                .request().post(Entity.entity(g, MediaType.APPLICATION_JSON));
        assertThat(post.getStatus(), is(Response.Status.FORBIDDEN.getStatusCode()));
    }

    @Test
    public void testIfPutWorks() {
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
    public void testIfDeleteWorks() {
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
    public void testIfLoginWorks() {
        Client http = ClientBuilder.newClient();
        InlogPoging il = new InlogPoging("henkmail", "wachtwoord");

        String login = http
                .target(gebruikersResource + "/login")
                .request().post(Entity.entity(il, MediaType.APPLICATION_JSON), String.class);


        assertThat(login, containsString("henkmail"));
    }

    @Test
    public void testIfLoginDoesNotWorkWithWrongPassword() {
        Client http = ClientBuilder.newClient();
        InlogPoging il = new InlogPoging("henkmail", "w8woord");

        Response r = http
                .target(gebruikersResource + "/login")
                .request().post(Entity.entity(il, MediaType.APPLICATION_JSON));

        assertThat(r.getStatus(), is(Response.Status.NOT_ACCEPTABLE.getStatusCode()));
    }

    @Test
    public void testIfLoginDoesNotWorkWithUnknownEmail() {
        Client http = ClientBuilder.newClient();
        InlogPoging il = new InlogPoging("henkmail2", "w8woord");

        Response r = http
                .target(gebruikersResource + "/login")
                .request().post(Entity.entity(il, MediaType.APPLICATION_JSON));

        assertThat(r.getStatus(), is(Response.Status.NOT_FOUND.getStatusCode()));
    }

    @Test
    public void testIfAdvertentieCanBeAddedToUser(){
        Client http = ClientBuilder.newClient();
        Advertentie a = new Advertentie("Fiets", "Product", 12, "Mooie fiets", new ArrayList<Bezorgwijze>(Arrays.asList(Bezorgwijze.AFHALEN)));

        Response r = http.target(gebruikersResource + "/1/advertenties")
                .request().post(Entity.entity(a, MediaType.APPLICATION_JSON));

        Gebruiker g = dao.getById(1L);

        Advertentie aNieuw = g.getAangebodenAdvertenties().get(0);
        assertThat(a.getTitel(), is(aNieuw.getTitel()));
        assertThat(aNieuw.getEigenaarAdvertentie().getId(), is(1L));
    }
}
