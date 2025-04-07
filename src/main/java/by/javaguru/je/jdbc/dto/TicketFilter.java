package by.javaguru.je.jdbc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TicketFilter {
    private String passengerName;
    private String seatNo;
    private int limit;
    private int offset;
}
