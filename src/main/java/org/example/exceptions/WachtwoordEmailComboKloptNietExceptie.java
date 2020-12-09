package org.example.exceptions;

public class WachtwoordEmailComboKloptNietExceptie extends Exception {
    public WachtwoordEmailComboKloptNietExceptie(){
        super("Combinatie van email en wachtwoord klopt niet.");
    }
}
