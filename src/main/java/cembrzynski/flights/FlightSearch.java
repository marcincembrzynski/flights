package cembrzynski.flights;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class FlightSearch {

    private Map<Integer, List<Flight>> flightsDayOfWeekMap;

    @Value("${flights.data}")
    private String flightsData;


    @PostConstruct
    public void init(){
        try {
            List<String> lines = Files.readAllLines(Path.of(flightsData));
            List<Flight> flights = lines.stream().skip(1).map(e -> new Flight(e.split(","))).collect(Collectors.toList());
            flightsDayOfWeekMap = IntStream.rangeClosed(1, 7).boxed()
                    .collect(Collectors.toMap(i -> i, i -> flights.stream().filter(e -> e.getDays().contains(i)).sorted((a, b) -> a.getDepartureTime().compareTo(b.getDepartureTime())).collect(Collectors.toList())));
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public List<Flight> getFlightsForDate(int year, int month, int day){
        DayOfWeek dayOfWeek = LocalDate.of(year, month, day).getDayOfWeek();
        return flightsDayOfWeekMap.get(dayOfWeek.getValue());
    }
}