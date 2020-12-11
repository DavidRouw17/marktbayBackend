package org.example.dao;

import org.example.domein.Advertentie;
import org.example.domein.AdvertentieDto;
import org.example.domein.Gebruiker;
import org.example.domein.GebruikerDto;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class AdvertentieDao extends GeneriekeDao<Advertentie> {

    public List<AdvertentieDto> getByQueryDto(String q) {
        return getAllDto().stream()
                .filter(a -> a.zoekbareData().contains(q))
                .collect(Collectors.toList());
    }

    public List<AdvertentieDto> getAllDto() {
        TypedQuery<AdvertentieDto> tq = em.createNamedQuery("Advertentie.findAll", AdvertentieDto.class);
        return tq.getResultList();
    }
}
