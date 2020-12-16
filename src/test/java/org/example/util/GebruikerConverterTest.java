package org.example.util;

import org.example.domein.Gebruiker;
import org.example.domein.GebruikerDto;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class GebruikerConverterTest {
    Gebruiker g;

    @Before
    public void setUp() throws Exception {
        g = new Gebruiker(1L, "Aart", "Staartjes", "mail", "ww");
    }

    @Test
    public void convert() {
        GebruikerDto gd = new GebruikerConverter().convert(g);
        assertThat(gd.getId(), is(g.getId()));
        assertThat(gd.getVoornaam(), is(g.getVoornaam()));
        assertThat(gd.getAchternaam(), is(g.getAchternaam()));
        assertThat(gd.getEmail(), is(g.getEmail()));
        assertThat(gd.getWachtwoord(), is(g.getWachtwoord()));
    }
}
