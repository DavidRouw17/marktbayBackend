package org.example.dao;

import org.example.domein.Advertentie;
import org.example.domein.AdvertentieDto;
import org.example.domein.Gebruiker;
import org.example.domein.GebruikerDto;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class AdvertentieDao extends GeneriekeDao<Advertentie> {

    public List<AdvertentieDto> getByQueryDto(String q) {
        String[] query = q.toLowerCase().split(" ");
        List<AdvertentieDto> retList = new ArrayList<>();
        for (AdvertentieDto advertentieDto : getAllDto()) {
            boolean add = true;
            for (String s : query) {
                if (!advertentieDto.zoekbareData().toLowerCase().contains(s)){
                    add = false;
                    break;
                }
            }
            if (add) {retList.add(advertentieDto);}
        }
        return retList;
    }

    public List<AdvertentieDto> getAllDto() {
        TypedQuery<AdvertentieDto> tq = em.createNamedQuery("Advertentie.findAll", AdvertentieDto.class);
        return tq.getResultList();
    }
}
