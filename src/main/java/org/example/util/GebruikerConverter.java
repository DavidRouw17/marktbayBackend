package org.example.util;

import org.example.domein.Gebruiker;
import org.example.domein.GebruikerDto;

public class GebruikerConverter {

    public GebruikerDto convert(Gebruiker g){
        return new GebruikerDto(g.getId(), g.getVoornaam(), g.getAchternaam(), g.getEmail(), g.getWachtwoord());
    }
}
