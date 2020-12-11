package org.example.domein;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.util.Bezorgwijze;

import java.util.List;

@Data
@AllArgsConstructor
public class AdvertentieDto {
    private Long id;

    private String titel;
    private String soort;
    private double prijs;

    private String omschrijving;
    private String gebruikerVoornaam;
    private String gebruikerAchternaam;

    public String zoekbareData(){
        return titel + " " + soort + " " + omschrijving;
    }
}
