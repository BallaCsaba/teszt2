package astronauts;

import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Collections;
import org.jetbrains.annotations.Unmodifiable;

public interface AstronautManager {

    @SuppressWarnings("unchecked")
    default @Unmodifiable List<Astronaut> getAstronauts() {
        try {
            var astronauts = (List<Astronaut>) new ObjectInputStream(AstronautManager.class.getResourceAsStream("astronauts.ser"))
                    .readObject();
            return Collections.unmodifiableList(astronauts);
        } catch (Exception e) {
            throw new AssertionError("Failed to load objects");
        }
    }

    /**
     * A konzolra írja a még élő űrhajósok közül az első 10 legfiatalabb nevét
     * és születési dátumát. Az űrhajósok mindegyikéhez egy sort kell a konzolra
     * írni
     * <pre>név: születési_dátum</pre>
     * formában. Tipp: a {@link LocalDate} osztály implementálja a
     * {@link Comparable} interfészt.
     */
    void printFirstTenYoungestLivingAstronauts();

    /**
     * Visszaadja a repülések számának összegét az amerikai ({@code "American"})
     * nemzetiségű űrhajósokra.
     *
     * @return a repülések számának összege az amerikai ({@code "American"})
     *         nemzetiségű űrhajósokra
     */
    int getSumOfNumberOfFlightsForAmericanAstronauts();

    /**
     * Visszaadja a {@code "NASA"} ügynökség űrhajósaihoz tartozó és a
     * {@code "colonel"} sztringet kisbetű-nagybetű érzéketlen módon tartalmazó
     * életrajzok közül a legrövidebbet egy {@code Optional}-be csomagolva.
     *
     * @return a {@code "NASA"} ügynökség űrhajósaihoz tartozó és a
     *         {@code "colonel"} sztringet kisbetű-nagybetű érzéketlen módon
     *         tartalmazó életrajzok közül a legrövidebb egy {@code Optional}-be
     *         csomagolva
     */
    Optional<String> getShortestNASABioContainingColonel();

    /**
     * Visszaadja a már elhunyt űrhajósok számát a halálozás hónapja szerint
     * csoportosítva. Tipp: a {@link LocalDate#getMonth()} metódus
     * adja vissza egy {@link LocalDate} objektum hónapját egy
     * {@link Month} típusú objektum formájában, mint például
     * {@code Month.JUNE}.
     *
     * @return a már elhunyt űrhajósok száma a halálozás hónapja szerint
     *         csoportosítva
     */
    Map<Month, Long> getNumberOfAstronautsByMonthOfDateOfDeath();

    /**
     * Visszaadja a legtöbb szóból álló űrhajós nevet egy {@code Optional}-be
     * csomagolva. Tipp: a {@link  String#split(String)} metódus használható
     * egy sztring feldarabolására. Ha például szóközökkel elválasztott szavakra
     * kell egy {@code s} sztringet bontani, akkor az alábbi kódot
     * használhatjuk:
     * {@snippet :
     * String[] words = s.split(" ");
     * }
     * Mint látható, a {@code split()} metódus egy tömbben adja vissza a
     * szavakat.
     *
     * @return a legtöbb szóból álló űrhajós név egy {@code Optional}-be
     *         csomagolva
     */
    Optional<String> getNameWithTheMostNumberOfWords();

}
