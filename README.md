Feladat
Ebben a feladatban űrhajósokkal kell dolgoznod. Az astronauts.Astronaut rekord osztály egy űrhajóst ábrázol, az alábbi komponensei vannak:

name: űrhajós neve (String);
dateOfBirth: születési dátum (java.time.LocalDate);
dateOfDeath: elhalálozás dátuma (java.time.LocalDate), lehet null;
nationality: nemzetiség (String);
bio: életrajz (String);
agency: ügynökség (például ESA, NASA) (String);
timeInSpace: világűrben töltött idő (java.time.Duration);
numberOfSpacewalks: űrséták száma (int);
numberOfFlights: repülések száma (int);
inSpace: jelenleg a világűrben van-e (boolean).
Az src/main/resources/astronauts könyvtár alatt találod az astronauts.ser bináris állományt, mely 500 darab Astronaut objektum egy listáját tartalmazza. A lista objektum sorosítással került kiírásra az állományba.

Az astronauts.AstronautManager interfész Astronaut objektumok kezeléséhez biztosít támogatást. A getAstronauts() alapértelmezett metódus adja vissza az 500 Astronaut objektum listáját.

Az interfész öt absztrakt metódust tartalmaz, ezeket lásd a forráskódban.

Előkészületek
Csomagold szét a ZIP állományt, mely egy Apache Maven projektet tartalmaz, ez a kiindulási alap a munkádhoz.

A továbbiakban minden Git műveletet a projekt pom.xml állományát tartalmazó könyvtáradban kell végrehajtani.

Hozz létre egy üres Git tárolót a

git init
parancs végrehajtásával.

Konfiguráld a Git-et megfelelően beállítva a tárolóban a user.name és user.email opciókat a

git config --local user.name "teljes_hivatalos_név"
git config --local user.email email_cím
parancsokkal. Figyeld meg, hogy a --local opció megadásával arra utasítjuk a Git-et, hogy az opciókat a tárolóban, a .git rejtett könyvtár alatt raktározza el.

Importáld a projekt könyvtárát a tárolóba a

git add .
git commit -m "initial import"
parancsok végrehajtásával.

Interfész implementálás
A feladatod egy, az interfészt implementáló AstronautManagerImpl nevű osztály létrehozása az astronauts csomagban.

Először is implementáld az interfészt az IntelliJ IDEA Implement Methods lehetőségével, mely "csonkokat" hoz létre az interfész metódusokhoz. Az IDE által generált metódustörzsek üresek lesznek vagy pedig egy return 0;, return null; vagy return Optional.empty(); utasítást fognak tartalmazni. Tipp: a Copy Javadoc jelölő bekattintása arra utasítja az IDE-t, hogy az interfész metódus Javadoc megjegyzését is másolja le, ez jól jön!

Adj hozzá az AstronautManagerImpl osztályhoz egy main metódust. A main metódus törzsének első sorában deklarálj egy olyan lokális változót, melynek értéke egy referencia az osztály egy példányára:

public static void main(String[] args) {
    var manager = new AstronautManagerImpl();
}
Git művelet: Add hozzá a metódus "csonkokat" és a main metódust tartalmazó AstronautManagerImpl osztályt a tárolóhoz egy commit-ban, melynek üzenete legyen "feat: add AstronautManagerImpl class with method stubs".

Az AstronautManagerImpl osztályban az alábbi módon kell implementálnod minden egyes absztrakt interfész metódust:

Ha a metódus void visszatérési típusú és a neve print-tel kezdődik, akkor az alábbi módon kell, hogy kinézzen az implementációja:

@Override
public void printSomething() {
  getAstronauts().stream()
      // köztes műveletek
      .forEach(...);
}
Ha a visszatérési típus nem void, akkor az implementáció az alábbi módon kell, hogy kinézzen:

@Override
public SomeType getSomething() {
  return getAstronauts().stream()
      // köztes műveletek
      // terminális művelet
      ;
}
Tehát a metódustörzsek pontosan egy stream csővezetéket kell, hogy tartalmazzanak, semmi más nem megengedett! Elutasításra kerülnek az olyan metódusok, melyek törzse bármi egyebet tartalmaz, mint egy stream csővezeték.

Ezután az interfész használatának bemutatásához a main metódusban meg kell hívnod a példányon mind a négy absztrakt interfész metódust. Ha egy metódus egy értéket ad vissza, akkor azt a konzolon meg is kell jeleníteni.

Egy interfész metódus implementációja 1 pontot ér, azonban akkor, és csak akkor jár a pont, ha az alábbiak mindegyike teljesül:

A metódustörzs semmi egyebet nem tartalmaz, mint egy stream csővezeték (nem void metódus esetén előtte egy return utasítással).

A stream csővezeték pontosan azt csinálja, ami az interfész metódus Javadoc megjegyzésében is elő van írva. Nem jár részpont a részben helyes implementációkra.

A metódus meghívásra kerül az AstronautManagerImpl osztály main metódusában. Ha a metódus értéket ad vissza, az a konzolra is kiírásra kerül.

Szigorúan tilos az AstronautManager interfészben bármiféle módosítás. Például nem változtatható meg egy metódus visszatérési típusa. Ha az interfészben bármit is megváltoztatsz, akkor a megoldásod automatikusan elutasításra kerül.

Git művelet: Véglegesítd a változásokat a tárolóban egy commit-ban, melynek üzenete legyen "fix: implement interface methods in AstronautManagerImpl".

Függőség hozzáadása a projekthez
Git művelet: Hozz létre a tárolóban egy annotate nevű ágat és válts át rá. A további változások mind az annotate ágon kell, hogy történjenek.

A pom.xml állományban add hozzá az alábbi függőséget a projekthez:

org.jetbrains:annotations:26.0.2, melynek hatásköre (scope) legyen compile
Tehát a JetBrains java-annotations könyvtárával fogunk dolgozni a projektben, mely a fejlesztést támogató annotációkat biztosít.

Fontos: A függőségek hozzáadása után kattints a pom.xml állományon az egér jobb gombjával a Project fülön, majd válaszd a Sync Project pontot a Maven almenüből.

Git művelet: Véglegesítd a változásokat a tárolóban egy commit-ban, melynek üzenete legyen "build: add java-annotations dependency to pom.xml".

Ezután az alábbi módosításokat kell elvégezned az AstronautManager interfészben. Először is add hozzá a kódhoz az

import java.util.Collections;
import org.jetbrains.annotations.Unmodifiable;
import deklarációkat. Ezután a getAstronauts() alapértelmezett metódus return astronauts; utasítását cseréld le az alábbi sorra:

return Collections.unmodifiableList(astronauts);
Végül pedig a getAstronauts() alapértelmezett metódus visszatérési típusát az alábbi módon jelöld meg az @Unmodifiable annotációval:

default @Unmodifiable List<Astronaut> getAstronauts() {
    // ...
}
Az annotáció azt jelöli, hogy metódus által visszaadott lista nem módosítható.

2 pontot kapsz, ha a megfelelően módosított kód lefordul.

Git művelet: Véglegesítd a változásokat a tárolóban egy commit-ban, melynek üzenete legyen "chore: annotate AstronautManager with @Unmodifiable".

A munkád feltöltése
A projekted munkakönyvtárát kell feltöltened egy ZIP állományban, melynek tartalmaznia kell a Git tárolódat is, azaz a .git könyvtárat.

Fontos: Olyan projektet kell feltöltened, mely sikeresen lefordítható. Ha az mvn compile parancs végrehajtásának eredménye BUILD FAILURE, akkor a munkád elutasításra kerül.

Pontozás
A megoldások pontozása az alábbi módon történik:

Git tároló létrehozása és a projekt a tárolóba importálása: 1 pont
"Csonkok" létrehozása az interfész metódusokhoz és ezen változások a tárolóba importálása: 1 pont
Az 1. interfész metódus tökéletes implementálása: 1 pont
A 2. interfész metódus tökéletes implementálása: 1 pont
A 3. interfész metódus tökéletes implementálása: 1 pont
A 4. interfész metódus tökéletes implementálása: 1 pont
Az 5. interfész metódus tökéletes implementálása: 1 pont
Az új lehetőség tökéletes implementálása: 2 pont (1 pont a pom.xml módosítása, 1 pont a Java kód módosítása)
Az új lehetőség tökéletes implementálása egy ágon 2 commit-ban történt: 1 pont
Tippek
Nem kötelező a parancssor (Git BASH) használata a Git műveletekhez. Minden egyes Git művelet elvégezhető az IDE-ben a menükből is.

A site életciklus fázis végrehajtásakor a target/site könyvtárban jön létre a projekt webhelye. A generált Javadoc dokumentáció a Project Reports alatt érhető el a webhelyen.
