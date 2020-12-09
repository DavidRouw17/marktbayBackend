package org.example.exceptions;

public class GebruikerBestaatAlExceptie extends Exception {
    public GebruikerBestaatAlExceptie(){
        super("Gebruiker bestaat al!");
    }
}
