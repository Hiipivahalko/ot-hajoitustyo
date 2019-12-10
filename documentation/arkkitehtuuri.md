
# Arkkitehtuurikuvaus

## Rakenne

Alla karkea luonnos projektin rakenteesta

![pakkausrakenne](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/documentation/pictures/pakkausrakenne.jpg)

Kuvasta nähdään, että kaikki käyttäjän näkemät asiat sisältyvät foodstorage.ui pakkaukseen, josta ohjataan tietoa ohjelman "aivoihin" foodstorage.appservice. Appservice tekee siis ohjelman suurimman toiminnallisuuden, josta johdetaan vastuuta alaspäin foodstorage.domain luokille, jotka jakavat lopulta toiminnot vielä foodstorage.dao pakkauselle, joka tallentaa lopullisen tiedon pysyvästi tietokantaan.

## Käyttöliittymä

## Sovelluslogiikka

Sovelluksen tärkeinpänä objektina toimii Food-luokka, johon sovellus nojaa täysin, mikä myös huomataan alla olevasta luokkakaaviosta. Food-luokka kuvaa keittön raaka-aineita, joista sovelluksessa pidetään tietoa.

![luokkakaavioSuppea](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/documentation/pictures/sovellusLuokkakaavio.jpg)



![luokkakaavio](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/documentation/pictures/luokkakaavio.jpg)


![sekvenssikaavio](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/documentation/pictures/tuotteenlisäysSekvenssi.png)
