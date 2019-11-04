package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void saldoAlussaOikein() {
        assertEquals("saldo: 10.0", kortti.toString());
    }
    
    @Test
    public void rahanlataaminenToimiiOikeinPositiivisellaArvolla() {
        kortti.lataaRahaa(500);
        assertEquals("saldo: 15.0", kortti.toString());
    }
    
    @Test
    public void rahanOttaminenKortiltaToimiiKunKortillaTarpeeksiRahaa() {
        boolean value = kortti.otaRahaa(500);
        assertEquals("saldo: 5.0", kortti.toString());
        assertEquals(true, value);
    }
    
    @Test
    public void josKortillaEiTarpeeksiRahaaSaldoEiMuutu() {
        boolean  value = kortti.otaRahaa(1500);
        assertEquals("saldo: 10.0", kortti.toString());
        assertEquals(false, value);
    }
    
    @Test
    public void saldoOikea() {
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void saldoOikeaKunLisataanRahaa() {
        kortti.lataaRahaa(400);
        assertEquals(1400, kortti.saldo());
    }
    
    
}
