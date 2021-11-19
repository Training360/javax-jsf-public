# Java alapú webfejlesztés JSF technológiával - gyakorlati feladat

A gyakorlati feladat célja egy olyan alkalmazás létrehozása, mellyel
kedvenc helyeket (location) lehet nyilvántartani. Minden helyhez tartozik egy
név és koordináták (latitude - szélesség, longitude - hosszúság).

A feladatok szándékosan alulspecifikáltak, hogy legyen mód a kreativitásra,
valamint a JSF különböző funkcionalitásának kipróbálására.

A feladatokat megoldhatod Java EE alkalmazásával (WildFly alkalmazásszerverrel),
ahogy a videók többsége is, de kellő tapasztalattal akár használhatsz végig
Spring Bootot is. Elegendő csak az egyik technológiával végigcsinálni a
feladatokat. Később viszonylag kis energiával akár át is alakítható a másik
technológiára.

## Egyszerű Java EE/Spring Boot JSF alkalmazás

Készíts egy `locations` nevű alkalmazást! Hozz létre egy oldalt, mely kíírja a
neved! A Controller neve legyen `NameController`, míg a Facelets neve
`name.xhtml`!

## Ürlapkezelés

Módosítsd úgy az előbbi oldalt, hogy legyen rajta egy beviteli mező,
ahova be lehet írni a neved, és köszönt.

## Redirect after post

Módosítsd az oldalt, hogy a Redirect After Post tervezési mintával működjön!

## Alkalmazásarchitektúra - listázás és felvitel

Hozz létre egy oldalt, ahol táblázatosan a kedvenc helyeket lehet
listázni, és egy oldalt, ahol egy új kedvenc helyet lehet felvenni!

Felvitelnél meg kell adni a nevét, valamint a koordináit
lebegőpontos számokként. A szélességi és hosszúsági koordinátákat
külön `double` típusú attribútumként tárold!
Létrehozandó komponensek:

* `Location` - `name`, `lat`, `lon` attribútumokkal
* `LocationDto` - hasonló attribútumokkal
* `LocationsService` - `Location` példányok nyilvántartására
* `LocationsController` - táblázat megjelenítésére Controller
* `locations.xhtml` - táblázatot tartalmazó Facelets
* `CreateLocationController` - kedvenc helyet létrehozó Controller
* `CreateLocationCommand` - `name` és `lat` és `lon` attribútumokkal
* `create-location.xhtml` - Faclets, mely tartalmaz egy űrlapot kedvenc hely létrehozására
* `ModelMapperConfig` - `ModelMapper` példány létrehozására, hogy később injektálni lehessen

Sikeres létrehozás után írjon ki egy üzenetet!

## Alkalmazásarchitektúra - módosítás és törlés

Hozz létre egy oldalt a módosításra! Használd a `UpdateLocationController` és
`update-location.xhtml` neveket!

Módosítsd a `LocationsController` és `locations.xhtml` állományokat, hogy lehessen
törölni is!

## Controllerek Unit tesztelése

Hozz létre unit teszteket a `CreateLocationController`, `UpdateLocationController` és
`LocationsController` osztályok tesztelésére!
Ehhez egy `MessageContext` osztályra is szükséged lesz.

## Integrációs tesztelés Arquillian használatával

Hozz létre integrációs teszteket a következőkre:

* Kedvenc hely létrehozása
* Kedvenc hely módosítása
* Kedvenc hely törlése

Ahhoz, hogy a tesztek ne akadjanak össze, minden teszt eset előtt töröld ki a
kedvenc helyek listáját!

## E2E tesztelés Selenium használatával

Hozz létre integrációs tesztet Selenium használatával a felvitelre, módosításra és
törlésre!

Ahhoz, hogy a tesztek ne akadjanak össze, minden teszt eset előtt töröld ki a
kedvenc helyek listáját!

## Űrlap get metódussal

Implementáld, hogy lehessen keresni a kedvenc helyek nevére!

## Ürlap előzetes feltöltése

A kedvenc helynél ki lehessen jelölni egy pipa segítségével a következőket:

* Épület
* Szép kilátás
* Víz van-e a közelben
* Városi

Ezeket az értékeket a controller töltse ki előre.
Jelenjen meg kedvenc hely létrehozásánál, módosításánál is, valamint a
táblázatban!

## Fájl fel- és letöltés

A kedvenc helyhez lehessen egy képet is feltölteni!

## Konvertálás

Vegyél fel a `Location` osztályba egy utolsó látogatás dátumát,
amit létrehozásnál meg lehet adni, módosításnál lehet módosítani,
és törölni is lehessen!

Jelenjen meg a táblázatban is!

## Validálás

Implementáld a következő ellenőrzéseket!

* A név megadása felvétel és módosítás esetén is kötelező.
* A szélesség értéke -90 és 90 közötti, a hosszúság értéke -180 és 180 közötti legyen.

## Validálás üzleti logikában

Implementáld, hogy ugyanazzal a névvel ne szerepelhessen kedvenc hely a
rendszerben!

## Ismétlődés és panel típusú komponensek

A táblázat alatt a kedvenc helyek jelenjenek meg egy `div` blokkban.
Kerüljön kiírásra egy címben a neve, alatta a koordináták, végül alatta, hogy
milyen típusú hely, és mikor lett utoljára meglátogatva.

## Hibakezelés

Készíts egy hibaoldalt, ahol kezeled a szerver `500` hibát. Kerüljön kiírásra egy
hibaüzenet!

## I18N

A felhasználói felület szövegei properties állományból kerüljenek betöltésre!

## Nyelvváltás

Lehessen váltani angol és magyar felület között!

## Stílus

Tegyél egy alap Bootstrap stílust a felhasználói felület oldalaira!

## Template kezelés

Készíts egy sablont fejléccel és lábléccel, és minden oldal ez alapján
kerüljön megjelenítésre!

## Navigációs szabályok

(Opcionális, vagy ezt akár külön projektben is implementálhatod, az eredeti
  projekt lemásolásával.)

Vegyél fel navigációs szabályokat az oldalak közötti navigációra!

## AJAX

Implementáld a validációt AJAX-szal!

Implementáld, hogy amikor beírod a koordinátákat, a rendszer számítsa át un. DMS (Degrees/Minutes/Seconds)
formátumba! Azaz váltsa át perc/másodperc értékekké.

A következő kódrészlet használható:

```java
double coord = 59.345235;
int sec = (int) Math.round(coord * 3600);
int deg = sec / 3600;
sec = Math.abs(sec % 3600);
int min = sec / 60;
sec %= 60;
```

## Flow

(Opcionális, vagy ezt akár külön projektben is implementálhatod, az eredeti
  projekt lemásolásával.)

A kedvenc hely létrehozását válaszd szét két lapra! Az első oldalon lehessen megadni
a nevét és a koordinátákat, a második lapon pedig a többi tulajdonságát!

## Kompozit komponensek

Hozz létre egy komponenst, amivel egy koodinátát lehet kiírni annak két komponensének
megadásával! Tegye `span` tagek közé, és vesszővel válassza el!

## PrimeFaces

A PrimaFaces [GMap komponensével](https://www.primefaces.org/showcase/ui/data/gmap/markers.xhtml) helyezd el a térképen a megfelelő kedvenc helyeket!
