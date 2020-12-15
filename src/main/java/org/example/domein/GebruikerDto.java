package org.example.domein;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.util.Bezorgwijze;

import javax.persistence.Entity;
import java.util.List;

@AllArgsConstructor
@Data
public class GebruikerDto {
    private long id;
    private String voornaam;
    private String achternaam;
    private String email;
    private String wachtwoord;

    private List<Bezorgwijze> bezorgwijzen;

    public GebruikerDto(long id, String voornaam, String achternaam, String email, String wachtwoord) {
        this.id = id;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.email = email;
        this.wachtwoord = wachtwoord;
    }
}
