package org.example.util;

import org.example.domein.Advertentie;
import org.example.domein.AdvertentieDto;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class AdvertentieConverterTest {

    Advertentie a;

    @Before
    public void setUp(){
        a = new Advertentie("mooie fiets", "Product", 12, "Goed mooie fiets", null);
    }

    @Test
    public void convert() {
        AdvertentieDto ad = new AdvertentieConverter().convert(a);
        assertThat(ad.getTitel(), is(a.getTitel()));
        assertThat(ad.getSoort(), is(a.getSoort()));
        assertThat(ad.getPrijs(), is(a.getPrijs()));
        assertThat(ad.getOmschrijving(), is(a.getOmschrijving()));
    }
}
