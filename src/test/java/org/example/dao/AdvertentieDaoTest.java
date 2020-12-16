package org.example.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.example.domein.Advertentie;
import org.example.domein.AdvertentieDto;
import org.example.domein.Gebruiker;
import org.example.domein.GebruikerDto;
import org.example.exceptions.GeenGebruikerGevondenExceptie;
import org.example.exceptions.WachtwoordEmailComboKloptNietExceptie;
import org.example.util.Bezorgwijze;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//ook voor generieke dao
@ExtendWith(MockitoExtension.class)
class AdvertentieDaoTest {

    @InjectMocks
    private AdvertentieDao target;

    @Mock
    private EntityManager emMock;

    @Mock
    private Advertentie aMock;

    @Mock
    private AdvertentieDto adMock;

    @Mock
    private TypedQuery<Advertentie> tqaMock;

    @Mock
    private TypedQuery<AdvertentieDto> tqadMock;

    @Test
    void getAllDTOWorks(){
        //given
        when(emMock.createNamedQuery("Advertentie.findAll", AdvertentieDto.class)).thenReturn(tqadMock);
        //when
        target.getAllDto();
        //then
        verify(emMock).createNamedQuery(eq("Advertentie.findAll"), eq(AdvertentieDto.class));
        verify(tqadMock).getResultList();
    }

    @Test
    void getByQueryDTOWorks(){
        //given
        String q = "zoekterm";
        when(emMock.createNamedQuery("Advertentie.findAll", AdvertentieDto.class)).thenReturn(tqadMock);
        //when
        target.getByQueryDto(q);
        //then
        verify(emMock).createNamedQuery(eq("Advertentie.findAll"), eq(AdvertentieDto.class));
        verify(tqadMock).getResultList();
    }


}
