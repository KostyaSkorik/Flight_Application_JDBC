package by.javaguru.je.jdbc;

import by.javaguru.je.jdbc.dao.FlightDao;
import by.javaguru.je.jdbc.dao.TicketDao;



public class JdbcRunnerWithDao {
    public static void main(String[] args) {
//        Ticket ticket;
//        Optional<Ticket> ticketOptional = ticketDao.findById(14L);
//        if(ticketOptional.isPresent()){
//            ticket = ticketOptional.get();
//            System.out.println(ticket);
//            ticket.setCost(BigDecimal.valueOf(560L));
//            ticketDao.update(ticket);
//        }
//        Optional<Ticket> ticketOptionalAfterUpdate = ticketDao.findById(14L);
//        if(ticketOptionalAfterUpdate.isPresent()){
//            System.out.println(ticketOptionalAfterUpdate);
//        }
        FlightDao flightDao = FlightDao.getInstance();
        TicketDao ticketDao = TicketDao.getInstance();

//        Flight flight = new Flight();
//        flight.setId(102L);
//        flight.setFlightNo(2321L);
//        flight.setDepartureDate(LocalDateTime.of(2020, 12, 12, 12, 12));
//        flight.setDepartureAirportCode(8L);
//        flight.setArrivalDate(LocalDateTime.of(2020, 12, 13, 12, 12));
//        flight.setArrivalAirportCode(9L);
//        flight.setAircraftId(7L);
//        flight.setStatus(FlightStatus.ARRIVED);

//        TicketFilter filter = new TicketFilter(null,null,4,0);
        System.out.println(ticketDao.findById(14L));


    }
}
