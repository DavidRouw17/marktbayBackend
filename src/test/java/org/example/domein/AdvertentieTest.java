package org.example.domein;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class AdvertentieTest {

    Advertentie a;

    @Before
    public void setUp(){
        a = new Advertentie("Fiets", "Product", 12, "Mooie Fiets", null);
    }

    @Test
    public void setEigenaarAdvertentie() {
        //given
        GebruikerDto g = new GebruikerDto("Herman", "Scherman", "ww");

        //when
        a.setEigenaarAdvertentie(g);

        assertThat(a.getEigenaarAchternaam(), is(g.getAchternaam()));
        assertThat(a.getEigenaarVoornaam(), is(g.getVoornaam()));
    }
}
