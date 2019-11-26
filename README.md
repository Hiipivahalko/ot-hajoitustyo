# Ohjelmistotekniikka, harjoitustyö

Helsingin yliopiston Ohjelmistotekniikka kurssin harjoitustyö

## Dokumentaatio

* [Työaikakirjanpito](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/documentation/working_hours.md)

* [Määrittelydokumentti](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/documentation/definition.md)

* [Arkkitehtuuri](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/documentation/arkkitehtuuri.md)

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

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto target/site/jacoco/index.html

### Checkstyle

Tiedostoon [checkstyle.xml](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/FoodStorage/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

```
mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto target/site/checkstyle.html
