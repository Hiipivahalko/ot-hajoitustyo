# Käyttöohje

Lataa varasto sovellus [varasto.jar](https://github.com/Hiipivahalko/ot-hajoitustyo/releases/tag/3.0)

## Tietokanta/Tietojen tallennus ja käyttö

* Ohjelma olettaa että sinulla on luku/kirjoitusoikues kansioon jossa suoritat ohjelmaa
* Ohjelma tallentaa sovelluksessa käytetyt tiedot tietokantaan. Jos kansiossa jossa suoritat ohjlemaa ei löydy tietokantatiedostoa ```foodstorage.db``` , niin ohjelma itse luo tämän tiedoston. Muuten ohjelma käyttää valmiiksi olevaa tietokantaa. Jos tietokanta on jonkuin muun rakentama kuin ohjelman, niin voi syntyä virhe tilanteita ja ohjelma ei välttämättä toimi halutulla tavalla. Joten tarkasta, että kansioissa jossa suoritat ohjelman ei ole saman nimistä tietokantatiedostoa, ellet ole jo käyttänyt aikaisemmin ohjelmaa

## Sovelluksen käynnistäminen

Sovelluksen saa vaivattomasti käyntiin seuraavalla komennolla kansion juuresta jonne sovelluksen latasit (tänne ohjelma myös tallentaa tarvitsemansa tietokannan).

```
java -jar varasto.jar
```
Tämän jälkeen seuraavanlainen ikkuna pitäisi aueta. Tämä näkymä aukeaa aina kun sovelluksen käynnistää. Kirjautumisia ei vaadita. Jos avaat sovelluksen ensi kertaa, olet poistanut tietokannan tai suoritat ohjelmaa jossain uudessa kansiossa, niin kaikki listat: raaka-aineet, ostoskori sekä reseptit ovat tyhjiä.

![kuva etusivusta](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/documentation/pictures/frontpage.png)

### Yleistä ohjelman rakenteesta

Ohjelman jokaisen sivun yläreunasta löytyy suunnistuspainikkeet joiden avulla voit liikkua ohjelman eri sivuilla. Vasemmassa- ja alareunassa on taas yleisesti painikkeita jokta toteuttavat jonkinlaisia toimintoja. Eleisesti painikkeiden nimet kertovat mitä, niistä kuuluisi tapahtua.

## Varastonnäkymä

Klikattuasi etusivun varasto painiketta, pääset näkemään keittiösi raaka-aineet, valmiit reseptit sekä mitä reseptejä pystyisit valmistamaan saatavilla olevista raaka-aineista. Jos haluat valmistaa reseptin raaka-aine valikoimastasi, niin se tapahtuu painamalla painiketta "__valmista resepti__" joka löytyy mahdollisten reseptien yläpuolelta. Sivun vasemmasta reunasta voit rajoittaa raaka-aineittesi näkymää tai hake tiettyjä raaka-aineita. Voit myös lisätä uusia raaka-aineita varastoon "__Lisää raaka-aine__" painikkeesta. Tästä painikkeesta painettuna siirryt "__raaka-aine mallit-sivulle__". Syötyäsi ruoan tai reseptin, voit poistaa sen listalta helposti sivun alareunassa olevien painikkeiden avulla. 

![kuva varastosta](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/documentation/pictures/storage.png)

## Raaka-aine mallit

Mallit sivulla pääset helposti lisäämään raaka-aineita varastoon, joita sinulla on jo ennen ollut. Kunhan vain lisäät halutun raaka-aineen kohdalla olevaan "__määrä__" kenttään kuinka monta kyseyistä raaka-ainetta haluat ja painat samaa raaka-ainetta listalla, siten että sen raaka-aineen rivi muuttuu joko siniseksi tai harmaaksi. Tämän jälkeen voit painaa "__Lisää valittu raaka-aine__" ja raaka-aine listään varastoon.

Jos taas haluat lisätä kokonaan uuden raaka-aineen, niin sinun pitää painaa "__Lisää uusi raaka-aine__", tämän jälkeen sinut ohjataan sivulle jossa pääset lisäämään uuden raaka-aineen

![kuva malleista](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/documentation/pictures/layouts.png)

## Uuden raaka-aineen lisäys ilman mallia

Tältä sivulta pääset lisäämään uuden raaka-aineen suoraan varastoosi tai vain tehdä uuden raaka-aine mallin. Täytä sivun kentät ja paina haluamaasi painiketta. __Määrä__ ja __paino__ kirjoituskenttiin tulee laittaa vain numeroita. Ohjelma kyllä osaa huomata jos yrität laittaa jotain muuta.

![kuva uuden raaka-aineen lisäyksestä](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/documentation/pictures/newFood.png)

## Reseptit

Reseptit sivulta pääset katselemaan luomiasi reseptejä ja siirtymään luomaan uusia reseptejä. Valmiin reseptin tarkastelu tapahtuu klikkaamalla haluamaansa reseptiä __Kaikki reseptit__ listalta. Klikattuasi reseptia, sen rivi muuttuu joko siniseksi tai harmaaksi. Tällöin voit painaa painiketta __näytä resepti__ jolloin reseptin tiedot tulevat sivun oikeaan keskilohkoon näkyviin. Uuden reseptin luonti tapahtuu siirtymällä reseptin lisäys sivulle, johon pääsee painamalla painiketta __Lisää uusi__ vasemmassa reunassa.

![kuva resepteistä](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/documentation/pictures/recipes.png)

## Luo uusi resepti

Lisää resepti sivulla voit luoda uuden haluamasi reseptin. Täytä vaaditut kentät ja valitse listalta mitä raaka-aineita kyseinen resepti vaatii ja miten monta. Tämän jälkeen paina __Lisää uusi resepti__ painiketta ja uusi resepti luodaan ja sinut ohjataan takasin reseptit sivulle. Huomaa että __valmistusaika__ kenttä vaatii pelkkiä numeroita toimiakseen.

![kuva uuden reseptin luonnista](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/documentation/pictures/createNewRecipe.png)

## Ostoslista

Ostoskori sivulla pääset luomaan seuraavan kerran kauppakassiasi. Voit lisätä myös ostoslistalle suoraan haluamasi reseptin raaka-aineet. Tuotteiden tai reseptien lisääminen tapahtuu klikkaamalla haluamaa tuotetta/reseptiä ja lisäämällä haluaman määrän tuotetta/tuotteita. Haluamasi tuoteen/reseptin rivi täytyy olla väriltää sininen tai harmaa, tällöin tiedät että olet valinnut tuotteen. Sitten valitset vain tarvitsemasi painikkeen. Resepteille ja raaka-aineille on omat painikkeensa.

Sitten kun olet käynyt kaupassa voit tyhjentää ostoslistan tuotteen suoraan varastoosi painikkeen __Tuotteet hankittu -> lisää tuotteet varastoon__ samalla ostoslista siis myös tyhjenee kokonaan.

![kuva ostoslistasta](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/documentation/pictures/shoppingBasket.png)


## Ohjelman sulkeminen

Ohjelman voit sulkea käyttösi jälkeen painamalla sovellusikkunan rasti painiketta 

![rasti](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/documentation/pictures/rasti.png)
