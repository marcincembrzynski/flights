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
import java.util.stream.Collectors;

import org.springframework.data.util.Pair;

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

        testGetFlights("2022/06/13", 1, "[09:00->GND, 09:00->UVF, 10:15->HAV, 10:35->LAS, 11:00->MCO, 11:05->BGI, 11:10->MCO, 13:00->MCO, 15:35->LAS]");
    }

    @Test
    public void shouldGetFlightsForTuesday(){
        testGetFlights("2022/06/14", 2, "[09:00->ANU, 10:15->HAV, 10:35->LAS, 11:05->BGI, 11:45->MCO, 12:20->CUN, 13:00->MCO, 15:35->LAS]");
    }

    @Test
    public void shouldGetFlightsForWednesday(){
        testGetFlights("2022/06/15", 3, "[10:15->MCO, 10:35->LAS, 11:05->BGI, 11:45->MCO, 12:25->MBJ, 13:00->MCO, 15:35->LAS]");
    }

    @Test
    public void shouldGetFlightsForThursday(){
        testGetFlights("2022/06/16", 4, "[10:00->ANU, 10:15->HAV, 10:25->LAS, 11:05->BGI, 11:35->MCO, 13:00->MCO, 15:35->LAS]");
    }

    @Test
    public void shouldGetFlightsForFriday(){
        testGetFlights("2022/06/17", 5, "[10:10->GND, 10:10->UVF, 10:15->LAS, 11:05->BGI, 11:20->MCO, 13:00->MCO, 15:35->LAS]");
    }

    @Test
    public void shouldGetFlightsForSaturday(){
        testGetFlights("2022/06/18", 6, "[10:00->ANU, 10:15->LAS, 11:05->BGI, 11:20->MCO, 13:00->MCO, 15:35->LAS]");
    }

    @Test
    public void shouldGetFlightsForSunday(){
        testGetFlights("2022/06/19", 7,  "[09:00->UVF, 09:00->TAB, 10:10->MCO, 10:15->LAS, 11:05->BGI, 11:45->MCO, 12:40->MBJ, 13:00->MCO, 15:35->LAS]");
    }

    private void testGetFlights(String date, int dayOfWeek, String expectedListOfTimesAndDestinations){

        ResponseEntity<List<Flight>> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/api/flights/" + date, HttpMethod.GET, null, new ParameterizedTypeReference<List<Flight>>() {
        });

        List<Flight> actual = responseEntity.getBody();

        assertTrue(actual.stream().allMatch(e -> e.getDays().contains(dayOfWeek)));
        assertEquals(expectedListOfTimesAndDestinations, actual.stream().map(e -> Pair.of(e.getDepartureTime(), e.getDestinationIataCode())).collect(Collectors.toList()).toString());

    }
}