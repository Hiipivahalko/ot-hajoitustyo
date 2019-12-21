# Testausdokumentti

Ohjelmaa on testattu automaattisesti toistettavilla yksikkö- ja integraatiotestein JUnitilla sekä järjestelmätestaus käsin ohjelmaa suorittaessa. Automaattiset testit voi suorittaa ```./FoodStorage/``` juuressa komennolla

```
mvn test

```

## Yksikkö ja integraatiotestaus

### sovelluslogiikka

Automaatiotestien suurimmassa pääpainossa on [service luokkien](https://github.com/Hiipivahalko/ot-hajoitustyo/tree/master/FoodStorage/src/test/java/ot/foodstorage/service) integraatiotestaus, jolla pystymme tarkastamaan että sovelluksemme toimii tilanteissa kuin tilanteissa haluamallamme tavalla ja käyttäjälle ei synny virheellisiä toimintoja.

Yksikkö- ja integraatio testit käyttävät tallennukseen Dao-rajapintoja, jotka käyttävät testeihin omia testaustietokantoja. Tietokannat alustetaan halutuisksi aina ennen testejä, jotta pystymme varautumaan erillaisiin tilanteisiin.

Yksikkötestauksessä on myös tarkastettu että [foodstorage.domain](https://github.com/Hiipivahalko/ot-hajoitustyo/tree/master/FoodStorage/src/main/java/ot/foodstorage/domain) luokkien omat funktiot toimivat kuten pitää (esim equals()).

### Dao-rajapinnat

Dao-rajapinnan toteuttavat luokat on myös testattu käyttäen omia testitietokantoja, jotta tiedämme että tietokantaan tallentuu tieto juuri siten kun haluammekin.

### testauskattavuus

Sovellusta on testauttu seuraavanlaisesti:

* rivikattavuus 92%
* haarautumiskattavuus 88%

![testauskattavuus kuva](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/documentation/pictures/jacoco.png)

Testikattavuuden voi saada selville seuraavalla komennolla

```
mvn test jacoco:report
```
Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto FoodStorage/target/site/jacoco/index.html

## Järjestelmätestaus

Sovellusta on myös testattu manuaalisesti käsin

### Asennus ja käynnistäminen

Sovellus on haettu, asennetu ja käynnistetty sovelluksen [käyttöohjeen](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/documentation/manual.md) mukaan. Testaus on tapahtunut LINUX-ympäristössä.

Testauksessa on myös otettu huomioon onko tietokantatiedosto ollut jo valmiina ennen sovelluksen käyttöä.

### Toiminnot

Sovelluksen [määrittelydokumentin](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/documentation/definition.md) listaamat featuret/toiminnot on testattu manuaaliseti, myös siten että virhetilanne saattaisi sattua. Nämä kaikki toiminnot toimivat moitteetta niinkuin määrittelydokumentissa kerrotaan.
