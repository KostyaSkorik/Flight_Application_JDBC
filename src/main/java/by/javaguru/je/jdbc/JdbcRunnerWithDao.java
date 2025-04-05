package by.javaguru.je.jdbc;

import by.javaguru.je.jdbc.dao.TicketDao;
import by.javaguru.je.jdbc.entity.Ticket;

import java.math.BigDecimal;
import java.util.Optional;


public class JdbcRunnerWithDao {
    public static void main(String[] args) {
        Ticket ticket;
        TicketDao ticketDao = TicketDao.getInstance();
        Optional<Ticket> ticketOptional = ticketDao.findById(14L);
        if(ticketOptional.isPresent()){
            ticket = ticketOptional.get();
            System.out.println(ticket);
            ticket.setCost(BigDecimal.valueOf(5600L));
            ticketDao.update(ticket);
        }
        Optional<Ticket> ticketOptionalAfterUpdate = ticketDao.findById(14L);
        if(ticketOptionalAfterUpdate.isPresent()){
            System.out.println(ticketOptionalAfterUpdate);
        }

    }
}
