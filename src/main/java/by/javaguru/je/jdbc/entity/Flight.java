package by.javaguru.je.jdbc.entity;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Flight {
    private Long id;
    private Long flightNo;
    private LocalDateTime departureDate;
    private Long departureAirportCode;
    private LocalDateTime arrivalDate;
    private Long arrivalAirportCode;
    private Long aircraftId;
    private FlightStatus status;

}
