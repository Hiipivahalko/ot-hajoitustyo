/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author osiipola
 */
public class KassapaateTest {
    
    private Maksukortti kortti;
    private Kassapaate paate;
    
    
    @Before
    public void setUp() {
        this.kortti = new Maksukortti(240);
        this.paate = new Kassapaate();
    }
    
    @Test
    public void tarkistaEttaKassanAlustusOikein() {
        assertEquals(100000, paate.kassassaRahaa());
        assertEquals(0, paate.maukkaitaLounaitaMyyty() + paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void edullisenLounaanOstoKateisellaOnnistuu() {
        int vaihtoRaha = paate.syoEdullisesti(240);
        assertEquals(0, vaihtoRaha);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
        assertEquals(100240, paate.kassassaRahaa());
    }
    
    @Test
    public void maukkaanLounaanOstoKateisellaOnnistuu() {
        int vaihtoRaha = paate.syoMaukkaasti(400);
        assertEquals(0, vaihtoRaha);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
        assertEquals(100400, paate.kassassaRahaa());
    }
    
    @Test
    public void edullisenLounaanOstoKateisellaOnnistuu2() {
        int vaihtoRaha = paate.syoEdullisesti(500);
        assertEquals(260, vaihtoRaha);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
        assertEquals(100240, paate.kassassaRahaa());
    }
    
    @Test
    public void maukkaanLounaanOstoKateisellaOnnistuu2() {
        int vaihtoRaha = paate.syoMaukkaasti(500);
        assertEquals(100, vaihtoRaha);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
        assertEquals(100400, paate.kassassaRahaa());
    }
    
    @Test
    public void eiRiittavastiRahaaEiEdullistaLounasta() {
        int vaihtoRaha = paate.syoEdullisesti(200);
        assertEquals(200, vaihtoRaha);
        assertEquals(0, paate.edullisiaLounaitaMyyty());
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void eiRiittavastiRahaaEiMaukastaLounasta() {
        int vaihtoRaha = paate.syoMaukkaasti(200);
        assertEquals(200, vaihtoRaha);
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void kortillaVoiOstaaEdullisenLounaan() {
        boolean succ = paate.syoEdullisesti(kortti);
        assertEquals(true, succ);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
        assertEquals(100000, paate.kassassaRahaa());
        assertEquals(0, kortti.saldo());
    }
    
    @Test
    public void kortillaVoiOstaaMaukkaanLounaan() {
        kortti.lataaRahaa(500);
        boolean succ = paate.syoMaukkaasti(kortti);
        assertEquals(true, succ);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
        assertEquals(100000, paate.kassassaRahaa());
        assertEquals(340, kortti.saldo());
    }
    
    @Test
    public void kortillaeiTarpeeksiSaldoaJotenEiEdullistaLounasta() {
        paate.syoEdullisesti(kortti);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
        boolean succ = paate.syoEdullisesti(kortti);
        assertEquals(false, succ);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
        assertEquals(100000, paate.kassassaRahaa());
        assertEquals(0, kortti.saldo());
    }
    
    @Test
    public void kortillaeiTarpeeksiSaldoaJotenEiMaukastaLounasta() {
        boolean succ = paate.syoMaukkaasti(kortti);
        assertEquals(false, succ);
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
        assertEquals(240, kortti.saldo());
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void lataaKortilleRahaaOnnistuu() {
        paate.lataaRahaaKortille(kortti, 100);
        assertEquals(100100, paate.kassassaRahaa());
        assertEquals(340, kortti.saldo());
    }
    
    @Test
    public void lataaKortilleEpaonnistuu() {
        paate.lataaRahaaKortille(kortti, -20);
        assertEquals(100000, paate.kassassaRahaa());
        assertEquals(240, kortti.saldo());
    }
    
    
}
