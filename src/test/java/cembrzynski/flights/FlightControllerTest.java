package cembrzynski.flights;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FlightControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldGetFlightsForMonday(){
        testGetFlights("2022/06/13", 1, 9);
    }

    @Test
    public void shouldGetFlightsForTuesday(){
        testGetFlights("2022/06/14", 2, 8);
    }

    @Test
    public void shouldGetFlightsForWednesday(){
        testGetFlights("2022/06/15", 3, 7);
    }

    @Test
    public void shouldGetFlightsForThursday(){
        testGetFlights("2022/06/16", 4, 7);
    }

    @Test
    public void shouldGetFlightsForFriday(){
        testGetFlights("2022/06/17", 5, 7);
    }

    @Test
    public void shouldGetFlightsForSaturday(){
        testGetFlights("2022/06/18", 6, 6);
    }

    @Test
    public void shouldGetFlightsForSunday(){
        testGetFlights("2022/06/19", 7, 9);
    }

    private void testGetFlights(String date, int dayOfWeek, int size){

        ResponseEntity<List<Flight>> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/api/flights/" + date, HttpMethod.GET, null, new ParameterizedTypeReference<List<Flight>>() {
        });

        List<Flight> actual = responseEntity.getBody();

        assertTrue(actual.stream().allMatch(e -> e.getDays().contains(dayOfWeek)));
        assertEquals(size, actual.size());
        Flight previous = actual.get(0);

        for (int i = 1; i < actual.size(); i++) {
            assertTrue(actual.get(i).getDepartureTime().isAfter(previous.getDepartureTime()) || actual.get(i).getDepartureTime().equals(previous.getDepartureTime()));
            previous = actual.get(i);
        }
    }
}