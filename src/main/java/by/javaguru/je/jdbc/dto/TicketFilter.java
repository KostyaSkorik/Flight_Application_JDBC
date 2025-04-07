package by.javaguru.je.jdbc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TicketFilter {
    private final String passengerName;
    private final String seatNo;
    private final int limit;
    private final int offset;
}
