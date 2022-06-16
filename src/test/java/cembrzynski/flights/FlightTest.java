package cembrzynski.flights;


import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlightTest {

    @Test
    public void shouldCreateFlight(){

        String[] input = {" 12:25 " ,"Montego Bay","MBJ","VS065","","","","x","","",};

        assertEquals(new Flight(LocalTime.of(12,25), "Montego Bay", "MBJ", "VS065", Set.of(3)), new Flight(input));

    }
}