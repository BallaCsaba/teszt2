package astronauts;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;

public record   Astronaut(String name,
                        LocalDate dateOfBirth,
                        LocalDate dateOfDeath,
                        String nationality,
                        String bio,
                        String agency,
                        Duration timeInSpace,
                        int numberOfSpacewalks,
                        int numberOfFlights,
                        boolean inSpace) implements Serializable {
}
