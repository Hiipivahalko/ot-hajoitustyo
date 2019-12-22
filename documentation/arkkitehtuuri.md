
# Arkkitehtuurikuvaus

## Rakenne

Alla karkea luonnos projektin rakenteesta

![pakkausrakenne](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/documentation/pictures/pakkausrakenne.png)

Kuvasta nähdään, että kaikki käyttäjän näkemät asiat sisältyvät foodstorage.ui pakkaukseen, josta ohjataan tietoa ohjelman "aivoihin" foodstorage.service. Serviceluokat tekee siis ohjelman suurimman toiminnallisuuden, josta johdetaan vastuuta alaspäin foodstorage.domain luokille, jotka jakavat lopulta toiminnot vielä foodstorage.dao pakkauselle, joka tallentaa lopullisen tiedon pysyvästi tietokantaan.

## Käyttöliittymä

Käyttöliittymä on toteutettu käyttämällä JavaFX:n FXML-tiedostoja. Sovellus perustuu kolmeen pääsivuun

* varasto
* reseptit
* ostoslista

Jokaisella FXML-tiedostolla on oma [controller.java](https://github.com/Hiipivahalko/ot-hajoitustyo/tree/master/FoodStorage/src/main/java/ot/foodstorage/ui) tiedosto joka pystyy antamaan tietoja ja kertomaan mitä toimintoja sovelluslogiikan pitäisi tehdä. FXML-tiedostot toimivat vain ns. näkyvänä osapuolena, niin kuin esim HTML-tiedostot selaimessa. Käyttöliittymän FXML- ja controller-tiedostoilta on siis eriytetty kokonaan pois sovelluslogiikka ja ne vain luovat uusia instasseja eri [domain luokista](https://github.com/Hiipivahalko/ot-hajoitustyo/tree/master/FoodStorage/src/main/java/ot/foodstorage/domain) ja antavat tämäm olion eteenpäin sovelluslogiikalle ja kutsuvat jotain sovelluslogiikan funktiota. Aina kun sovellus tekee jonkin toiminnon, niin aina jokin lista tai kokosivu päivitetään.

## Sovelluslogiikka

Alla olevassa kuvassa on kuvailtuna sovelluksen rakenne ja riippuvuudet toisiinsa

![luokkakaavio](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/documentation/pictures/luokkakaavio.jpg)

Kuvasta huomataan että appService luokan kautta pääsemme käsitksi kaikkialle sovelluksen tapahtumiin ja se toimiikin myös välikätenä käyttöliitymälle toimitettavasta datasta. Appservice toimii sovelluksen aivoina.

Sovelluksen tärkeinpänä objektina toimii Food-luokka, johon sovellus nojaa täysin, mikä myös huomataan alla olevasta luokkakaaviosta. Food-luokka kuvaa keittön raaka-aineita, joista sovelluksessa pidetään tietoa. Nämä riipuvuudet on jätety pois sovelluksen kokonais luokkakaaviosta, koska nämä vain kuvaavat että ostoskorilla ja reseptillä on raaka-aine objekteja itsellää, mutta kun esim tyhjennämme ostoskorin, niin ostoskorin raaka-aineet tallentuvat tietokantaan appService -> FoodService... polkua pitkin

![luokkakaavioSuppea](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/documentation/pictures/suppealuokkakaavio.jpg)

### Päätoiminnallisuudet

Sovelluksen päätoiminnallisutena voidaan pitää kuutta suurinta toimintoa:

* uuden/malli raaka-aineen lisääminen varastoon
* uuden raaka-aine mallin luominen
* ostoslistan rakentaminen resepteistä ja raaka-aineista
* ostoslistan tyhjentäminen ja tuotteiden siirto varastoon
* uusien reseptine luonti
* reseptien valmistaminen saatavilla olevista raaka-ainesta
* valmiin reseptin tai raaka-aineen poistaminen varastosta (syöminen)

Alle on kuvattuna yhden toiminnallisuuden sekvensiikaavio selventämään toimintojen toimimista

### Reseptin valmistaminen (kokkaaminen)

Sekvenssikaavio reseptin valmistamisesta, kun käyttäjä valitsee mahdollisten reseptien listalta mitä pystytään mahdollisesti valmistamaan.

![reseptin valmistus](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/documentation/pictures/reseptinValmistus.png)

Sama periaate toimii kaikissa muissakin toiminnoissa, eli luodaan jokin uusi instanssi ja kutsutaan appService luokkaa, joka jatkaa toiminnon funktiota johonkin muuhun service luokkaa ja sieltä aina Dao-luokan kautta tietokantaan.

#### Muut toiminnallisuudet

Muut sovelluksen toiminnot perustuvat enemänkin vain käyttöliittymän näkyviin rajauksiin, tietojen näyttämiseen tai jonkin tietyn asian hakua. Toki näissäkin toiminnoissa tarvitaan appService luokkaa joka tuo käyttöliittymälle tarvittavat tiedot


## Tietojen pysyväisyystallennus

Sovelluksen tietojentallennuksesta vastaavat ot.foodstorage.dao pakkauksen luokat.

Sovellus käyttää tietojen pysyväisyystallennukseen tietokantaa joka koostuu viidestä eri taulusta (Food, Layout, Recipe, ReadyRecipes, ShoppinBasket). Jos tietokantatiedostoa ```foodstorage.db``` ei löydy ohjelman suorittevasta kansiosta luodaan se sinne ja alustetaan tarvittavilla tauluilla. Muuten jos tämä tiedosto on olemassa, niin ohjelma käyttää tätä samaa tiedostoa. Vaikka käyttäjä itse menisi poistamaan jonkin taulun käsin valmiista tietokantatiedostosta, niin ohjelma luo uudestaan tämän taulun. Ohjelma siis tarkastaa että käytettävästä tietokannasta löytyy tarvittavat taulut, mutta valitettavasti ei tarkasta että taulun rakenne olisi juuri haluttu. 

Vaikka tietokantataulujen luokilla on omia riippuuvuuksia toisiinsa, niin silti kaikki tietokantataulut ovat muista riippumattomia ja toimivat vaikka muut taulut olisivat tyhjiä. Dao-luokat on toteuttu käyttäen [Data Access Object](https://en.wikipedia.org/wiki/Data_access_object)-suunnittelumallia. Tietokanta Dao-luokkia ei käytetä sovelluslogiikan perustava, vaan niiden avulla alustetaan sovelluslogiikan tietokantarakenteet, joten halutessaa sovelluksen toimintaa ja laajennusta voi helposti toteuttaa. 

### Tietokantataulujen rakenne

alle on kuvattu sovelluksen tietokantataulut karkeasti ja mihin niitä käytetään

```CREATE TABLE food(id INTEGER, name TEXT, manufacturer TEXT, preservation TEXT, weight INTEGER, amount INTEGER)```

* Foodtaulu säilyttää tietotaan varastossa olevista raaka-aineista

```CREATE TABLE layout(id INTEGER, name TEXT, rawMaterials TEXT, cookTime INTEGER, description TEXT, instruction TEXT)```

* Layouttaulussa säilytetään tietona saatavilla olevista raaka-aine malleista

```CREATE TABLE recipe(id INTEGER, name TEXT, cookTime INTEGER, description TEXT, instruction TEXT)```

* recipetauluun tallennetaan käyttäjän luomat reseptit

```CREATE TABLE readeRecipes(id INTEGER, name TEXT, cookTime INTEGER, description TEXT, instruction TEXT, amount INTEGER)```

* readyRecipestaulussa säilytetään tieto siitä mitä reseptejä käyttäjä on itse valmistanut olemassa olevista raaka-aineista

```CREATE TABLE shoppingbasket(id INTEGER, name TEXT, items TEXT)```

* shoppingBaskettauluun tallennetaan käyttäjän ostoslista ja sen arvoa muutetaan sen mukaan, miten käyttäjä lisää tuotteita ostoskoriin.



Food ja Layout-tauluista muodostetaan sovelluksen käynnistyessä [domain.Food.java](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/FoodStorage/src/main/java/ot/foodstorage/domain/Food.java) luokan mukaisia olioita. Recipe- ja ReadyRecipes-tauluista taas muodostetaan [domain.Recipe.java](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/FoodStorage/src/main/java/ot/foodstorage/domain/Recipe.java) luokan olioita sekä shoppingbasketluokasta yksi [domain.ShoppinBasket.java](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/FoodStorage/src/main/java/ot/foodstorage/domain/ShoppingBasket.java) luokan olio



## Ohjelman rakenteeseen jääneet heikkoudet

* Ohjelma tallentaa turhaan tietokantaan tietoja ojelman pyöriessä. Tällä turhilla tiedoilla tarkoitan sitä jos käyttäjä lisää uuden raaka-aineen varastoon, niin se tieto tallennetaan suoraan myös tietokantaa, kun parempi vaihtoehto olisi tallentaa tiedot vasta esim yhdellä kertaa sitten kun käyttäjä sulkee ohjelman

* Käyttöliittymän jotkin toiminnot vähempien klikkauksien takana, jotta sovelluksen käyttö olisi suotuisampi käyttäjälle. Esim reseptin tietojen näyttäminen käyttäjälle voisi tapahtua suoraan reseptiä klikkaamalla, kun täytyy vielä painaa "näytä resepti"







