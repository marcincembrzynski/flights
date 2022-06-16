package cembrzynski.flights;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FlightController {

    private final FlightSearch flightSearch;

    public FlightController(FlightSearch flightSearch) {
        this.flightSearch = flightSearch;
    }

    @GetMapping("/flights/{year}/{month}/{day}")
    public ResponseEntity<List<Flight>> flights(@PathVariable("year") int year, @PathVariable("month") int month, @PathVariable("day") int day){
        return ResponseEntity.ok(flightSearch.getFlightsForDate(year, month, day));
    }
}
