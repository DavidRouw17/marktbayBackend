package org.example.domein;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class AdvertentieDtoTest {



    @Test
    public void zoekbareData() {
        AdvertentieDto a = new AdvertentieDto(1L, "Fiets", "Product", 12, "leuk",
                "henk", "henkie");

        assertThat(a.zoekbareData(), is("Fiets Product leuk"));
    }
}
