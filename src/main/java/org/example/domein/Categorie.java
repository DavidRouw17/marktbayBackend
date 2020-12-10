package org.example.domein;

import javax.persistence.*;

@Entity
public class Categorie {

    @GeneratedValue
    @Id
    private long id;
    private String categorie;

}
