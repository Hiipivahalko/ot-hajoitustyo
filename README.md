# Ohjelmistotekniikka, harjoitustyö

Helsingin yliopiston Ohjelmistotekniikka kurssin harjoitustyö

Varasto sovellus, sovelluksella pystyt pitämään kirjaa sekä näkemään mitä keittiöstäsi on. Pystyt myös nähdä millaisia aterioita/reseptejä pystyisit niistä saamaan aikaiseksi. Tarvittavista tuotteista pystyt myös rakentamaan ostoslistaa seuraavan kerran kauppareissullesi. Keittiö myös muistaa vanhat tuotteesi, jonka avulla pystyt lisäämään ne helposti takaisin kaappeihin, kun olet käynyt kaupassa.

## Dokumentaatio

* [Työaikakirjanpito](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/documentation/working_hours.md)

* [Määrittelydokumentti](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/documentation/definition.md)

* [Arkkitehtuuri](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/documentation/arkkitehtuuri.md)

## Releaset

[Viikko 5](https://github.com/Hiipivahalko/ot-hajoitustyo/releases/tag/1.0)
[Viikko 6](https://github.com/Hiipivahalko/ot-hajoitustyo/releases/tag/1.0)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto FoodStorage/target/site/jacoco/index.html

### Suoritettavan jarin generointi

Komento

```
mvn package
```
generoi hakemistoon target suoritettavan jar-tiedoston FoodStorage-1.0-SNAPSHOT.jar

### JavaDoc

JavaDoc generoidaan komennolla
```
mvn javadoc:javadoc
```

JavaDocia voi tarkastella avaamalla seilamella tiedosto FoodStorage/target/site/apidocs/index.html

### Checkstyle

Tiedostoon [checkstyle.xml](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/FoodStorage/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

```
mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto FoodStorage/target/site/checkstyle.html
