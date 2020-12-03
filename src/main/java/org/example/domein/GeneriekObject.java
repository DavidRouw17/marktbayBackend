package org.example.domein;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class GeneriekObject {

    @Id
    @GeneratedValue
    protected Long id;

    public Long getId(){
        return id;
    }
}
