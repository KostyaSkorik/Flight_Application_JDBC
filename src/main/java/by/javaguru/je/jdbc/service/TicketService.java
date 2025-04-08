package by.javaguru.je.jdbc.service;

import by.javaguru.je.jdbc.dao.TicketDao;
import by.javaguru.je.jdbc.dto.TicketDto;

import java.util.List;
import java.util.stream.Collectors;

public class TicketService {

    private static final TicketService INSTANCE = new TicketService();
    private static final TicketDao ticketDao = TicketDao.getInstance();


    public List<TicketDto> findAllByFlightId(Long flightId){
        return ticketDao.findAllByFlightId(flightId).stream().map(ticket -> new TicketDto(ticket.getId(),
                ticket.getFlight().getId(),ticket.getSeatNo())).collect(Collectors.toList());
    }

    public static TicketService getInstance() {
        return INSTANCE;
    }
    private TicketService(){

    }
}
