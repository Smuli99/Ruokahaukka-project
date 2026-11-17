## Serverin toiminta (jotenkin kerrottu)
- kannattaa käyttää tekoälyä myös apuna sillä en tiedä itsekkään kaikkien toimintaa täysin

Datan haku sovelluksesta:
- pyyntö:
  - `Frontend -> Routes -> Services -> Repository -> Tietokanta`
- vastaus:
  - `Frontend <- Routes <- Services <- Repository <- Tietokanta`

Datan synkkaus Semmalta
- `Semma -> Client -> DTO -> Services -> Repository -> Tietokanta`

### main

```client/```
- Vastaa tiedon hakemisesta ulkoisista palveluista HTTP-pyyntöjen avulla
- `RestaurantFetcher` hakee ravintolan ruokalistat JSON-muodossa
- Haettu data parsitaan RestaurantDto data muotoon
- `toRestaurant()` muuntaa dto datan `NewRestaurantMenu` data muotoon
- Client siis vastaa vain ulkoisen datan hakemisesta ja sen muuntamisesta

```config/```
- Sisältää sovelluksen kiinteät asetukset
- Tänne voi siis lisätä, kun tarve kiinteille asetuksille
- `RestaurantDataSources` sisältää tiedot tuetuista ravintoloiden lähteistä
  - jokaiselle lähteelle on nimetty **nimi** ja **url**
  - metodi `all` hakee listan kaikista tietolähteistä

```database/```
- Luodaan yhteys PostgreSQL tietokantaan 
- `AppDatabase` määrittelee tietokannan käyttöön tarvittavan rajapinnan
- `AppDatabaseImpl` toteuttaa tietokantayhteyden Hikarilla ja suorittaa tietokantamigraatiot Flywaylla
- Tietokantaoperaatiot suoritetaan erillisissä transaktioissa

```dto/```
- Sisältää ulkoisista palveluista haettavan JSON-datan rakenteita vastaavat Data Transfer Object luokat
- Dto luokkien avulla JSON data voidaan muuntaa kotlin olioiksi

```models/```
- Sisältää sovelluksen omat tietomallit
- Malleja voi muokata haluttuun muotoon sovellusta varten
- Sisältää myös uusien tietojen luomiseen käytettäviä malleja
  - yleensä ilman `id` kenttää, koska sen luo tietokanta

```repository/```
- Vastaa tietokannan tietojen hakemisesta, lisäämisestä ja muokkaamisesta
- `RestaurantRepository` määrittelee tietokantaoperaatioiden rajapinnan
- `RestaurantRepositoryImpl` toteuttaa rajapinnan ja suorittaa tietokantakyselyt
- Repository erottaa tietokantatoiminnot muusta sovelluslogiikasta

```routes/```
- Sisältää Ktorin HTTP-reitit, joiden avulla sovelluksen toimintoja voidaan käyttää
- Reitit siis vastaanottavat ja palauttat HTTP-pyyntöjä
- `FetchDataRoutes` sisältää reitit ulkoisen tiedon hakuun
- `RestaurantRoutes` sisältää reitit joiden avulla
  - haetaan, muokataan, poistetaan tietokannassa olevia ravintoloita ja niiden tietoja

```service/```
- Sisältää sovelluksen toimintalogiikan
- `RestaurantSyncService`  vastaa ulkoisesta palvelusta haetun datan synkronoinnista tietokantaan 
- Se tarkistaa löytyykö ravintola jo tietokannasta
  - jos ei se luodaan
  - sen jälkeen tarkistetaan onko kyseinen viikkolista lisätty jo ravintolalle
  - jos on ei tehdä mitään sillä synkronointi on jo tehty
  - jos ei luodaan viikkolista data ravintolalle
- `RestaurantService` vastaa tietokantaan tallennettujen ravintolatietojen käsittelystä
    - Se hakee ravintoloita ja niiden tietoja repositoryn kautta
    - Se välittää haetut tiedot reiteille palautettavaksi

``Application.kt``
- Sovelluksen käynnistys
- Luo ja yhdistää sovelluksen tarvitsemat riippuvuudet, kuten tietokannan, repositoryn ja servicet

### test
- testejä varten


