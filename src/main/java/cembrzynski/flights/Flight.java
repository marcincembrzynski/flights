package cembrzynski.flights;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    private LocalTime departureTime;
    private String destination;
    private String destinationIataCode;
    private String flightNo;
    private Set<Integer> days;

    public Flight(String[] parts) {
        String[] departureTimeArr = parts[0].split(":");
        int hour = Integer.valueOf(departureTimeArr[0].replaceAll(" ", ""));
        int minute = Integer.valueOf(departureTimeArr[1].replaceAll(" ", ""));
        this.departureTime = LocalTime.of(hour, minute);
        this.destination = parts[1];
        this.destinationIataCode = parts[2];
        this.flightNo = parts[3];

        Set<Integer> days = new HashSet<>();
        for(int i = 4; i < parts.length; i++){
            if("x".equals(parts[i])){
                if(i == 4) {
                    days.add(7);
                } else {
                    days.add(i - 4);
                }
            }
        }
        this.days = days;
    }

}