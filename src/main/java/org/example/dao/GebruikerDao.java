package org.example.dao;

import org.example.domein.Gebruiker;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class GebruikerDao extends GeneriekeDao<Gebruiker> {


    public List<Gebruiker> getByQuery(String q){
        return getAll().stream()
                .filter(c -> c.allSearchableDataText().contains(q))
                .collect(Collectors.toList());
    }

}
