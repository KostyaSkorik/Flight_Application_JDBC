package by.javaguru.je.jdbc.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketDto {
    private final Long id;
    private final Long flightId;
    private final String seatNo;
}
