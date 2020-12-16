package org.example.dao;


import org.example.domein.Gebruiker;
import org.example.domein.GebruikerDto;
import org.example.exceptions.GeenGebruikerGevondenExceptie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//gebruiker en generiek
@ExtendWith(MockitoExtension.class)
public class GebruikerDaoTest {

    @InjectMocks
    private GebruikerDao target;

    @Mock
    private EntityManager emMock;

    @Mock
    private Gebruiker gMock;

    @Mock
    private GebruikerDto gdMock;

    @Mock
    private TypedQuery<Gebruiker> tqgMock;

    @Mock
    private TypedQuery<GebruikerDto> tqgdMock;

    @Mock
    private BCrypt bMock;


    @Test
    void findByIdWorks() {
        //given
        long id = 1L;
        when(emMock.find(Gebruiker.class, id)).thenReturn(gMock);
        //when
        Gebruiker g = target.getById(id);
        //then
        verify(emMock).find(eq(Gebruiker.class), eq(id));
        assertThat(g, is(gMock));
    }

    @Test
    void getAllWorks() {
        //given

        when(emMock.createNamedQuery("Gebruiker.findAll", Gebruiker.class)).thenReturn(tqgMock);
        //when
        target.getAll();
        //then
        verify(emMock).createNamedQuery(eq("Gebruiker.findAll"), eq(Gebruiker.class));
        verify(tqgMock).getResultList();
    }


    @Test
    void getByQueryWorks() {
        //given
        String q = "zoekopdracht";
        when(emMock.createNamedQuery("Gebruiker.findAll", Gebruiker.class)).thenReturn(tqgMock);
        //when
        target.getByQuery(q);
        //then
        verify(emMock).createNamedQuery(eq("Gebruiker.findAll"), eq(Gebruiker.class));
        verify(tqgMock).getResultList();
    }

    @Test
    void getByEmailWorks() throws GeenGebruikerGevondenExceptie {
        //given
        String email = "email";
        when(emMock.createNamedQuery("Gebruiker.zoekOpEmail", GebruikerDto.class)).thenReturn(tqgdMock);
        when(tqgdMock.getSingleResult()).thenReturn(gdMock);
        //when
        GebruikerDto g = target.getByEmail(email);
        //then
        verify(emMock).createNamedQuery(eq("Gebruiker.zoekOpEmail"), eq(GebruikerDto.class));
        verify(tqgdMock).getSingleResult();
        assertThat(g, is(gdMock));
    }

    @Test
    void addWorks() {
        //given
        String email = "email";
        when(gMock.getEmail()).thenReturn(email);
        when(emMock.createNamedQuery("Gebruiker.zoekOpEmail", GebruikerDto.class)).thenReturn(tqgdMock);
        when(tqgdMock.getSingleResult()).thenThrow(NoResultException.class);

        //when
        target.add(gMock);
        //then
        verify(emMock).createNamedQuery(eq("Gebruiker.zoekOpEmail"), eq(GebruikerDto.class));
        verify(tqgdMock).getSingleResult();
        verify(gMock).setWachtwoord(any());
    }


}
