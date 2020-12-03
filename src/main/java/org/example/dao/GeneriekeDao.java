package org.example.dao;

import org.example.domein.Gebruiker;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;


public abstract class GeneriekeDao<T> {

    @PersistenceContext
    protected EntityManager em;

    public T getById(long id) {
        return em.find(T(), id);
    }

    public List<T> getByQuery(String q){
        return getAll().stream()
                .filter(c -> c.toString().contains(q))
                .collect(Collectors.toList());
    }


    public boolean add(T e) {
        try {
            em.persist(e);
            return true;
        }
        catch (Exception ex){
            // TODO loggen
            return false;
        }
    }

    public boolean update(T e) {
        try {
            em.merge(e);
            return true;
        }
        catch (Exception ex){
            //TODO logger inbouwen
            return false;
        }
    }

    public boolean remove(long id) {
        try {
            T e = getById(id);
            em.remove(e);
            return true;
        }
        catch (Exception ex){
            //TODO logger inbouwen
            return false;
        }
    }

    public List<T> getAll() {
        TypedQuery<T> tq = em.createNamedQuery(typeSimple() + ".findAll", T());
        return tq.getResultList();
    }

    public int dbSize(){
        return getAll().size();
    }

    private String typeSimple() {
        return T().getSimpleName();
    }

    @SuppressWarnings("unchecked")
    private Class<T> T() {
        return (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
