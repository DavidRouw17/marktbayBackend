package org.example.domein;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Adres {

    @Id
    @GeneratedValue
    private Long id;

    private String straat;
    private String woonplaats;
    private String postcode;
}
