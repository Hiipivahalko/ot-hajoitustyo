
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

Jokaisella FXML-tiedostolla on oma controller.java tiedosto joka pystyy antamaan tietoja ja kertomaan mitä toimintoja pitäisi tehdä sovelluslogiikalle. FXML-tiedostot toimivat vain ns. näkyvänä osapuolena, niin kuin esim HTML-tiedostot. Käyttöliittymän FXML- ja controller-tiedostoilta on siis eriytetty kokonaan pois sovelluslogiikka ja ne vain luovat uusia instasseja eri domain luokista ja antavat tämäm olion eteenpäin sovelluslogiikalle ja kutsuvat sen avulla jotain sovelluslogiikan funktiota. Aina kun sovellus tekee jonkin toiminnon, niin aina jokin lista tai kokosivu päivitetään, ellei sitten siirrytä kokonaan uudelle sivulle jolloin sen sivun tiedon myös päivitetään ennen käyttäjälle näyttämistä

## Sovelluslogiikka

Sovelluksen tärkeinpänä objektina toimii Food-luokka, johon sovellus nojaa täysin, mikä myös huomataan alla olevasta luokkakaaviosta. Food-luokka kuvaa keittön raaka-aineita, joista sovelluksessa pidetään tietoa.

![luokkakaavioSuppea](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/documentation/pictures/sovellusLuokkakaavio.jpg)

### Päätoiminnallisuudet



#### Muut toiminnallisuudet
![luokkakaavio](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/documentation/pictures/luokkakaavio.jpg)

![sekvenssikaavio](https://github.com/Hiipivahalko/ot-hajoitustyo/blob/master/documentation/pictures/tuotteenlisäysSekvenssi.png)

## Tietojen pysyväisyystallennus

Sovelluksen tietojentallennuksesta vastaavat ot.foodstorage.dao pakkauksen luokat.

Sovellus käyttää tietojen pysyväisyystallennukseen tietokantaa joka koostuu viidestä eri taulusta (Food, Layout, Recipe, ReadyRecipes, ShoppinBasket). Jos tietokantatiedostoa ```foodstorage.db``` ei löydy ohjelman suorittevasta kansiosta luodaan se sinne ja alustetaan tarvittavilla tauluilla. Muuten jos tämä tiedosto on olemassa, niin ohjelma käyttää tätä samaa tiedostoa. Vaikka käyttäjä itse menisi poistamaan jonkin taulun käsin valmiista tietokantatiedostosta, niin ohjelma loisi uudestaan tämän taulun. Ohjelma siis tarkastaa että käytettävästä tietokannasta löytyy tarvittavat taulut, mutta valitettavasti ei tarkasta että taulun rakenne olisi juuri haluttu. Vaikka tietokantataulujen luokilla on omia riippuuvuuksia toisiinsa, niin silti kaikki tietokantataulut ovat muista riippumattomia ja toimivat vaikka muut taulut olisivat tyhjiä. Dao-luokat on toteuttu käyttäen [Data Access Object](https://en.wikipedia.org/wiki/Data_access_object)-suunnittelumallia. Tietokanta Dao-luokkia ei käytetä sovelluslogiikan perustava, vaan niiden avulla alustetaan sovelluslogiikan tietokantarakenteet, joten halutessaa sovelluksen toimintaa ja laajennusta voi helposti toteuttaa. 

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







