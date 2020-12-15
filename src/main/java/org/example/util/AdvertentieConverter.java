package org.example.util;

import org.example.domein.Advertentie;
import org.example.domein.AdvertentieDto;

public class AdvertentieConverter {

    public AdvertentieDto convert(Advertentie a){
        return new AdvertentieDto(a.getId(), a.getTitel(), a.getSoort(), a.getPrijs(), a.getOmschrijving(),
                a.getEigenaarVoornaam(), a.getEigenaarAchternaam());
    }
}
